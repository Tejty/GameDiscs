package net.tejty.gamediscs.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class VisualButton {
    private ResourceLocation image;
    private int imageWidth;
    private int imageHeight;
    public int x;
    public int y;
    public int width;
    public int height;
    private int sourceX;
    private int sourceY;
    private int shift;
    public VisualButton(ResourceLocation image, int imageWidth, int imageHeight, int x, int y, int width, int height, int sourceX, int sourceY, int shift) {
        this.image = image;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sourceX = sourceX;
        this.sourceY = sourceY;
        this.shift = shift;
    }

    public void render(GuiGraphics graphics, int x, int y, boolean pressed) {
        graphics.blit(image, x + this.x, y + this.y, 0, sourceX, pressed ? this.shift + sourceY : sourceY, width, height, imageWidth, imageHeight);
    }
}
