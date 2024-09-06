package net.tejty.gamediscs.game.games;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.game.Game;
import net.tejty.gamediscs.game.Sprite;
import net.tejty.gamediscs.game.controls.Button;

import java.util.ArrayList;
import java.util.List;

public class FlappyBirdGame extends Game {
    private Sprite bird;
    private List<Sprite> pillars = new ArrayList<Sprite>();
    private Sprite ground = new Sprite(new Vec2(0, HEIGHT - 16), new Vec2(156, 16), new ResourceLocation("gamediscs:textures/games/sprite/ground.png"));
    private int pillarSpawnTimer = 0;

    public FlappyBirdGame() {
        super();
    }

    @Override
    public synchronized void prepare() {
        super.prepare();

        bird = new Sprite(new Vec2(10, 30), new Vec2(10, 8), new ResourceLocation("gamediscs:textures/games/sprite/bird.png"));
        pillars = new ArrayList<Sprite>();
        ground.setVelocity(new Vec2(-2.5f, 0));
    }
    @Override
    public synchronized void start() {
        super.start();

        pillars.clear();
        pillarSpawnTimer = 0;

        bird.setPos(new Vec2(10, 30));
        bird.setVelocity(Vec2.ZERO);
        ground.setVelocity(new Vec2(-2.5f, 0));
    }

    @Override
    public synchronized void gameTick() {
        super.gameTick();

        bird.tick();
        bird.addVelocity(new Vec2(0, 0.75f));
        bird.setVelocity(bird.getVelocity().scale(0.9f));

        if (pillarSpawnTimer <= 0) {
            spawnPillar();
            pillarSpawnTimer = 30;
        }
        int i = 0;
        while (i < pillars.size()) {
            Sprite pillar = pillars.get(i);
            pillar.tick();
            if (pillar.getX() + pillar.getWidth() < 0) {
                //Minecraft.getInstance().player.displayClientMessage(Component.literal("deleting pillar: " + pillar.getX() + ", " + pillar.getWidth()), false);
                pillars.remove(pillar);
                i--;
            }

            if (bird.isTouching(pillar)) {
                die();
            }

            i++;
        }

        if (bird.isTouching(ground) || bird.getY() < 0) {
            die();
        }

        ground.tick();
        if (ground.getX() <= -16) {
            ground.moveBy(new Vec2(16, 0));
        }

        pillarSpawnTimer--;
    }

    private void spawnPillar() {
        //Minecraft.getInstance().player.displayClientMessage(Component.literal("new pillar"), false);
        int holeSize = random.nextInt(24, 28);
        int hole = random.nextInt(5, HEIGHT - holeSize - 21);
        pillars.add(new Sprite(new Vec2(WIDTH, (float)hole - 64f), new Vec2(16f, 64f), new ResourceLocation("gamediscs:textures/games/sprite/pipe_top.png")));
        pillars.get(pillars.size() - 1).setVelocity(new Vec2(-2.5f, 0f));
        pillars.add(new Sprite(new Vec2(WIDTH, (float)hole + holeSize), new Vec2(16f, 64f), new ResourceLocation("gamediscs:textures/games/sprite/pipe_bottom.png")));
        pillars.get(pillars.size() - 1).setVelocity(new Vec2(-2.5f, 0f));
    }

    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        super.render(graphics, posX, posY);

        for (Sprite pillar : pillars) {
            pillar.render(graphics, posX, posY);
        }

        ground.render(graphics, posX, posY);

        if (bird != null) {
            bird.render(graphics, posX, posY);
        }
    }

    @Override
    public synchronized void buttonDown(Button button) {
        super.buttonDown(button);

        if (button.isActionButton()) {
            bird.setVelocity(new Vec2(0, -4.5f));
        }
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation("gamediscs:textures/games/background/flappy_bird_background.png");
    }
}
