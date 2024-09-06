package net.tejty.gamediscs.game;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.tejty.gamediscs.game.controls.Button;
import net.tejty.gamediscs.game.controls.Controls;

import java.awt.*;
import java.util.Random;

public class Game {
    public GameStage stage = GameStage.START;

    public Controls controls = new Controls(this);

    public static final int WIDTH = 140;
    public static final int HEIGHT = 100;

    public final Random random = new Random();

    public Game() {

    }

    public synchronized void prepare() {
        //LogUtils.getLogger().debug("PREPARING GAME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        stage = GameStage.START;
    }

    public synchronized void start() {
        //LogUtils.getLogger().debug("STARTING GAME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        stage = GameStage.PLAYING;
    }

    public synchronized  void die() {
        stage = GameStage.DIED;
    }

    public synchronized void tick() {
        if (stage == GameStage.PLAYING) {
            gameTick();
        }
    }
    public synchronized void gameTick() {

    }

    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        if (getBackground() != null) {
            graphics.blit(getBackground(), posX, posY, 0, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
        }

        Font font = Minecraft.getInstance().font;
        if (stage != GameStage.PLAYING) {
            graphics.drawString(
                    font,
                    Component.translatable("gui.gamingconsole.press_any_key"),
                    posX + (WIDTH - font.width(Component.translatable("gui.gamingconsole.press_any_key").getVisualOrderText())) / 2 + 1,
                    posY + HEIGHT - font.lineHeight + 1,
                    0x373737,
                    true
            );
            graphics.drawString(
                    font,
                    Component.translatable("gui.gamingconsole.press_any_key"),
                    posX + (WIDTH - font.width(Component.translatable("gui.gamingconsole.press_any_key").getVisualOrderText())) / 2,
                    posY + HEIGHT - font.lineHeight,
                    0xFFFFFF,
                    true
            );
        }
    }

    public synchronized void buttonDown(Button button) {
        if (stage == GameStage.START || stage == GameStage.RETRY) {
            start();
        }
        else if (stage == GameStage.WON || stage == GameStage.DIED) {
            prepare();
        }
        //Minecraft.getInstance().player.displayClientMessage(Component.literal("inGame: " + button), false);
    }

    public synchronized void buttonUp(Button button) {

    }

    public ResourceLocation getBackground() {
        return null;
    }
}
