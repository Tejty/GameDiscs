package net.tejty.gamediscs.games.util;

import net.minecraft.client.gui.GuiGraphics;
import net.tejty.gamediscs.games.graphics.Image;
import net.tejty.gamediscs.games.graphics.MultiImage;

import java.util.List;

public class Grid {
    private final int[][] map;
    private final int size;
    private MultiImage images;

    public Grid(int width, int height, int tileSize, MultiImage images) {
        map = new int[width][height];
        size = tileSize;
        this.images = images;
    }

    public Grid(int width, int height, int tileSize, List<Image> images) {
        map = new int[width][height];
        size = tileSize;
        this.images = new MultiImage(images);
    }

    public int get(int x, int y) {
        return map[x][y];
    }

    public void set(int x, int y, int value) {
        map[x][y] = value;
    }

    public void render(GuiGraphics graphics, int posX, int posY) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[x].length; y++) {
                renderTile(graphics, posX, posY, x, y);
            }
        }
    }

    private void renderTile(GuiGraphics graphics, int posX, int posY, int x, int y) {
        images.setImage(map[x][y]);
        images.render(graphics, posX + x * size, posY + y * size);
    }
}
