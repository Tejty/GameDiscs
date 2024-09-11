package net.tejty.gamediscs.client.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.item.custom.GameDiscItem;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private static final int SHIFT = 24;
    private static final int W_X = 33;
    private static final int W_Y = 121;
    private static final int W_WIDTH = 14;
    private static final int W_HEIGHT = 24;
    private static final int W_SOURCE = 183;
    private static final int A_X = 17;
    private static final int A_Y = 137;
    private static final int A_WIDTH = 23;
    private static final int A_HEIGHT = 15;
    private static final int A_SOURCE = 160;
    private static final int D_X = 40;
    private static final int D_Y = 137;
    private static final int D_WIDTH = 23;
    private static final int D_HEIGHT = 15;
    private static final int D_SOURCE = 197;
    private static final int S_X = 33;
    private static final int S_Y = 145;
    private static final int S_WIDTH = 14;
    private static final int S_HEIGHT = 23;
    private static final int S_SOURCE = 220;
    private static final int B1_X = 96;
    private static final int B1_Y = 136;
    private static final int B1_WIDTH = 16;
    private static final int B1_HEIGHT = 16;
    private static final int B1_SOURCE = 234;
    private static final int B2_X = 128;
    private static final int B2_Y = 128;
    private static final int B2_WIDTH = 16;
    private static final int B2_HEIGHT = 16;
    private static final int B2_SOURCE = 234;

    private int getConsoleX() {
        return (this.width - SCREEN_WIDTH) / 2;
    }
    private int getConsoleY() {
        return (this.height - SCREEN_HEIGHT) / 2;
    }
    private List<Game> availableGames = new ArrayList<>();
    private int selected = 0;
    private Game game = new Game();
    public GamingConsoleScreen(Component title) {
        super(title);
        availableGames = scanForGames();
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (game != null) {
                    game.tick();
                }
            }
        });
        timer.start();
    }
    @Override
    public boolean isPauseScreen() {
        return false;
    }
    @Override
    public void render(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(graphics);
        graphics.blit(BACKGROUD, getConsoleX(), getConsoleY(), 0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 256, 256);
        graphics.enableScissor(SCREEN_X + getConsoleX(), SCREEN_Y + getConsoleY(), SCREEN_X + getConsoleX() + Game.WIDTH, SCREEN_Y + getConsoleY() + Game.HEIGHT);
        if (!game.isEmpty()) {
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
        renderButtons(graphics);
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
    }

    private void renderButtons(GuiGraphics graphics) {
        graphics.blit(BACKGROUD, getConsoleX() + W_X, getConsoleY() + W_Y, 0, W_SOURCE, game.controls.isButtonDown(Button.UP) ? SHIFT : 0, W_WIDTH, W_HEIGHT, 256, 256);
        graphics.blit(BACKGROUD, getConsoleX() + A_X, getConsoleY() + A_Y, 0, A_SOURCE, game.controls.isButtonDown(Button.LEFT) ? SHIFT : 0, A_WIDTH, A_HEIGHT, 256, 256);
        graphics.blit(BACKGROUD, getConsoleX() + D_X, getConsoleY() + D_Y, 0, D_SOURCE, game.controls.isButtonDown(Button.RIGHT) ? SHIFT : 0, D_WIDTH, D_HEIGHT, 256, 256);
        graphics.blit(BACKGROUD, getConsoleX() + S_X, getConsoleY() + S_Y, 0, S_SOURCE, game.controls.isButtonDown(Button.DOWN) ? SHIFT : 0, S_WIDTH, S_HEIGHT, 256, 256);
        graphics.blit(BACKGROUD, getConsoleX() + B1_X, getConsoleY() + B1_Y, 0, B1_SOURCE, game.controls.isButtonDown(Button.BUTTON1) ? SHIFT : 0, B1_WIDTH, B1_HEIGHT, 256, 256);
        graphics.blit(BACKGROUD, getConsoleX() + B2_X, getConsoleY() + B2_Y, 0, B2_SOURCE, game.controls.isButtonDown(Button.BUTTON2) ? SHIFT : 0, B2_WIDTH, B2_HEIGHT, 256, 256);
    }
    public List<Game> scanForGames() {
        List<Game> games = new ArrayList<>();
        Player player = Minecraft.getInstance().player;
        //player.displayClientMessage(Component.literal("Scanning"), false);
        for (int i = 0; i < Objects.requireNonNull(player).getInventory().getContainerSize(); i++) {
            //player.displayClientMessage(Component.literal("Item: " + player.getInventory().getItem(i).toString()), false);
            if (player.getInventory().getItem(i).getItem() instanceof GameDiscItem disc) {
                games.removeIf((game) -> game.getClass().equals(disc.getGame().getClass()));
                games.add(disc.getGame());
            }
        }
        return games;
    }
    @Override
    public boolean keyPressed(int key, int pScanCode, int pModifiers) {
        boolean flag = false;
        if (!game.isEmpty()) {
            if (key == 81) {
                game = new Game();
            }
            else if (key == 82) {
                game.prepare();
            }
        }
        if (game != null) {
            switch (key) {
                case W -> {
                    game.controls.setButton(Button.UP, true);
                    flag = true;
                }
                case S -> {
                    game.controls.setButton(Button.DOWN, true);
                    flag = true;
                }
                case A -> {
                    game.controls.setButton(Button.LEFT, true);
                    flag = true;
                }
                case D -> {
                    game.controls.setButton(Button.RIGHT, true);
                    flag = true;
                }
                case SPACE -> {
                    game.controls.setButton(Button.BUTTON1, true);
                    flag = true;
                }
                case ENTER -> {
                    game.controls.setButton(Button.BUTTON2, true);
                    flag = true;
                }
            }
        }
        assert game != null;
        if (game.isEmpty()) {
            if (key == W) {
                int newSelected = selected - 1;
                if (newSelected < 0) {
                    newSelected = availableGames.size() - 1;
                }
                selected = newSelected;
            }
            if (key == S) {
                int newSelected = selected + 1;
                if (newSelected > availableGames.size() - 1) {
                    newSelected = 0;
                }
                selected = newSelected;
            }
            if ((key == SPACE || key == ENTER) && !availableGames.isEmpty()) {
                Game newGame = availableGames.get(selected);
                newGame.prepare();
                game = newGame;
            }
        }
        return (super.keyPressed(key, pScanCode, pModifiers) || flag);
    }
    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        boolean flag = false;
        //Minecraft.getInstance().player.displayClientMessage(Component.literal("Key: " + pKeyCode), false);
        if (game != null) {
            switch (pKeyCode) {
                case W -> {
                    game.controls.setButton(Button.UP, false);
                    flag = true;
                }
                case S -> {
                    game.controls.setButton(Button.DOWN, false);
                    flag = true;
                }
                case A -> {
                    game.controls.setButton(Button.LEFT, false);
                    flag = true;
                }
                case D -> {
                    game.controls.setButton(Button.RIGHT, false);
                    flag = true;
                }
                case SPACE -> {
                    game.controls.setButton(Button.BUTTON1, false);
                    flag = true;
                }
                case ENTER -> {
                    game.controls.setButton(Button.BUTTON2, false);
                    flag = true;
                }
            }
        }
        return (super.keyPressed(pKeyCode, pScanCode, pModifiers) || flag);
    }
}
