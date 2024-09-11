package net.tejty.gamediscs.games.util;

import net.minecraft.world.phys.Vec2;

import java.util.Random;
public class VecUtil {
    public static int UP = 0;
    public static int RIGHT = 1;
    public static int DOWN = 2;
    public static int LEFT = 3;
    public static Vec2 VEC_UP = new Vec2(0, -1);
    public static Vec2 VEC_RIGHT = new Vec2(1, 0);
    public static Vec2 VEC_DOWN = new Vec2(0, 1);
    public static Vec2 VEC_LEFT = new Vec2(-1, 0);
    public static Vec2 of(float xy) {
        return new Vec2(xy, xy);
    }
    public static int get4DirectionTo(Vec2 pos1, Vec2 pos2) {
        Vec2 pos = pos2.add(pos1.negated());
        return get4Direction(pos);
    }
    public static int get4Direction(Vec2 pos) {
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
    public static Vec2 getFrom(int direction) {
        return switch (direction) {
            case 0 -> VEC_UP;
            case 1 -> VEC_RIGHT;
            case 2 -> VEC_DOWN;
            case 3 -> VEC_LEFT;
            default -> Vec2.ZERO;
        };
    }
    public static boolean is(Vec2 pos1, Vec2 pos2) {
        return (pos1.x == pos2.x && pos1.y == pos2.y);
    }
    public static Vec2 randomInt(Vec2 min, Vec2 max, Random random) {
        return new Vec2(random.nextInt((int)min.x, (int)max.x), random.nextInt((int)min.y, (int)max.y));
    }
    public static Vec2 randomFloat(Vec2 min, Vec2 max, Random random) {
        return new Vec2(random.nextFloat(min.x, max.x), random.nextFloat(min.y, max.y));
    }
}
