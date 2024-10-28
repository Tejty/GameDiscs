package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.AnimatedImage;
import net.tejty.gamediscs.games.graphics.BreakParticleRenderer;
import net.tejty.gamediscs.games.graphics.ParticleColor;
import net.tejty.gamediscs.games.util.*;

import java.util.ArrayList;
import java.util.List;
public class FlappyBirdGame extends Game {
    // The main player Sprite
    private Sprite bird = new Sprite(new Vec2f(20, 30), new Vec2f(10, 8),
            new AnimatedImage(
                    new Identifier(GameDiscsMod.MOD_ID, "textures/games/sprite/bird.png"),
                    10, 32, 4, 2)
    );
    // List of pipes
    private List<Sprite> pipes = new ArrayList<Sprite>();
    // Ground Sprite
    private final Sprite ground = new Sprite(new Vec2f(0, HEIGHT - 16), new Vec2f(156, 16),
            new Identifier(GameDiscsMod.MOD_ID, "textures/games/sprite/ground.png"));

    // Countdown of spawning another pillar
    private int pipeSpawnTimer = 0;

    public FlappyBirdGame() {
        super();
    }

    @Override
    public synchronized void prepare() {
        // Calls prepare of super
        super.prepare();

        // Resets everything
        bird = new Sprite(new Vec2f(20, 30), new Vec2f(10, 8),
                new AnimatedImage(
                        new Identifier(GameDiscsMod.MOD_ID, "textures/games/sprite/bird.png"),
                        10, 32, 4, 2)
        );
        pipes = new ArrayList<>();
        ground.setVelocity(new Vec2f(-2.5f, 0));
        bird.show();
    }

    @Override
    public synchronized void start() {
        // Calls start of super
        super.start();

        // Resets pillar spawn timer
        pipeSpawnTimer = 0;
    }
    @Override
    public synchronized void tick() {
        // Calls tick of super
        super.tick();

        // Animating bee
        if (stage != GameStage.DIED) {
            bird.animTick();
        }

        // If it's not died or won, the ground moves
        if (stage != GameStage.DIED && stage != GameStage.WON) {
            ground.tick();
            if (ground.getX() <= -16) {
                ground.moveBy(new Vec2f(16, 0));
            }
        }
    }
    @Override
    public synchronized void gameTick() {
        // Calls game tick of super
        super.gameTick();

        // Ticks the bird
        bird.tick();

        // Adds velocity down to the bird (gravity)
        bird.addVelocity(new Vec2f(0, 0.75f));

        // Makes air friction to the bird
        bird.setVelocity(bird.getVelocity().multiply(0.9f));

        // Spawns a pipe at the right time
        if (pipeSpawnTimer <= 0) {
            spawnPipe();
            pipeSpawnTimer = 30;
        }

        // Goes through all pipes
        int i = 0;
        while (i < pipes.size()) {
            Sprite pipe = pipes.get(i);

            // If the right edge of the pipe is before the left edge of the bird, and if the pipe is top pipe
            boolean flag = pipe.getX() + pipe.getWidth() >= bird.getX() && pipe.getY() < 0;

            // Ticks the pipe
            pipe.tick();

            // If the right edge of the pipe is after the left edge of the bird,
            // and the flag is true,
            // (that means it was on the right but during the tick it got to the left)
            if (flag && pipe.getX() + pipe.getWidth() < bird.getX()) {
                // Adds score
                score++;
                // Play sound
                soundPlayer.playPoint();
            }

            // If the pipe is outside the game, it is removed
            if (pipe.getX() + pipe.getWidth() < 0) {
                pipes.remove(pipe);
                i--;
            }

            // If the bird is touching the pipe, the player dies
            if (bird.isTouching(pipe)) {
                die();
            }

            i++;
        }

        // If the bird is touching ground, or is outside the game, the player dies
        if (bird.isTouching(ground) || bird.getY() < 0) {
            die();
        }

        if (ticks % 2 == 0) {
            addParticle(new Particle(bird.getCenterPos(), ParticleColor.WHITE, 9, ParticleLevel.RUNNING_GAME)).setVelocity(new Vec2f(-2.5f, 0));
        }

        // Counting down the pipe spawning
        pipeSpawnTimer--;
    }

    @Override
    public synchronized void die() {
        super.die();
        bird.hide();
        spawnParticleExplosion(
                () -> new BreakParticleRenderer(new Identifier(GameDiscsMod.MOD_ID, "textures/games/sprite/bird.png"), 10, 32),
                bird.getCenterPos(),
                20,
                2,
                10,
                ParticleLevel.GAME
        );
    }

    /**
     * Spawns new pipe
     */
    private void spawnPipe() {
        // Selects random hole size
        int holeSize = random.nextInt(24, 28);

        // Selects random position of the hole
        int hole = random.nextInt(5, HEIGHT - holeSize - 21);

        // Adds one top pipe and one bottom pipe, on their corresponding positions, and adds them velocity to the left
        pipes.add(new Sprite(new Vec2f(WIDTH, (float)hole - 64f), new Vec2f(16f, 64f), new Identifier(GameDiscsMod.MOD_ID, "textures/games/sprite/pipe_top.png")));
        pipes.get(pipes.size() - 1).setVelocity(new Vec2f(-2.5f, 0f));
        pipes.add(new Sprite(new Vec2f(WIDTH, (float)hole + holeSize), new Vec2f(16f, 64f), new Identifier(GameDiscsMod.MOD_ID, "textures/games/sprite/pipe_bottom.png")));
        pipes.get(pipes.size() - 1).setVelocity(new Vec2f(-2.5f, 0f));
    }
    @Override
    public synchronized void render(DrawContext graphics, int posX, int posY) {
        // Calls render of super
        super.render(graphics, posX, posY);

        // Renders bird
        if (bird != null) {
            bird.render(graphics, posX, posY);
        }

        // Renders all pipes
        for (Sprite pillar : pipes) {
            pillar.render(graphics, posX, posY);
        }

        // Renders ground
        ground.render(graphics, posX, posY);

        // Renders particles
        renderParticles(graphics, posX, posY);

        // Renders overlay
        renderOverlay(graphics, posX, posY);
    }
    @Override
    public synchronized void buttonDown(Button button) {
        // Calls buttonDown of super
        super.buttonDown(button);

        // If an action button was pressed, bird sets velocity to up (jumps)
        if (button.isActionButton()) {
            bird.setVelocity(new Vec2f(0, -4.5f));
            if (stage == GameStage.PLAYING) {
                soundPlayer.playJump();
            }
        }
    }
    @Override
    public Identifier getBackground() {
        return new Identifier(GameDiscsMod.MOD_ID, "textures/games/background/flappy_bird_background.png");
    }
    @Override
    public boolean showScoreBox() {
        return false;
    }
    @Override
    public int scoreColor() {
        return Formatting.YELLOW.getColorValue();
    }
    @Override
    public Text getName() {
        return Text.translatable("gamediscs.flappy_bird");
    }
    @Override
    public Identifier getIcon() {
        return new Identifier(GameDiscsMod.MOD_ID, "textures/item/game_disc_flappy_bird.png");
    }
}
