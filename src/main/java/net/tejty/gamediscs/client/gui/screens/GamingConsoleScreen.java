package net.tejty.gamediscs.client.gui.screens;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.tejty.gamediscs.game.Game;
import net.tejty.gamediscs.game.controls.Button;
import net.tejty.gamediscs.item.custom.GameDiscItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GamingConsoleScreen extends Screen {
    private static final ResourceLocation BACKGROUD = new ResourceLocation("gamediscs:textures/gui/gaming_console.png");
    private static final int SCREEN_WIDTH = 160;
    private static final int SCREEN_HEIGHT = 198;
    private static final int SCREEN_X = 10;
    private static final int SCREEN_Y = 10;

    private static final int W = 87;
    private static final int S = 83;
    private static final int A = 65;
    private static final int D = 68;
    private static final int SPACE = 32;
    private static final int ENTER = 257;
    private final Timer timer = new Timer(50, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (game != null) {game.tick();}
        }
    });

    private int getConsoleX() {
        return (this.width - SCREEN_WIDTH) / 2;
    }
    private int getConsoleY() {
        return (this.height - SCREEN_HEIGHT) / 2;
    }

    private List<Game> availableGames = new ArrayList<>();
    private int selected = 0;
    private Game game;

    public GamingConsoleScreen(Component title) {
        super(title);

        availableGames = scanForGames();

        timer.start();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(graphics);

        graphics.blit(BACKGROUD, getConsoleX(), getConsoleY(), 0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 256, 256);

        graphics.enableScissor(SCREEN_X + getConsoleX(), SCREEN_Y + getConsoleY(), SCREEN_X + getConsoleX() + Game.WIDTH, SCREEN_Y + getConsoleY() + Game.HEIGHT);
        if (game != null) {
            game.render(graphics, SCREEN_X + getConsoleX(), SCREEN_Y + getConsoleY());
        }
        else {
            if (!availableGames.isEmpty()) {
                graphics.blit(
                        new ResourceLocation("gamediscs:textures/gui/selected.png"),
                        getConsoleX() + SCREEN_X,
                        getConsoleY() + SCREEN_Y + 3 + font.lineHeight + 18 * selected - (Math.max(0, selected - 3) * 18),
                        0, 0, 0, 140, 18, 140, 18
                );
            }

            graphics.drawString(
                    font,
                    Component.translatable("gui.gamingconsole.select_game").withStyle(ChatFormatting.BOLD),
                    getConsoleX() + SCREEN_X + (Game.WIDTH - font.width(Component.translatable("gui.gamingconsole.select_game").withStyle(ChatFormatting.BOLD).getVisualOrderText())) / 2,
                    getConsoleY() + SCREEN_Y + 3 - (Math.max(0, selected - 3) * 18),
                    0xace53b,
                    false
            );

            for (int i = 0; i < availableGames.size(); i++) {
                graphics.drawString(
                        font,
                        Component.literal(availableGames.get(i).getName().getString()).withStyle(availableGames.get(i).getColor(), i == selected ? ChatFormatting.BOLD : ChatFormatting.ITALIC),
                        getConsoleX() + SCREEN_X + 22,
                        getConsoleY() + SCREEN_Y + 4 + font.lineHeight + 18 * i + (18 - font.lineHeight) / 2 - (Math.max(0, selected - 3) * 18),
                        availableGames.get(i).getColor().getColor(),
                        false
                );
                graphics.blit(
                        availableGames.get(i).getIcon(),
                        getConsoleX() + SCREEN_X + 3,
                        getConsoleY() + SCREEN_Y + 4 + font.lineHeight + 18 * i - (Math.max(0, selected - 3) * 18),
                        0, 0, 0, 16, 16, 16, 16
                );
            }
        }
        graphics.disableScissor();

        super.render(graphics, pMouseX, pMouseY, pPartialTick);
    }

    public List<Game> scanForGames() {
        List<Game> games = new ArrayList<>();
        Player player = Minecraft.getInstance().player;
        //player.displayClientMessage(Component.literal("Scanning"), false);
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            //player.displayClientMessage(Component.literal("Item: " + player.getInventory().getItem(i).toString()), false);
            if (player.getInventory().getItem(i).getItem() instanceof GameDiscItem disc) {
                games.removeIf((game) -> game.getClass().equals(disc.getGame().getClass()));
                games.add(disc.getGame());
            }
        }
        return games;
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        boolean flag = false;

        //Minecraft.getInstance().player.displayClientMessage(Component.literal("Key: " + pKeyCode), false);
        if (game != null) {
            switch (pKeyCode) {
                case W:
                    game.controls.setButton(Button.UP, true);
                    flag = true;
                    break;
                case S:
                    game.controls.setButton(Button.DOWN, true);
                    flag = true;
                    break;
                case A:
                    game.controls.setButton(Button.LEFT, true);
                    flag = true;
                    break;
                case D:
                    game.controls.setButton(Button.RIGHT, true);
                    flag = true;
                    break;
                case SPACE:
                    game.controls.setButton(Button.BUTTON1, true);
                    flag = true;
                    break;
                case ENTER:
                    game.controls.setButton(Button.BUTTON2, true);
                    flag = true;
                    break;
            }
        }
        else {
            if (pKeyCode == W) {
                int newSelected = selected - 1;
                if (newSelected < 0) {
                    newSelected = availableGames.size() - 1;
                }
                selected = newSelected;
            }
            if (pKeyCode == S) {
                int newSelected = selected + 1;
                if (newSelected > availableGames.size() - 1) {
                    newSelected = 0;
                }
                selected = newSelected;
            }

            if ((pKeyCode == SPACE || pKeyCode == ENTER) && !availableGames.isEmpty()) {
                Game newGame = availableGames.get(selected);
                newGame.prepare();
                game = newGame;
            }
        }
        return (super.keyPressed(pKeyCode, pScanCode, pModifiers) || flag);
    }
    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        boolean flag = false;

        //Minecraft.getInstance().player.displayClientMessage(Component.literal("Key: " + pKeyCode), false);
        if (game != null) {
            switch (pKeyCode) {
                case W:
                    game.controls.setButton(Button.UP, false);
                    flag = true;
                    break;
                case S:
                    game.controls.setButton(Button.DOWN, false);
                    flag = true;
                    break;
                case A:
                    game.controls.setButton(Button.LEFT, false);
                    flag = true;
                    break;
                case D:
                    game.controls.setButton(Button.RIGHT, false);
                    flag = true;
                    break;
                case SPACE:
                    game.controls.setButton(Button.BUTTON1, false);
                    flag = true;
                    break;
                case ENTER:
                    game.controls.setButton(Button.BUTTON2, false);
                    flag = true;
                    break;
            }
        }

        return (super.keyPressed(pKeyCode, pScanCode, pModifiers) || flag);
    }
}
