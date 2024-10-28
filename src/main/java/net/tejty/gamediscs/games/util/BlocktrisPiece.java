package net.tejty.gamediscs.games.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec2f;
import net.tejty.gamediscs.games.gamediscs.BlocktrisGame;

import java.util.List;
import java.util.function.Supplier;

public class BlocktrisPiece {
    private final List<List<Vec2f>> variants;
    private final BlocktrisGame game;
    private int x;
    private int y;
    private int rotation;
    private int color;
    private final Grid grid;
    private List<Vec2f> altPositions = List.of(
            new Vec2f(0, 1),
            new Vec2f(-1, 1),
            new Vec2f(1, 1),
            new Vec2f(-1, 0),
            new Vec2f(1, 0),
            new Vec2f(0, -1),
            new Vec2f(-1, -1),
            new Vec2f(1, -1)
    );

    public static final Supplier<List<List<Vec2f>>> TRIANGLE = () -> List.of(
            List.of(
                    new Vec2f(0, 0),
                    new Vec2f(0, -1),
                    new Vec2f(1, 0),
                    new Vec2f(0, 1)
            ),
            List.of(
                    new Vec2f(0, 0),
                    new Vec2f(-1, 0),
                    new Vec2f(1, 0),
                    new Vec2f(0, 1)
            ),
            List.of(
                    new Vec2f(0, 0),
                    new Vec2f(0, -1),
                    new Vec2f(-1, 0),
                    new Vec2f(0, 1)
            ),
            List.of(
                    new Vec2f(0, 0),
                    new Vec2f(0, -1),
                    new Vec2f(1, 0),
                    new Vec2f(-1, 0)
            )
    );

    public static final Supplier<List<List<Vec2f>>> J = () -> List.of(
            List.of(
                    new Vec2f(0, -1),
                    new Vec2f(0, 0),
                    new Vec2f(0, 1),
                    new Vec2f(-1, 1)
            ),
            List.of(
                    new Vec2f(-1, -1),
                    new Vec2f(-1, 0),
                    new Vec2f(0, 0),
                    new Vec2f(1, 0)
            ),
            List.of(
                    new Vec2f(0, -1),
                    new Vec2f(0, 0),
                    new Vec2f(0, 1),
                    new Vec2f(1, -1)
            ),
            List.of(
                    new Vec2f(-1, 0),
                    new Vec2f(0, 0),
                    new Vec2f(1, 0),
                    new Vec2f(1, 1)
            )
    );
    public static final Supplier<List<List<Vec2f>>> LINE = () -> List.of(
            List.of(
                    new Vec2f(0, -1),
                    new Vec2f(0, 0),
                    new Vec2f(0, 1),
                    new Vec2f(0, 2)
            ),
            List.of(
                    new Vec2f(-1, 0),
                    new Vec2f(0, 0),
                    new Vec2f(1, 0),
                    new Vec2f(2, 0)
            )
    );
    public static final Supplier<List<List<Vec2f>>> Z = () -> List.of(
            List.of(
                    new Vec2f(-1, 0),
                    new Vec2f(0, 0),
                    new Vec2f(-1, 1),
                    new Vec2f(0, -1)
            ),
            List.of(
                    new Vec2f(-1, 0),
                    new Vec2f(0, 0),
                    new Vec2f(0, 1),
                    new Vec2f(1, 1)
            )
    );
    public static final Supplier<List<List<Vec2f>>> SQUARE = () -> List.of(
            List.of(
                    new Vec2f(0, 0),
                    new Vec2f(1, 0),
                    new Vec2f(0, 1),
                    new Vec2f(1, 1)
            )
    );
    public static final Supplier<List<List<Vec2f>>> L = () -> List.of(
            List.of(
                    new Vec2f(0, -1),
                    new Vec2f(0, 0),
                    new Vec2f(0, 1),
                    new Vec2f(1, 1)
            ),
            List.of(
                    new Vec2f(-1, 1),
                    new Vec2f(-1, 0),
                    new Vec2f(0, 0),
                    new Vec2f(1, 0)
            ),
            List.of(
                    new Vec2f(0, -1),
                    new Vec2f(0, 0),
                    new Vec2f(0, 1),
                    new Vec2f(-1, -1)
            ),
            List.of(
                    new Vec2f(-1, 0),
                    new Vec2f(0, 0),
                    new Vec2f(1, 0),
                    new Vec2f(1, -1)
            )
    );
    public static final Supplier<List<List<Vec2f>>> S = () -> List.of(
            List.of(
                    new Vec2f(0, -1),
                    new Vec2f(0, 0),
                    new Vec2f(1, 0),
                    new Vec2f( 1, 1)
            ),
            List.of(
                    new Vec2f(0, -1),
                    new Vec2f(0, 0),
                    new Vec2f(1, -1),
                    new Vec2f(-1, 0)
            )
    );

