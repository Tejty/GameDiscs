package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.util.GameStage;
import net.tejty.gamediscs.games.util.Sprite;
import net.tejty.gamediscs.games.util.VecUtil;
import net.tejty.gamediscs.games.controls.Button;

import java.util.ArrayList;
import java.util.List;
public class SlimeGame extends Game {
    private static final ResourceLocation HEAD_LEFT = new ResourceLocation("gamediscs:textures/games/sprite/slime_head_left.png");
    private static final ResourceLocation HEAD_RIGHT = new ResourceLocation("gamediscs:textures/games/sprite/slime_head_right.png");
    private static final ResourceLocation HEAD_DOWN = new ResourceLocation("gamediscs:textures/games/sprite/slime_head_down.png");
    private static final ResourceLocation HEAD_UP = new ResourceLocation("gamediscs:textures/games/sprite/slime_head_up.png");
    private static final ResourceLocation BODY = new ResourceLocation("gamediscs:textures/games/sprite/slime_body.png");
    private static final ResourceLocation TAIL_LEFT = new ResourceLocation("gamediscs:textures/games/sprite/slime_tail_left.png");
    private static final ResourceLocation TAIL_RIGHT = new ResourceLocation("gamediscs:textures/games/sprite/slime_tail_right.png");
    private static final ResourceLocation TAIL_DOWN = new ResourceLocation("gamediscs:textures/games/sprite/slime_tail_down.png");
    private static final ResourceLocation TAIL_UP = new ResourceLocation("gamediscs:textures/games/sprite/slime_tail_up.png");
    private static final ResourceLocation CONNECT_HORIZONTAL = new ResourceLocation("gamediscs:textures/games/sprite/slime_connect_horizontal.png");
    private static final ResourceLocation CONNECT_VERTICAL = new ResourceLocation("gamediscs:textures/games/sprite/slime_connect_vertical.png");
    private static final ResourceLocation APPLE = new ResourceLocation("gamediscs:textures/games/sprite/apple.png");
    private static final Vec2 GAME_POS = new Vec2(6, 2);
    private static final int TILE_SIZE = 8;
    private static final int GAME_WIDTH = 16;
    private static final int GAME_HEIGHT = 12;
    private static final List<Vec2> BLOCKED = List.of(
            new Vec2(0, 0),
            new Vec2(1, 0),
            new Vec2(2, 0),
            new Vec2(3, 0),
            new Vec2(4, 0),
            new Vec2(5, 0),
            new Vec2(6, 0)
    );
    private List<Vec2> snake = new ArrayList<>();
    private Vec2 direction = VecUtil.VEC_RIGHT;
    private final Sprite snakeRenderer = new Sprite(Vec2.ZERO, new Vec2(TILE_SIZE, TILE_SIZE), BODY);
    private final Sprite apple = new Sprite(Vec2.ZERO, new Vec2(TILE_SIZE, TILE_SIZE), APPLE);
    public SlimeGame() {
        super();
    }
    @Override
    public synchronized void prepare() {
        super.prepare();
        snake = new ArrayList<>();
        snake.add(new Vec2(5, 5));
        snake.add(new Vec2(6, 5));
        snake.add(new Vec2(7, 5));
        respawnApple();
    }
    @Override
    public synchronized void start() {
        super.start();
        direction = VecUtil.VEC_RIGHT;
    }
    @Override
    public synchronized void gameTick() {
        super.gameTick();
        Vec2 newPos = snake.get(snake.size() - 1).add(direction);
        if (newPos.x >= GAME_WIDTH || newPos.x < 0 || newPos.y >= GAME_HEIGHT || newPos.y < 0) {
            die();
        }
        for (int i = 0; i < snake.size(); i++) {
            Vec2 part = snake.get(i);
            if (i != 0 && VecUtil.is(part, newPos)) {
                die();
            }
        }
        for (Vec2 block : BLOCKED) {
            if (VecUtil.is(block, newPos)) {
                die();
            }
        }
        if (stage == GameStage.PLAYING) {
            snake.add(newPos);
            if (!VecUtil.is(newPos, calcTile(apple.getPos()))) {
                snake.remove(snake.get(0));
            }
            else {
                respawnApple();
                score++;
            }
        }
    }
    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        super.render(graphics, posX, posY);
        apple.render(graphics, posX, posY);
        for (int i = snake.size() - 1; i >= 0; i--) {
            if (i == 0) {
                snakeRenderer.setImage(getTail(VecUtil.get4DirectionTo(snake.get(0), snake.get(1))));
            }
            else if (i == snake.size() - 1) {
                snakeRenderer.setImage(getHead(VecUtil.get4DirectionTo(snake.get(snake.size()-2), snake.get(snake.size() - 1))));
            }
            else {
                snakeRenderer.setImage(BODY);
            }
            Vec2 part = snake.get(i);
            snakeRenderer.setPos(calcPos(part));
            snakeRenderer.render(graphics, posX, posY);
            if (i + 1 < snake.size()) {
                snakeRenderer.setPos(calcPos(part.add(VecUtil.getFrom(VecUtil.get4DirectionTo(part, snake.get(i+1))).scale(0.5f))));
                snakeRenderer.setImage(getConnect(VecUtil.get4DirectionTo(part, snake.get(i+1))));
                snakeRenderer.render(graphics, posX, posY);
            }
        }
        renderOverlay(graphics, posX, posY);
    }
    private void respawnApple() {
        boolean valid = false;
        while (!valid) {
            apple.setPos(calcPos(VecUtil.randomInt(Vec2.ZERO, new Vec2(GAME_WIDTH, GAME_HEIGHT), random)));
            valid = true;
            Vec2 applePos = calcTile(apple.getPos());
            for (Vec2 vec2 : snake) {
                if (VecUtil.is(vec2, applePos)) {
                    valid = false;
                    break;
                }
            }
            for (Vec2 block : BLOCKED) {
                if (VecUtil.is(block, applePos)) {
                    valid = false;
                    break;
                }
            }
        }
    }
    private ResourceLocation getHead(int direction) {
        return switch (direction) {
            case 0 -> HEAD_UP;
            case 1 -> HEAD_RIGHT;
            case 3 -> HEAD_LEFT;
            default -> HEAD_DOWN;
        };
    }
    private ResourceLocation getTail(int direction) {
        return switch (direction) {
            case 0 -> TAIL_UP;
            case 1 -> TAIL_RIGHT;
            case 3 -> TAIL_LEFT;
            default -> TAIL_DOWN;
        };
    }
    private ResourceLocation getConnect(int direction) {
        return switch (direction) {
            case 0, 2 -> CONNECT_VERTICAL;
            default -> CONNECT_HORIZONTAL;
        };
    }
    private Vec2 calcTile(Vec2 pos) {
        return pos.add(GAME_POS.negated()).scale((float) 1 / TILE_SIZE);
    }
    private Vec2 calcPos(Vec2 tile) {
        return tile.scale(TILE_SIZE).add(GAME_POS);
    }
    @Override
    public synchronized void buttonDown(Button button) {
        super.buttonDown(button);
        if (stage == GameStage.PLAYING) {
            switch (button) {
                case UP -> direction = VecUtil.VEC_UP;
                case RIGHT -> direction = VecUtil.VEC_RIGHT;
                case DOWN -> direction = VecUtil.VEC_DOWN;
                case LEFT -> direction = VecUtil.VEC_LEFT;
            }
        }
    }
    @Override
    public int gameTickDuration() {
        return 5;
    }
    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation("gamediscs:textures/games/background/slime_background.png");
    }
    @Override
    public Component getName() {
        return Component.translatable("gamediscs.slime");
    }
    @Override
    public ResourceLocation getIcon() {
        return new ResourceLocation("gamediscs:textures/item/game_disc_slime.png");
    }
}
