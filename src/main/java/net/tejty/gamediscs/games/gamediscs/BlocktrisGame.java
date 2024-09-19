package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.MultiImage;
import net.tejty.gamediscs.games.util.BlocktrisPiece;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.util.Grid;

import java.util.List;

public class BlocktrisGame extends Game {
    private Grid grid;
    private BlocktrisPiece piece;

    public BlocktrisGame() {
        super();
        grid = new Grid(
                10,
                23,
                5,
                new MultiImage(
                        new ResourceLocation("gamediscs:textures/games/sprite/cubes.png"),
                        5, 40, 8));
        piece = new BlocktrisPiece(
                BlocktrisPiece.L.get(),
                3, 1,
                3,
                grid
        );
    }

    @Override
    public synchronized void gameTick() {
        if (piece.move(0, 1)) {
            piece.place();
            piece.setPos(4, 1);
        }
    }

    @Override
    public int gameTickDuration() {
        return 10;
    }

    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        super.render(graphics, posX, posY);

        grid.render(graphics, posX + 45, posY - 15);
        piece.render(graphics, posX + 45, posY - 15);
    }

    @Override
    public synchronized void buttonDown(Button button) {
        super.buttonDown(button);
        if (button == Button.UP) {
            piece.rotate();
        }
        if (button == Button.LEFT) {
            piece.move(-1, 0);
        }
        if (button == Button.RIGHT) {
            piece.move(1, 0);
        }
        if (button == Button.DOWN) {
            if(piece.move(0, 1)) {
                piece.place();
                piece.setPos(4, 1);
            }
        }
    }
    @Override
    public synchronized void tick() {
        super.tick();
        if (ticks % 2 == 0) {
            if (controls.isButtonDown(Button.LEFT)) {
                piece.move(-1, 0);
            }
            if (controls.isButtonDown(Button.RIGHT)) {
                piece.move(1, 0);
            }
            if (controls.isButtonDown(Button.DOWN)) {
                if (piece.move(0, 1)) {
                    piece.place();
                    piece.setPos(4, 1);
                }
            }
        }
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
