package net.tejty.gamediscs.games.util;

// Using this color to prevent text not rendering
public enum Color {
    BLACK(0xFF000000),
    DARK_BLUE(0xFF0000AA),
    DARK_GREEN(0xFF00AA00),
    DARK_AQUA(0xFF00AAAA),
    DARK_RED(0xFFFF0000),
    DARK_PURPLE(0xFFFF00AA),
    GOLD(0xFFFFAA00),
    GRAY(0xFFAAAAAA),
    DARK_GRAY(0xFF555555),
    BLUE(0xFF5555FF),
    GREEN(0xFF55FF55),
    AQUA(0xFF55FFFF),
    RED(0xFFFF5555),
    LIGHT_PURPLE(0xFFFF55FF),
    YELLOW(0xFFFFFF55),
    WHITE(0xFFFFFFFF);
    private final int color;

    Color(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
