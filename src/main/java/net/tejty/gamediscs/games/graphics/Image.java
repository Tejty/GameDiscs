package net.tejty.gamediscs.games.graphics;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

public class Image extends Renderer {
    private final Identifier file;
    private final int fileWidth, fileHeight, x, y, width, height;

    public Image(Identifier file, int width, int height) {
        this(file, width, height, 0, 0, width, height);
    }
    public Image(Identifier file, int fileWidth, int fileHeight, int x, int y, int width, int height) {
        this.file = file;
        this.fileWidth = fileWidth;
        this.fileHeight = fileHeight;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(DrawContext graphics, int posX, int posY) {
        graphics.drawTexture(RenderLayer::getGuiTextured, file, posX, posY, x, y, width, height, fileWidth, fileHeight);
    }
}
