package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.BreakParticleRenderer;
import net.tejty.gamediscs.games.graphics.DirectionalImage;
import net.tejty.gamediscs.games.util.*;

import java.util.ArrayList;
import java.util.List;
public class SlimeGame extends Game {
    private final DirectionalImage HEAD = new DirectionalImage(
            ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/slime_head.png"), 8, 32);
    private final DirectionalImage TAIL = new DirectionalImage(
            ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/slime_tail.png"), 8, 32);
    private final DirectionalImage CONNECTION = new DirectionalImage(
            ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/slime_connection.png"), 8, 32);
    private static final ResourceLocation BODY = ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/slime_body.png");
    private static final ResourceLocation APPLE = ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/apple.png");

    // Start position of the actual game field
    private static final Vec2 GAME_POS = new Vec2(6, 2);

    // Size of the tile
    private static final int TILE_SIZE = 8;

    // Dimensions of the game field (in tiles)
    private static final int GAME_WIDTH = 16;
    private static final int GAME_HEIGHT = 12;

    // List of blocked positions (in tiles)
    private static final List<Vec2> BLOCKED = List.of(
            new Vec2(0, 0),
            new Vec2(1, 0),
            new Vec2(2, 0),
            new Vec2(3, 0),
            new Vec2(4, 0),
            new Vec2(5, 0),
            new Vec2(6, 0)
    );

    // List of slime positions (in tiles)
    private List<Vec2> slime = new ArrayList<>();

    // Direction of moving (as a Vec2 relative position)
    private Vec2 direction = VecUtil.VEC_RIGHT;

    // Sprite used for rendering all slime parts
    private final Sprite slimeRenderer = new Sprite(Vec2.ZERO, new Vec2(TILE_SIZE, TILE_SIZE), BODY);

    // Apple Sprite
    private final Sprite apple = new Sprite(Vec2.ZERO, new Vec2(TILE_SIZE, TILE_SIZE), APPLE);
    public SlimeGame() {
        super();
    }
    @Override
    public synchronized void prepare() {
        // Calls prepare of super
        super.prepare();

        // Creates slime body
        slime = new ArrayList<>();
        slime.add(new Vec2(5, 5));
        slime.add(new Vec2(6, 5));
        slime.add(new Vec2(7, 5));

        // Moves apple to random position
        respawnApple();
    }
    @Override
    public synchronized void start() {
        // Calls start of super
        super.start();

        // Resets direction
        direction = VecUtil.VEC_RIGHT;
    }
    @Override
    public synchronized void gameTick() {
        // Calls game tick of super
        super.gameTick();

        // Calculates new position of the slime
        Vec2 newPos = slime.getLast().add(direction);

        // If is outside the game field, player dies
        if (newPos.x >= GAME_WIDTH || newPos.x < 0 || newPos.y >= GAME_HEIGHT || newPos.y < 0) {
            die();
        }

        // If there is any part of the slime, player dies
        for (int i = 0; i < slime.size(); i++) {
            Vec2 part = slime.get(i);
            if (i != 0 && VecUtil.is(part, newPos)) {
                die();
            }
        }

        // If there is any blocked position, the player dies
        for (Vec2 block : BLOCKED) {
            if (VecUtil.is(block, newPos)) {
                die();
            }
        }

        if (stage == GameStage.PLAYING) {
            // New position is added to the slime body
            slime.add(newPos);

            // If there is no apple on the position, it removes tail part of the body
            if (!VecUtil.is(newPos, calcTile(apple.getPos()))) {
                slime.remove(slime.getFirst());
            }
            else {
                // If there is an apple, it respawns, and the score grows
                spawnParticleExplosion(() -> new BreakParticleRenderer(APPLE, 8, 8), apple.getCenterPos(), 15, 3, 5, ParticleLevel.GAME);
                respawnApple();
                score++;
                soundPlayer.play(SoundEvents.GENERIC_EAT);
            }
        }

        addParticle(new Particle(calcPos(slime.getFirst().add(VecUtil.randomFloat(Vec2.ZERO, new Vec2(1, 1), random))), new BreakParticleRenderer(BODY, 8, 8), random.nextInt(20, 50), ParticleLevel.RUNNING_GAME));
    }
    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        // Calls render of super
        super.render(graphics, posX, posY);

        // Renders apple
        apple.render(graphics, posX, posY);

        // Renders particles
        renderParticles(graphics, posX, posY);

        // Renders slime
        for (int i = slime.size() - 1; i >= 0; i--) {
            // Sets the image of the renderer to the corresponding slime part
            if (i == 0) {
                slimeRenderer.setImage(TAIL.setImage(VecUtil.get4DirectionTo(slime.get(0), slime.get(1))));
            }
            else if (i == slime.size() - 1) {
                slimeRenderer.setImage(HEAD.setImage(VecUtil.get4DirectionTo(slime.get(slime.size()-2), slime.getLast())));
            }
            else {
                slimeRenderer.setImage(BODY);
            }

            Vec2 part = slime.get(i);
            // Renders the part on its position
            slimeRenderer.setPos(calcPos(part));
            slimeRenderer.render(graphics, posX, posY);

            // If the part is not head, it renders a connection
            if (i + 1 < slime.size()) {
                // Moves by 0.5 tiles to the previous part
                slimeRenderer.setPos(calcPos(part.add(VecUtil.getFrom(VecUtil.get4DirectionTo(part, slime.get(i+1))).scale(0.5f))));

                // Sets the corresponding image based on rotation
                slimeRenderer.setImage(CONNECTION.setImage(VecUtil.get4DirectionTo(part, slime.get(i+1))));

                // Renders the connection
                slimeRenderer.render(graphics, posX, posY);
            }
        }

        // Renders overlay
        renderOverlay(graphics, posX, posY);
    }

    /**
     * Moves apple to a random location that is not occupied by slime, or blocked position
     */
    private void respawnApple() {
        // Keeps moving to random position until the position is valid
        boolean valid = false;
        while (!valid) {
            // Goes to random position within the game field
            apple.setPos(calcPos(VecUtil.randomInt(Vec2.ZERO, new Vec2(GAME_WIDTH, GAME_HEIGHT), random)));

            // Sets valid to true
            valid = true;
            Vec2 applePos = calcTile(apple.getPos());

            // If there is slime or blocked position, it sets valid to false
            for (Vec2 vec2 : slime) {
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

    /**
     * @param pos Position on screen
     * @return Tile position
     */
    private Vec2 calcTile(Vec2 pos) {
        return pos.add(GAME_POS.negated()).scale((float) 1 / TILE_SIZE);
    }

    /**
     * @param tile Tile position
     * @return Position on screen
     */
    private Vec2 calcPos(Vec2 tile) {
        return tile.scale(TILE_SIZE).add(GAME_POS);
    }
    @Override
    public synchronized void buttonDown(Button button) {
        // Calls button down of super
        super.buttonDown(button);

        // Changes the direction of the slime if a corresponding key was pressed
        if (stage == GameStage.PLAYING) {
            Vec2 oldDirection = direction;
            switch (button) {
                case UP -> direction = VecUtil.VEC_UP;
                case RIGHT -> direction = VecUtil.VEC_RIGHT;
                case DOWN -> direction = VecUtil.VEC_DOWN;
                case LEFT -> direction = VecUtil.VEC_LEFT;
            }
            if (!VecUtil.is(oldDirection, direction)) {
                    soundPlayer.play(SoundEvents.SLIME_SQUISH, 0.1f, 0.5f);
            }
        }
    }
    @Override
    public int gameTickDuration() {
        return 5;
    }
    @Override
    public ResourceLocation getBackground() {
        return ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/background/slime_background.png");
    }
    @Override
    public Component getName() {
        return Component.translatable("gamediscs.slime");
    }
    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/item/game_disc_slime.png");
    }
}
