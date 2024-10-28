package net.tejty.gamediscs.games.controls;
public enum Button {
    UP, DOWN, LEFT, RIGHT, BUTTON1, BUTTON2;
    public boolean isActionButton() {
        return (this == BUTTON1 || this == BUTTON2);
    }
}
