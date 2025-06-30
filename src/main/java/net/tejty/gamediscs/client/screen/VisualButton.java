package net.tejty.gamediscs.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.ResourceLocation;

public record VisualButton(
        ResourceLocation image,
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
    public void render(GuiGraphics graphics, int x, int y, boolean pressed) {
        graphics.blit(RenderPipelines.GUI_TEXTURED, image, x + this.x, y + this.y, sourceX, pressed ? this.shift + sourceY : sourceY, width, height, imageWidth, imageHeight);
    }
}

