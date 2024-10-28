package net.tejty.gamediscs.games.util;

import net.minecraft.util.math.Vec2f;

import java.util.List;
import java.util.Random;
public class VecUtil {
    // Direction constants as integers
    public static int UP = 0;
    public static int RIGHT = 1;
    public static int DOWN = 2;
    public static int LEFT = 3;

    // Direction constants as relative Vec2f
    public static Vec2f VEC_UP = new Vec2f(0, -1);
    public static Vec2f VEC_RIGHT = new Vec2f(1, 0);
    public static Vec2f VEC_DOWN = new Vec2f(0, 1);
    public static Vec2f VEC_LEFT = new Vec2f(-1, 0);
    public static final List<Vec2f> RELATIVES = List.of(
            new Vec2f(-1, -1),
            new Vec2f(0, -1),
            new Vec2f(1, -1),
            new Vec2f(1, 0),
            new Vec2f(1, 1),
            new Vec2f(0, 1),
            new Vec2f(-1, 1),
            new Vec2f(-1, 0)
    );

    /**
     * @param xy X and Y
     * @return Vec2f made of xy, and xy
     */
    public static Vec2f of(float xy) {
        return new Vec2f(xy, xy);
    }

    /**
     * Get four directional direction
     * @param pos1 Position 1
     * @param pos2 Position 2
     * @return Integer of four directional directions
     * @see VecUtil#getFrom(int) for transfering four dir. direction into relative Vec2f
     */
    public static int get4DirectionTo(Vec2f pos1, Vec2f pos2) {
        Vec2f pos = pos2.add(pos1.negate());
        return get4Direction(pos);
    }

    /**
     * Get four directional direction
     * @param pos Position of the direction measuring
     * @return Integer of four directional directions
     * @see VecUtil#getFrom(int) for transfering four dir. direction into relative Vec2f
     */
    public static int get4Direction(Vec2f pos) {
        if (pos.x > pos.y) {
            if (pos.x + pos.y > 0) {
                return RIGHT;
            }
            else {
                return UP;
            }
        }
        else {
            if (pos.x + pos.y > 0) {
                return DOWN;
            }
            else {
                return LEFT;
            }
        }
    }

    /**
     * @param direction Integer value of four directional direction
     * @return Vec2f relative direction
     */
    public static Vec2f getFrom(int direction) {
        return switch (direction) {
            case 0 -> VEC_UP;
            case 1 -> VEC_RIGHT;
            case 2 -> VEC_DOWN;
            case 3 -> VEC_LEFT;
            default -> Vec2f.ZERO;
        };
    }

    /**
     * @return True if pos1 is the same as pos2, false otherwise
     */
    public static boolean is(Vec2f pos1, Vec2f pos2) {
        return (pos1.x == pos2.x && pos1.y == pos2.y);
    }

    /**
     * @param min Minimal Vec2f
     * @param max Maximal Vec2f
     * @param random Random generator
     * @return Random Vec2f Integer position within the range of min and max
     */
    public static Vec2f randomInt(Vec2f min, Vec2f max, Random random) {
        return new Vec2f(random.nextInt((int)min.x, (int)max.x), random.nextInt((int)min.y, (int)max.y));
    }

    /**
     * @param min Minimal Vec2f
     * @param max Maximal Vec2f
     * @param random Random generator
     * @return Random Vec2f float position within the range of min and max
     */
    public static Vec2f randomFloat(Vec2f min, Vec2f max, Random random) {
        return new Vec2f(random.nextFloat(min.x, max.x), random.nextFloat(min.y, max.y));
    }

    /**
     * @return Rounded vector
     */
    public static Vec2f round(Vec2f vec) {
        return new Vec2f(Math.round(vec.x), Math.round(vec.y));
    }
}
