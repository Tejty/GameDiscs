package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.MultiImage;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.util.Sprite;
import net.tejty.gamediscs.games.util.VecUtil;

import java.util.Optional;

public class PongGame extends Game {
    private Sprite player = new Sprite(
            new Vec2(10, (float) HEIGHT / 2 - 10),
            new Vec2(5, 20),
            ResourceLocation.fromNamespaceAndPath("minecraft", "textures/block/white_concrete.png")
    );
    private Sprite oponent = new Sprite(
            new Vec2(WIDTH - 15, (float) HEIGHT / 2 - 10),
            new Vec2(5, 20),
            ResourceLocation.fromNamespaceAndPath("minecraft","textures/block/white_concrete.png")
    );
    private Sprite ball = new Sprite(
            new Vec2((float) WIDTH / 2 - 2, (float) HEIGHT / 2 - 2),
            new Vec2(4, 4),
            ResourceLocation.fromNamespaceAndPath("minecraft","textures/block/white_concrete.png")
    );
    private Sprite numberRenderer = new Sprite(
            new Vec2(0, 0),
            new Vec2(8, 12),
            new MultiImage(
                    ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/numbers.png"),
                    8,
                    120,
                    10
            )
    );

    private static final int SPEED = 3;
    private int oponentScore = 0;
    private int ballTimer;
    private float ballSpeed = 3;

    public PongGame() {
        super();
    }

    @Override
    public void prepare() {
        // Calls prepare of super
        super.prepare();

        // Resets everything
        player = new Sprite(
                new Vec2(10, (float) HEIGHT / 2 - 10),
                new Vec2(5, 20),
                ResourceLocation.fromNamespaceAndPath("minecraft","textures/block/white_concrete.png")
        );
        oponent = new Sprite(
                new Vec2(WIDTH - 15, (float) HEIGHT / 2 - 10),
                new Vec2(5, 20),
                ResourceLocation.fromNamespaceAndPath("minecraft","textures/block/white_concrete.png")
        );
        ballSpeed = 4;
        ball = new Sprite(
                new Vec2((float) WIDTH / 2 - 2, (float) HEIGHT / 2 - 2),
                new Vec2(4, 4),
                ResourceLocation.fromNamespaceAndPath("minecraft","textures/block/white_concrete.png")
        ).setVelocity(new Vec2((random.nextInt(2) * 2 - 1) * 2, (random.nextInt(2) * 2 - 1) * 2));
        oponentScore = 0;
    }

    public void resetBall() {
        ballSpeed = 4;
        ball.setPos(new Vec2((float) WIDTH / 2 - 2, (float) HEIGHT / 2 - 2));
        ball.setVelocity(new Vec2(random.nextInt(2) * 2 - 1, random.nextInt(2) * 2 - 1).normalized().scale(ballSpeed));
        ballTimer = 60;
    }

    @Override
    public void start() {
        // Calls start of super
        super.start();

        // Make everything on start
        ballTimer = 60;
    }
    @Override
    public void tick() {
        // Calls tick of super
        super.tick();

        // Make animation tick or anything that ticks even if the game is in DIED state
        if (ballTimer > 0) {
            ballTimer--;
        }
    }
    @Override
    public void gameTick() {
        // Calls game tick of super
        super.gameTick();

        if (ticks % 20 == 0) {
            ballSpeed += 0.1f;
        }

        // Ticks all sprites and everything that ticks only when the game is running
        if (controls.isButtonDown(Button.UP)) {
            player.moveBy(VecUtil.VEC_UP.scale(SPEED));
        }
        if (controls.isButtonDown(Button.DOWN)) {
            player.moveBy(VecUtil.VEC_DOWN.scale(SPEED));
        }
        if (ball.getCenterPos().y < oponent.getCenterPos().y) {
            oponent.moveBy(VecUtil.VEC_UP.scale(SPEED));
        }
        if (ball.getCenterPos().y > oponent.getCenterPos().y) {
            oponent.moveBy(VecUtil.VEC_DOWN.scale(SPEED));
        }
        player.setY(Math.min(Math.max(player.getY(), 0), HEIGHT - player.getHeight()));
        oponent.setY(Math.min(Math.max(oponent.getY(), 0), HEIGHT - oponent.getHeight()));

        if (ballTimer <= 0) {
            moveBall();

            if (ball.getX() < 0) {
                oponentScore++;
                resetBall();
            }
            if (ball.getX() + ball.getWidth() > WIDTH) {
                score++;
                soundPlayer.playPoint();
                resetBall();
            }
        }

        if (score >= 10) {
            win();
        }
        if (oponentScore >= 10) {
            die();
        }
    }

