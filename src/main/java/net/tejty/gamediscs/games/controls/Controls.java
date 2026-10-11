package net.tejty.gamediscs.games.controls;

import net.tejty.gamediscs.games.util.Game;

import java.util.EnumSet;
import java.util.Set;

public class Controls {
    private final Set<Button> heldButtons = EnumSet.noneOf(Button.class);
    private final Set<Button> skipNextRepeat = EnumSet.noneOf(Button.class);
    private final Game game;

    public Controls(Game game) {
        this.game = game;
    }

    public void setButton(Button button, boolean isDown) {
        if (isDown) {
            if (heldButtons.add(button)) {
                skipNextRepeat.add(button);
                game.buttonDown(button);
            }
        } else if (heldButtons.remove(button)) {
            game.buttonUp(button);
        }
    }

    public void reset() {
        heldButtons.clear();
        skipNextRepeat.clear();
    }

    public boolean isButtonDown(Button button) {
        return heldButtons.contains(button);
    }

    public boolean wasButtonDown(Button button) {
        // The press callback already handled the first movement; tick repeats skip it.
        return !skipNextRepeat.remove(button);
    }
}
