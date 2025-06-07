package net.tejty.gamediscs.games.graphics;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.Random;

public class BreakParticleRenderer extends Renderer {
    private static final int SIZE = 3;
    private final Identifier file;
    private final int fileWidth;
    private final int fileHeight;
    private final int x;
    private final int y;
    public BreakParticleRenderer(Identifier file, int fileWidth, int fileHeight) {
        this.file = file;
        this.fileWidth = fileWidth;
        this.fileHeight = fileHeight;
        Random random = new Random();
        x = random.nextInt(0, fileWidth - SIZE);
        y = random.nextInt(0, fileHeight - SIZE);
    }

    @Override
    public void render(DrawContext graphics, int posX, int posY) {
        graphics.drawTexture(RenderLayer::getGuiTextured, file, posX - SIZE / 2, posY - SIZE / 2, 0, x, y, SIZE, SIZE, fileWidth, fileHeight);
    }
}