    public static final List<Supplier<List<List<Vec2f>>>> PIECES = List.of(
            TRIANGLE,
            J,
            LINE,
            Z,
            SQUARE,
            L,
            S
    );

    public BlocktrisPiece(List<List<Vec2f>> variants, int x, int y, int color, BlocktrisGame game) {
        this.variants = variants;
        this.x = x;
        this.y = y;
        this.color = color;
        this.grid = game.grid;
        this.game = game;
    }

    public List<Vec2f> current() {
        return variants.get(rotation);
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isTouching() {
        for (Vec2f part : current()) {
            if (part.x + x >= grid.width() || part.x + x < 0 || part.y + y >= grid.height() || part.y + y < 0) {
                return true;
            }
            if (grid.get((int)part.x + x, (int)part.y + y) != 0) {
                return true;
            }
        }
        return false;
    }

    public void rotate() {
        rotation += 1;
        if (rotation >= variants.size()) {
            rotation = 0;
        }
        if (isTouching()) {
            int oldX = x;
            int oldY = y;
            int i = 0;
            while (true) {
                x = oldX;
                y = oldY;
                x += altPositions.get(i).x;
                y += altPositions.get(i).y;
                if (!isTouching()) {
                    break;
                }
                i++;
                if (i >= altPositions.size()) {
                    x = oldX;
                    y = oldY;
                    rotation -= 1;
                    if (rotation < 0) {
                        rotation = variants.size() - 1;
                    }
                    break;
                }
            }
        }
    }
    public boolean move(int x, int y) {
        boolean flag = false;
        this.x += x;
        if (isTouching()) {
            this.x -= x;
            flag = true;
        }
        this.y += y;
        if (isTouching()) {
            this.y -= y;
            flag = true;
        }
        return flag;
    }

    public void hardDrop() {
        while (true) {
            if (move(0, 1)) {
                return;
            }
        }
    }

    public void place() {
        for (Vec2f part : current()) {
            grid.set(x + (int)part.x, y + (int)part.y, color + 1);
        }
    }

    public void tick() {
        this.y += 1;
    }

    public void render(DrawContext graphics, int posX, int posY) {
        grid.getImages().setImage(color + 1);
        for (Vec2f part : current()) {
            grid.getImages().render(graphics, posX + (x + (int)part.x) * grid.tileSize(), posY + (y + (int)part.y) * grid.tileSize());
        }
    }

    public void renderCentered(DrawContext graphics, int posX, int posY) {
        grid.getImages().setImage(color + 1);
        Vec2f smallest = null;
        Vec2f biggest = null;
        for (int i = 0; i < current().size(); i++) {
            Vec2f part = current().get(i);
            if (smallest == null) {
                smallest = part;
            }
            if (biggest == null) {
                biggest = part;
            }
            smallest = new Vec2f(Math.min(smallest.x, part.x), Math.min(smallest.y, part.y));
            biggest = new Vec2f(Math.max(biggest.x, part.x), Math.max(biggest.y, part.y));
        }
        Vec2f addition = smallest.add(biggest.negate()).multiply(0.5f).add(biggest).negate();
        for (Vec2f part : current()) {
            grid.getImages().render(graphics, posX + (int)((x + part.x + addition.x) * grid.tileSize()), posY + (int)((y + (int)part.y + addition.y) * grid.tileSize()));
        }
    }

    public void setRotation(int direction) {
        rotation = direction;
        if (rotation >= variants.size()) {
            rotation = variants.size() - 1;
        }
        if (rotation < 0) {
            rotation = 0;
        }
    }
}
