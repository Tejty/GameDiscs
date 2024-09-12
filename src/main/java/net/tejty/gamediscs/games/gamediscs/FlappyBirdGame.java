package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.util.GameStage;
import net.tejty.gamediscs.games.util.Sprite;
import net.tejty.gamediscs.games.controls.Button;

import java.util.ArrayList;
import java.util.List;
public class FlappyBirdGame extends Game {
    // The main player Sprite
    private Sprite bird = new Sprite(new Vec2(10, 30), new Vec2(10, 8), new ResourceLocation("gamediscs:textures/games/sprite/bird.png"));
    // List of pipes
    private List<Sprite> pipes = new ArrayList<Sprite>();
    // Ground Sprite
    private final Sprite ground = new Sprite(new Vec2(0, HEIGHT - 16), new Vec2(156, 16), new ResourceLocation("gamediscs:textures/games/sprite/ground.png"));

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
        bird = new Sprite(new Vec2(10, 30), new Vec2(10, 8), new ResourceLocation("gamediscs:textures/games/sprite/bird.png"));
        pipes = new ArrayList<>();
        ground.setVelocity(new Vec2(-2.5f, 0));
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

        // If it's not died or won, the ground moves
        if (stage != GameStage.DIED && stage != GameStage.WON) {
            ground.tick();
            if (ground.getX() <= -16) {
                ground.moveBy(new Vec2(16, 0));
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
        bird.addVelocity(new Vec2(0, 0.75f));

        // Makes air friction to the bird
        bird.setVelocity(bird.getVelocity().scale(0.9f));

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

        // Counting down the pipe spawning
        pipeSpawnTimer--;
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
        pipes.add(new Sprite(new Vec2(WIDTH, (float)hole - 64f), new Vec2(16f, 64f), new ResourceLocation("gamediscs:textures/games/sprite/pipe_top.png")));
        pipes.get(pipes.size() - 1).setVelocity(new Vec2(-2.5f, 0f));
        pipes.add(new Sprite(new Vec2(WIDTH, (float)hole + holeSize), new Vec2(16f, 64f), new ResourceLocation("gamediscs:textures/games/sprite/pipe_bottom.png")));
        pipes.get(pipes.size() - 1).setVelocity(new Vec2(-2.5f, 0f));
    }
    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
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

        // Renders overlay
        renderOverlay(graphics, posX, posY);
    }
    @Override
    public synchronized void buttonDown(Button button) {
        // Calls buttonDown of super
        super.buttonDown(button);

        // If an action button was pressed, bird sets velocity to up (jumps)
        if (button.isActionButton()) {
            bird.setVelocity(new Vec2(0, -4.5f));
        }
    }
    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation("gamediscs:textures/games/background/flappy_bird_background.png");
    }
    @Override
    public boolean showScoreBox() {
        return false;
    }
    @Override
    public int scoreColor() {
        return ChatFormatting.YELLOW.getColor();
    }
    @Override
    public Component getName() {
        return Component.translatable("gamediscs.flappy_bird");
    }
    @Override
    public ResourceLocation getIcon() {
        return new ResourceLocation("gamediscs:textures/item/game_disc_flappy_bird.png");
    }
}
