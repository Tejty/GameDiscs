package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.util.*;

public class PongGame extends Game {
    // Here you can create variables for the game

    public PongGame() {
        super();
    }

    @Override
    public synchronized void prepare() {
        // Calls prepare of super
        super.prepare();

        // Resets everything
        // here
    }

    @Override
    public synchronized void start() {
        // Calls start of super
        super.start();

        // Make everything on start
        // here
    }
    @Override
    public synchronized void tick() {
        // Calls tick of super
        super.tick();

        // Make animation tick or anything that ticks even if the game is in DIED state
        // here
    }
    @Override
    public synchronized void gameTick() {
        // Calls game tick of super
        super.gameTick();

        // Ticks all sprites and everything that ticks only when the game is running
        // here
        // Example: yourSprite.tick();
    }

    @Override
    public synchronized void die() {
        // Calling die of super
        super.die();

        // Execute everything that happens when player dies
        // here
        // Example: yourSprite.hide();
    }

    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        // Calls render of super
        super.render(graphics, posX, posY);

        // Renders all sprites and everything
        // here
        // Example: yourSprite.render(graphics, posX, poY);

        // Renders particles
        renderParticles(graphics, posX, posY);

        // Renders overlay
        renderOverlay(graphics, posX, posY);
    }
    @Override
    public synchronized void buttonDown(Button button) {
        // Calls buttonDown of super
        super.buttonDown(button);

        // Execute code when a specific button is pressed
        // here
        // Example: if (button == Button.UP) { [Do something] }
        // Example: if (button.isActionButton()) { [Do something] }
    }
    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation("gamediscs:textures/games/background/pong_background.png");
    }
    @Override
    public boolean showScoreBox() {
        return false;
    }
    @Override
    public int scoreColor() {
        // Color for score text
        return ChatFormatting.YELLOW.getColor();
    }
    @Override
    public Component getName() {
        // Change to name of your game
        return Component.translatable("gamediscs.pong");
    }
    @Override
    public ResourceLocation getIcon() {
        // Change icon here:
        return new ResourceLocation("gamediscs:textures/item/game_disc_pong_no_anim.png");
    }
}