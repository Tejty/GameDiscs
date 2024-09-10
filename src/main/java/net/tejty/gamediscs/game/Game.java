package net.tejty.gamediscs.game;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.tejty.gamediscs.game.controls.Button;
import net.tejty.gamediscs.game.controls.Controls;
import net.tejty.gamediscs.item.ModItems;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;
import net.tejty.gamediscs.networking.ModMessages;
import net.tejty.gamediscs.networking.packet.SetBestScoreC2SPacket;

import java.awt.*;
import java.util.Random;

public class Game {
    public GameStage stage = GameStage.START;

    public Controls controls = new Controls(this);

    public static final int WIDTH = 140;
    public static final int HEIGHT = 100;

    public final Random random = new Random();

    public int ticks = 0;
    public int score = 0;

    public Game() {

    }

    public synchronized void prepare() {
        //LogUtils.getLogger().debug("PREPARING GAME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        stage = GameStage.START;
        ticks = 1;
    }

    public synchronized void start() {
        //LogUtils.getLogger().debug("STARTING GAME!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        score = 0;
        stage = GameStage.PLAYING;
        ticks = 1;
    }

    public synchronized  void die() {
        if (getConsole().getItem() instanceof GamingConsoleItem consoleItem) {
            if (consoleItem.getBestScore(getConsole(), this.getClass().getName().substring(this.getClass().getPackageName().length() + 1), Minecraft.getInstance().player) < score) {
                ModMessages.sendToServer(new SetBestScoreC2SPacket(this.getClass().getName().substring(this.getClass().getPackageName().length() + 1), score));
            }
        }
        stage = GameStage.DIED;
        ticks = 1;
    }

    private ItemStack getConsole() {
        Player player = Minecraft.getInstance().player;

        ItemStack item = player.getMainHandItem();
        if (item.getItem() instanceof GamingConsoleItem) {
            return item;
        }
        else {
            item = player.getOffhandItem();
            if (item.getItem() instanceof GamingConsoleItem) {
                return item;
            }
        }
        return new ItemStack(ModItems.GAMING_CONSOLE.get());
    }

    public synchronized void tick() {
        if (stage == GameStage.PLAYING) {
            gameTick();
        }
        ticks++;
    }
    public synchronized void gameTick() {

    }

    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        if (getBackground() != null) {
            graphics.blit(getBackground(), posX, posY, 0, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
        }
        renderOverlay(graphics, posX, posY);
    }

    public synchronized void renderOverlay(GuiGraphics graphics, int posX, int posY) {
        Font font = Minecraft.getInstance().font;
        if (stage != GameStage.PLAYING) {
            graphics.drawString(
                    font,
                    Component.translatable("gui.gamingconsole.press_any_key"),
                    posX + (WIDTH - font.width(Component.translatable("gui.gamingconsole.press_any_key").getVisualOrderText())) / 2 + 1,
                    posY + HEIGHT - font.lineHeight + 1,
                    0x373737,
                    false
            );
            graphics.drawString(
                    font,
                    Component.translatable("gui.gamingconsole.press_any_key"),
                    posX + (WIDTH - font.width(Component.translatable("gui.gamingconsole.press_any_key").getVisualOrderText())) / 2,
                    posY + HEIGHT - font.lineHeight,
                    0xFFFFFF,
                    false
            );
            if (stage == GameStage.DIED || stage == GameStage.WON) {
                graphics.blit(new ResourceLocation("gamediscs:textures/gui/score_board.png"), posX, posY, 0, 0, 0, 140, 100, 140, 100);

                Component component = stage == GameStage.DIED ? Component.translatable("gui.gamingconsole.died").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_RED) : Component.translatable("gui.gamingconsole.won").withStyle(ChatFormatting.BOLD, ChatFormatting.DARK_GREEN);

                graphics.drawString(
                        font,
                        component,
                        posX + (WIDTH - font.width(component.getVisualOrderText())) / 2,
                        posY + 29,
                        component.getStyle().getColor().getValue(),
                        false
                );
                graphics.drawString(
                        font,
                        component,
                        posX + (WIDTH - font.width(component.getVisualOrderText())) / 2,
                        posY + 31,
                        component.getStyle().getColor().getValue(),
                        false
                );
                graphics.drawString(
                        font,
                        component,
                        posX + (WIDTH - font.width(component.getVisualOrderText())) / 2 + 1,
                        posY + 30,
                        component.getStyle().getColor().getValue(),
                        false
                );
                graphics.drawString(
                        font,
                        component,
                        posX + (WIDTH - font.width(component.getVisualOrderText())) / 2 - 1,
                        posY + 30,
                        component.getStyle().getColor().getValue(),
                        false
                );

                component = stage == GameStage.DIED ? Component.translatable("gui.gamingconsole.died").withStyle(ChatFormatting.BOLD, ChatFormatting.RED) : Component.translatable("gui.gamingconsole.won").withStyle(ChatFormatting.BOLD, ChatFormatting.GREEN);

                graphics.drawString(
                        font,
                        component,
                        posX + (WIDTH - font.width(component.getVisualOrderText())) / 2,
                        posY + 30,
                        component.getStyle().getColor().getValue(),
                        false
                );




                component = Component.translatable("gui.gamingconsole.score").append(": ").append(String.valueOf(score)).withStyle(ChatFormatting.YELLOW);
                graphics.drawString(
                        font,
                        component,
                        posX + (WIDTH - font.width(component.getVisualOrderText())) / 2,
                        posY + 35 + font.lineHeight,
                        component.getStyle().getColor().getValue(),
                        false
                );
                int bestScore = GamingConsoleItem.getBestScore(getConsole(), this.getClass().getName().substring(this.getClass().getPackageName().length() + 1), Minecraft.getInstance().player);
                component = Component.translatable("gui.gamingconsole.best_score").append(": ").append(String.valueOf(bestScore)).withStyle(ChatFormatting.YELLOW);
                graphics.drawString(
                        font,
                        component,
                        posX + (WIDTH - font.width(component.getVisualOrderText())) / 2,
                        posY + 50 + font.lineHeight,
                        component.getStyle().getColor().getValue(),
                        false
                );
            }
        }
        else {
            graphics.drawString(
                    font,
                    Component.translatable("gui.gamingconsole.score").append(": ").append(String.valueOf(score)),
                    posX + 3,
                    posY + 3,
                    0x373737,
                    false
            );
            graphics.drawString(
                    font,
                    Component.translatable("gui.gamingconsole.score").append(": ").append(String.valueOf(score)),
                    posX + 2,
                    posY + 2,
                    0xFFFFFF,
                    false
            );
        }
    }

    public synchronized void buttonDown(Button button) {
        if ((stage == GameStage.START || stage == GameStage.RETRY) && ticks > 8) {
            start();
        }
        else if ((stage == GameStage.WON || stage == GameStage.DIED) && ticks > 8) {
            prepare();
        }
        //Minecraft.getInstance().player.displayClientMessage(Component.literal("inGame: " + button), false);
    }

    public synchronized void buttonUp(Button button) {

    }

    public ResourceLocation getBackground() {
        return null;
    }
    public Component getName() {return Component.empty();}
    public ResourceLocation getIcon() {return null;}
    public ChatFormatting getColor() {return ChatFormatting.YELLOW;}

    public boolean isEmpty() {
        return this.getClass().equals(Game.class);
    }
}
