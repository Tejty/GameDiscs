package net.tejty.gamediscs.games.graphics;

import net.minecraft.client.renderer.Rect2i;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public class DirectionalImage extends MultiImage {
    public DirectionalImage(ResourceLocation location, int fileWidth, int fileHeight) {
        super(fromFile(location, fileWidth, fileHeight));
    }

    public static List<Image> fromFile(ResourceLocation location, int fileWidth, int fileHeight) {
        List<Image> images = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            images.add(new Image(location, fileWidth, fileHeight, 0, fileHeight / 4 * i, fileWidth, fileHeight / 4));
        }
        return images;
    }

    public DirectionalImage(ResourceLocation file, int fileWidth, int fileHeight, List<Rect2i> rects) {
        super(file, fileWidth, fileHeight, rects);
    }

    @Override
    public int count() {
        return 4;
    }
}
