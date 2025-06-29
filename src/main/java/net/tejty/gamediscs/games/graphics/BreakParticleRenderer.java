package net.tejty.gamediscs.games.graphics;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

import java.util.Random;

public class BreakParticleRenderer extends Renderer {
    private static final int SIZE = 3;
    private final ResourceLocation file;
    private final int fileWidth;
    private final int fileHeight;
    private final int x;
    private final int y;
    public BreakParticleRenderer(ResourceLocation file, int fileWidth, int fileHeight) {
        this.file = file;
        this.fileWidth = fileWidth;
        this.fileHeight = fileHeight;
        Random random = new Random();
        x = random.nextInt(0, fileWidth - SIZE);
        y = random.nextInt(0, fileHeight - SIZE);
    }

    @Override
    public void render(GuiGraphics graphics, int posX, int posY) {
        graphics.blit(RenderType::guiTextured, file, posX - SIZE / 2, posY - SIZE / 2, x, y, SIZE, SIZE, fileWidth, fileHeight);
    }
}
