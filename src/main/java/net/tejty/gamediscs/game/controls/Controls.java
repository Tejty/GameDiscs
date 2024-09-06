package net.tejty.gamediscs.game.controls;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.tejty.gamediscs.game.Game;

public class Controls {
    private boolean UP;
    private boolean DOWN;
    private boolean LEFT;
    private boolean RIGHT;
    private boolean BUTTON1;
    private boolean BUTTON2;

    private final Game game;

    public Controls(Game game) {
        this.game = game;
    }

    public void setButton(Button button, boolean isDown) {
        //Minecraft.getInstance().player.displayClientMessage(Component.literal("Setting: " + button), false);
        boolean callEvent = false;

        switch (button) {
            case UP -> {
                if (!UP && isDown) {
                    callEvent = true;
                }
                UP = isDown;
            }
            case DOWN -> {
                if (!DOWN && isDown) {
                    callEvent = true;
                }
                DOWN = isDown;
            }
            case LEFT -> {
                if (!LEFT && isDown) {
                    callEvent = true;
                }
                LEFT = isDown;
            }
            case RIGHT -> {
                if (!RIGHT && isDown) {
                    callEvent = true;
                }
                RIGHT = isDown;
            }
            case BUTTON1 -> {
                if (!BUTTON1 && isDown) {
                    callEvent = true;
                }
                BUTTON1 = isDown;
            }
            case BUTTON2 -> {
                if (!BUTTON2 && isDown) {
                    callEvent = true;
                }
                BUTTON2 = isDown;
            }
        }
        if (isDown) {
            if (callEvent) {
                game.buttonDown(button);
            }
        }
        else {
            game.buttonUp(button);
        }
    }

    public boolean isButtonDown(Button button) {
        return switch (button) {
            case UP -> UP;
            case DOWN -> DOWN;
            case LEFT -> LEFT;
            case RIGHT -> RIGHT;
            case BUTTON1 -> BUTTON1;
            case BUTTON2 -> BUTTON2;
        };
    }
}