    private void moveBall() {
        Sprite paddle = ball.getVelocity().x < 0 ? player : oponent;
        float face = paddle == player ? paddle.getX() + paddle.getWidth() : paddle.getX() - ball.getWidth();
        AABB bounds = new AABB(paddle.getX() - ball.getWidth(), paddle.getY() - ball.getHeight(), -1,
                paddle.getX() + paddle.getWidth(), paddle.getY() + paddle.getHeight(), 1);
        double remaining = 1;
        // Trace straight segments in collision order, preserving travel after each wall bounce.
        while (remaining > 0) {
            Vec2 previous = ball.getPos();
            Vec2 velocity = ball.getVelocity();
            float wall = velocity.y < 0 ? 0 : HEIGHT - ball.getHeight();
            double wallTime = velocity.y == 0 ? Double.POSITIVE_INFINITY : (wall - (double) previous.y) / velocity.y;
            boolean touching = ball.isTouching(paddle)
                    || (previous.x == face && previous.y > bounds.minY && previous.y < bounds.maxY);
            ball.moveBy(velocity.scale((float) Math.min(remaining, wallTime)));
            Optional<Vec3> hit = touching ? Optional.of(new Vec3(previous.x, previous.y, 0))
                    : bounds.clip(new Vec3(previous.x, previous.y, 0), new Vec3(ball.getX(), ball.getY(), 0));
            if (hit.isPresent() || ball.isTouching(paddle)) {
                hit.ifPresent(pos -> ball.setY((float) pos.y));
                ball.setX(face);
                ball.setVelocity(ball.getCenterPos().add(paddle.getCenterPos().add(new Vec2(paddle == player ? -2 : 2, 0)).negated()).normalized().scale(ballSpeed));
                soundPlayer.playJump();
                return;
            }
            if (wallTime > remaining || ball.getX() < 0 || ball.getX() + ball.getWidth() > WIDTH) {
                return;
            }
            ball.setY(wall);
            ball.setVelocity(new Vec2(velocity.x, -velocity.y));
            soundPlayer.playJump();
            remaining -= wallTime;
        }
    }

    @Override
    public void die() {
        // Calling die of super
        super.die();

        // Execute everything that happens when player dies
        // here
        // Example: yourSprite.hide();
    }

    @Override
    public void render(GuiGraphics graphics, int posX, int posY) {
        // Calls render of super
        super.render(graphics, posX, posY);

        // Renders all sprites and everything
        player.render(graphics, posX, posY);
        oponent.render(graphics, posX, posY);
        ball.render(graphics, posX, posY);

        // Renders particles
        renderParticles(graphics, posX, posY);

        // Render score
        if (score < 10) {
            if (numberRenderer.getImage() instanceof MultiImage image) {
                image.setImage(score);
            }
            numberRenderer.setPos(new Vec2((float) WIDTH / 2 - numberRenderer.getWidth() - 4, 4));
            numberRenderer.render(graphics, posX, posY);
        }
        else {
            if (numberRenderer.getImage() instanceof MultiImage image) {
                image.setImage(1);
            }
            numberRenderer.setPos(new Vec2((float) WIDTH / 2 - numberRenderer.getWidth() * 2 - 4 * 2, 4));
            numberRenderer.render(graphics, posX, posY);
            if (numberRenderer.getImage() instanceof MultiImage image) {
                image.setImage(0);
            }
            numberRenderer.setPos(new Vec2((float) WIDTH / 2 - numberRenderer.getWidth() - 4, 4));
            numberRenderer.render(graphics, posX, posY);
        }


        if (oponentScore < 10) {
            if (numberRenderer.getImage() instanceof MultiImage image) {
                image.setImage(oponentScore);
            }
            numberRenderer.setPos(new Vec2((float) WIDTH / 2 + 4, 4));
            numberRenderer.render(graphics, posX, posY);
        }
        else {
            if (numberRenderer.getImage() instanceof MultiImage image) {
                image.setImage(1);
            }
            numberRenderer.setPos(new Vec2((float) WIDTH / 2 + 4, 4));
            numberRenderer.render(graphics, posX, posY);
            if (numberRenderer.getImage() instanceof MultiImage image) {
                image.setImage(0);
            }
            numberRenderer.setPos(new Vec2((float) WIDTH / 2 + numberRenderer.getWidth() + 4 * 2, 4));
            numberRenderer.render(graphics, posX, posY);
        }

        // Renders overlay
        renderOverlay(graphics, posX, posY);
    }
    @Override
    public void buttonDown(Button button) {
        // Calls buttonDown of super
        super.buttonDown(button);

        // Execute code when a specific button is pressed
    }
    @Override
    public ResourceLocation getBackground() {
        return ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/background/pong_background.png");
    }
    @Override
    public boolean showScore() {
        return false;
    }
    @Override
    public Component getName() {
        // Change to name of your game
        return Component.translatable("gamediscs.pong");
    }
    @Override
    public ResourceLocation getIcon() {
        // Change icon here:
        return ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/item/game_disc_pong_no_anim.png");
    }
}
