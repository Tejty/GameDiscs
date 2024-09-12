package net.tejty.gamediscs.games.controls;

import net.tejty.gamediscs.games.util.Game;
public class Controls {
    // Stores state of all buttons
    private boolean UP;
    private boolean DOWN;
    private boolean LEFT;
    private boolean RIGHT;
    private boolean BUTTON1;
    private boolean BUTTON2;

    // Stores the game
    private final Game game;
    public Controls(Game game) {
        this.game = game;
    }

    /**
     * Sets button's state, and calls key change event in the game
     * @param button Button that is affected
     * @param isDown True means pressed, false means not pressed
     */
    public void setButton(Button button, boolean isDown) {
        // Determines if the event should be called (if the button was already pressed, the event will not be called)
        boolean callEvent = false;
        switch (button) {
            // If the button is [button], changes the [button]'s state to [isDown],
            // and sets callEvent to true if the button wasn't in the state before
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
        // Calls the key changing event in game, if callEvent is true (calls buttonDown(), or buttonUp(), based on the isDown)
        if (isDown) {
            if (callEvent) {
                game.buttonDown(button);
            }
        }
        else {
            game.buttonUp(button);
        }
    }

    /**
     * @param button Button to check for
     * @return true if the button is down, false otherwise
     */
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
