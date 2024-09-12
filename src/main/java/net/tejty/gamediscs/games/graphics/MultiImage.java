package net.tejty.gamediscs.games.graphics;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class MultiImage extends Renderer {
    private List<Image> images = new ArrayList<>();
    private int current = 0;

    public MultiImage(List<Image>images) {
        this.images = images;
    }

    public MultiImage(ResourceLocation file, int fileWidth, int fileHeight, List<Rect2i> rects) {
        for (Rect2i rect : rects) {
            images.add(new Image(file, fileWidth, fileHeight, rect.getX(), rect.getY(), rect.getWidth(), rect.getHeight()));
        }
    }

    public void setImage(int index) {
        current = Math.min(Math.max(index, 0), count() - 1);
    }

    public int count() {
        return images.size();
    }

    @Override
    public void render(GuiGraphics graphics, int posX, int posY) {
        if (count() > 0) {
            images.get(current).render(graphics, posX, posY);
        }
    }
}
