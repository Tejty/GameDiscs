package net.tejty.gamediscs.games.graphics;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public class ParticleRenderer extends Renderer {
    private Identifier file;
    private final int fileWidth;
    private final int fileHeight;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public ParticleRenderer(Identifier file, int fileWidth, int fileHeight, int x, int y, int width, int height) {

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
        graphics.drawTexture(RenderPipelines.GUI_TEXTURED, file, posX - (int)(0.5 * width), posY - (int)(0.5 * height), x, y, width, height, fileWidth, fileHeight);
    }
}
