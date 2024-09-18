package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.tejty.gamediscs.games.graphics.MultiImage;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.util.Grid;

public class BlocktrisGame extends Game {
    private Grid grid;

    public BlocktrisGame() {
        super();
        grid = new Grid(
                10,
                20,
                5,
                new MultiImage(
                        new ResourceLocation("gamediscs:textures/games/sprite/cubes.png"),
                        5, 40, 8));
    }

    @Override
    public synchronized void gameTick() {
        // Just debugging
        grid.set(5, 14, 1);
    }

    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        super.render(graphics, posX, posY);

        grid.render(graphics, posX + 45, posY);
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation("gamediscs:textures/games/background/blocktris_bakground.png");
    }

    @Override
    public boolean showScoreBox() {
        return false;
    }

    @Override
    public boolean scoreText() {
        return false;
    }

    @Override
    public Component getName() {
        return Component.translatable("gamediscs.blocktris");
    }

    @Override
    public ResourceLocation getIcon() {
        return new ResourceLocation("gamediscs:textures/item/game_disc_blocktris.png");
    }
}
