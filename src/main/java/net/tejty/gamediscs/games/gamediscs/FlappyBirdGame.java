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
    private Sprite bird = new Sprite(new Vec2(10, 30), new Vec2(10, 8), new ResourceLocation("gamediscs:textures/games/sprite/bird.png"));
    private List<Sprite> pillars = new ArrayList<Sprite>();
    private final Sprite ground = new Sprite(new Vec2(0, HEIGHT - 16), new Vec2(156, 16), new ResourceLocation("gamediscs:textures/games/sprite/ground.png"));
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
    public synchronized void tick() {
        super.tick();
        if (stage != GameStage.DIED && stage != GameStage.WON) {
            ground.tick();
            if (ground.getX() <= -16) {
                ground.moveBy(new Vec2(16, 0));
            }
        }
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
            boolean flag = pillar.getX() + pillar.getWidth() > bird.getX() && pillar.getY() < 0;
            pillar.tick();

            if (flag && pillar.getX() + pillar.getWidth() <= bird.getX()) {
                score++;
            }
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
        renderOverlay(graphics, posX, posY);
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
