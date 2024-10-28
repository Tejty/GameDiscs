package net.tejty.gamediscs.games.graphics;

import net.minecraft.client.util.math.Rect2i;
import net.minecraft.util.Identifier;

import java.util.List;

public class DirectionalImage extends MultiImage {
    public DirectionalImage(Identifier location, int fileWidth, int fileHeight) {
        this(location, fileWidth, fileHeight, fromFile(fileWidth, fileHeight, 4));
    }

    public DirectionalImage(Identifier file, int fileWidth, int fileHeight, List<Rect2i> rects) {
        super(file, fileWidth, fileHeight, rects);
    }

    @Override
    public int count() {
        return 4;
    }
}
