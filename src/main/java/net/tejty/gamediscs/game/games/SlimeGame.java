package net.tejty.gamediscs.game.games;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.game.Game;
import net.tejty.gamediscs.game.Sprite;

import java.util.ArrayList;
import java.util.List;

public class SlimeGame extends Game {
    private static final ResourceLocation HEAD = new ResourceLocation("gamediscs:textures/games/sprite/slime_head.png");
    private static final ResourceLocation BODY = new ResourceLocation("gamediscs:textures/games/sprite/slime_body.png");
    private static final ResourceLocation TAIL = new ResourceLocation("gamediscs:textures/games/sprite/slime_tail.png");
    private static final ResourceLocation CONNECT = new ResourceLocation("gamediscs:textures/games/sprite/slime_connect.png");

    private List<Sprite> snake = new ArrayList<>();

    public SlimeGame() {
        super();
    }

    @Override
    public synchronized void prepare() {
        super.prepare();
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public synchronized void gameTick() {
        super.gameTick();
    }

    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        super.render(graphics, posX, posY);
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
