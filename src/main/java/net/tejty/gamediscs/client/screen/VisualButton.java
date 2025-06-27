package net.tejty.gamediscs.client.screen;

import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

public record VisualButton(
        Identifier image,
        int imageWidth,
        int imageHeight,
        int x,
        int y,
        int width,
        int height,
        int sourceX,
        int sourceY,
        int shift
) {
    public void render(DrawContext graphics, int x, int y, boolean pressed) {
        graphics.drawTexture(RenderPipelines.GUI_TEXTURED, image, x + this.x,
                y + this.y, sourceX, pressed ? this.shift + sourceY : sourceY,
                width, height, imageWidth, imageHeight);
    }
}

