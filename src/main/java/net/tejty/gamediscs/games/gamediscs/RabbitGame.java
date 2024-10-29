package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.AnimatedImage;
import net.tejty.gamediscs.games.graphics.BreakParticleRenderer;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.util.GameStage;
import net.tejty.gamediscs.games.util.ParticleLevel;
import net.tejty.gamediscs.games.util.Sprite;

import java.util.ArrayList;
import java.util.List;

public class RabbitGame extends Game {
    // The main player Sprite
    private Sprite rabbit = new Sprite(new Vec2(10, HEIGHT), new Vec2(16, 16),
            new AnimatedImage(
                    ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/rabbit_run.png"),
                    16, 48, 3, 2)
    );
    // List of cactus
    private List<Sprite> cactus = new ArrayList<>();
    // Ground Sprite
    private final Sprite ground = new Sprite(new Vec2(0, HEIGHT - 16), new Vec2(156, 16),
            ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/sand_ground.png"));

    // Countdown of spawning another cactus
    private int cactusSpawnTimer = 0;
    private float speed = -3f;

    public RabbitGame() {
        super();
    }

    @Override
    public synchronized void prepare() {
        // Calls prepare of super
        super.prepare();

        // Resets everything
        rabbit = new Sprite(new Vec2(10, HEIGHT - ground.getHeight() - 16), new Vec2(16, 16),
                new AnimatedImage(
                        ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/rabbit_run.png"),
                        16, 48, 3, 2)
        );
        cactus = new ArrayList<>();
        speed = -3f;
        ground.setVelocity(new Vec2(speed, 0));
        rabbit.show();
    }

    @Override
    public synchronized void start() {
        // Calls start of super
        super.start();

        // Resets cactus spawn timer
        cactusSpawnTimer = 0;
    }
    @Override
    public synchronized void tick() {
        // Calls tick of super
        super.tick();

        // Animating rabbit
        if (stage != GameStage.DIED) {
            rabbit.animTick();
        }

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

        // Ticks the rabbit
        rabbit.tick();

        // Adds velocity down to the rabbit (gravity)
        rabbit.addVelocity(new Vec2(0, 1f));

        speed -= 0.005f;
        ground.setVelocity(new Vec2(speed, 0));

        // Spawns a cactus at the right time
        if (cactusSpawnTimer <= 0) {
            spawnCactus();
            cactusSpawnTimer = random.nextInt(15, 30);
        }

        if (ticks % 20 == 0) {
            // Adds score
            score++;
        }

        // Goes through all cactus
        int i = 0;
        while (i < cactus.size()) {
            Sprite obstacle = cactus.get(i);

            obstacle.tick();

            // If the obstacle is outside the game, it is removed
            if (obstacle.getX() + obstacle.getWidth() < 0) {
                cactus.remove(obstacle);
                i--;
            }

            // If the rabbit is touching the obstacle, the player dies
            if (rabbit.isTouching(obstacle)) {
                die();
            }

            obstacle.setVelocity(new Vec2(speed, 0));

            i++;
        }

        if (rabbit.isTouching(ground)) {
            rabbit.setY(HEIGHT - ground.getHeight() - rabbit.getHeight());
            rabbit.setVelocity(Vec2.ZERO);
        }

        // Counting down the cactus spawning
        cactusSpawnTimer--;
    }

    @Override
    public synchronized void die() {
        super.die();
        rabbit.hide();
        spawnParticleExplosion(
                () -> new BreakParticleRenderer(ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/rabbit.png"), 10, 32),
                rabbit.getCenterPos(),
                20,
                2,
                10,
                ParticleLevel.GAME
        );
    }

    /**
     * Spawns new cactus
     */
    private void spawnCactus() {
        cactus.add(new Sprite(new Vec2(WIDTH, HEIGHT - ground.getHeight() - 10), new Vec2(8, 16), ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/cactus.png")));
        cactus.get(cactus.size() - 1).setVelocity(new Vec2(speed, 0f));
    }
    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        // Calls render of super
        super.render(graphics, posX, posY);

        // Renders rabbit
        if (rabbit != null) {
            rabbit.render(graphics, posX, posY);
        }

        // Renders all cactus
        for (Sprite obstacle : cactus) {
            obstacle.render(graphics, posX, posY);
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
        if (button.isActionButton() && rabbit.getY() >= HEIGHT - ground.getHeight() - rabbit.getHeight()) {
            rabbit.setVelocity(new Vec2(0, -6f));
            if (stage == GameStage.PLAYING) {
                soundPlayer.playJump();
            }
        }
    }
    @Override
    public ResourceLocation getBackground() {
        return ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/background/rabbit_background.png");
    }
    @Override
    public boolean showScoreBox() {
        return false;
    }
    @Override
    public Component getName() {
        return Component.translatable("gamediscs.rabbit");
    }
    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/item/game_disc_rabbit.png");
    }
}
