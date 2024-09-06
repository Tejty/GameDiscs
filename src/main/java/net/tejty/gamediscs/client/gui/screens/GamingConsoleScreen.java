package net.tejty.gamediscs.client.gui.screens;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.tejty.gamediscs.game.Game;
import net.tejty.gamediscs.game.controls.Button;
import net.tejty.gamediscs.game.games.FlappyBirdGame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
            game.tick();
        }
    });

    private int getConsoleX() {
        return (this.width - SCREEN_WIDTH) / 2;
    }
    private int getConsoleY() {
        return (this.height - SCREEN_HEIGHT) / 2;
    }

    private Game game = new FlappyBirdGame();

    public GamingConsoleScreen(Component title) {
        super(title);

        timer.start();
        game.prepare();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);

        pGuiGraphics.blit(BACKGROUD, getConsoleX(), getConsoleY(), 0, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 256, 256);

        pGuiGraphics.enableScissor(SCREEN_X + getConsoleX(), SCREEN_Y + getConsoleY(), SCREEN_X + getConsoleX() + Game.WIDTH, SCREEN_Y + getConsoleY() + Game.HEIGHT);
        game.render(pGuiGraphics, SCREEN_X + getConsoleX(), SCREEN_Y + getConsoleY());
        pGuiGraphics.disableScissor();

        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        boolean flag = false;

        //Minecraft.getInstance().player.displayClientMessage(Component.literal("Key: " + pKeyCode), false);
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

        return (super.keyPressed(pKeyCode, pScanCode, pModifiers) || flag);
    }
    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        boolean flag = false;

        //Minecraft.getInstance().player.displayClientMessage(Component.literal("Key: " + pKeyCode), false);
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

        return (super.keyPressed(pKeyCode, pScanCode, pModifiers) || flag);
    }
}
