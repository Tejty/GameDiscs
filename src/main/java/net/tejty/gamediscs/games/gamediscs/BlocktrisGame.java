package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.MultiImage;
import net.tejty.gamediscs.games.util.BlocktrisPiece;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.util.GameStage;
import net.tejty.gamediscs.games.util.Grid;

import java.util.ArrayList;
import java.util.List;

public class BlocktrisGame extends Game {
    private Grid grid;
    private BlocktrisPiece piece;
    private BlocktrisPiece hold = null;
    private List<BlocktrisPiece> nexts = new ArrayList<>();
    private int placementCooldown = 0;
    private boolean switched = false;

    public BlocktrisGame() {
        super();
        grid = new Grid(
                10,
                23,
                5,
                new MultiImage(
                        new ResourceLocation("gamediscs:textures/games/sprite/cubes.png"),
                        5, 40, 8));

        int type = random.nextInt(7);
        piece = new BlocktrisPiece(
                BlocktrisPiece.PIECES.get(type).get(),
                4, 1,
                type,
                grid
        );
    }

    @Override
    public synchronized void prepare() {
        super.prepare();

        grid = new Grid(
                10,
                23,
                5,
                new MultiImage(
                        new ResourceLocation("gamediscs:textures/games/sprite/cubes.png"),
                        5, 40, 8));

        int type = random.nextInt(0, 7);
        piece = new BlocktrisPiece(
                BlocktrisPiece.PIECES.get(type).get(),
                4, 1,
                type,
                grid
        );
        nexts.clear();
        hold = null;
    }

    @Override
    public synchronized void start() {
        super.start();
        for (int i = 0; i < 3; i++) {
            int type = random.nextInt(0, 7);
            nexts.add(
                    new BlocktrisPiece(
                            BlocktrisPiece.PIECES.get(type).get(),
                            4, 1,
                            type,
                            grid
                    )
            );
        }
        placementCooldown = 0;
        switched = false;
    }

    @Override
    public synchronized void gameTick() {
        if (piece.move(0, 1)) {
            placePiece();
        }
    }

    @Override
    public int gameTickDuration() {
        return (int)(10f / ((float)score / 30f + 1f));
    }

    @Override
    public synchronized void render(GuiGraphics graphics, int posX, int posY) {
        super.render(graphics, posX, posY);

        grid.render(graphics, posX + 45, posY - 15);
        piece.render(graphics, posX + 45, posY - 15);

        for (int i = 0; i < nexts.size(); i++) {
            BlocktrisPiece next = nexts.get(i);
            next.setRotation(1);
            next.setPos(23, 4 + i * 3);
            next.renderCentered(graphics, posX, posY);
        }

        if (hold != null) {
            hold.setRotation(1);
            hold.setPos(4, 6);
            hold.renderCentered(graphics, posX, posY);
        }

        renderOverlay(graphics, posX, posY);
    }

    @Override
    public synchronized void buttonDown(Button button) {
        super.buttonDown(button);
        if (stage == GameStage.PLAYING && ticks > 5) {
            if (button == Button.UP) {
                piece.rotate();
                placementCooldown = 10;
            }
            if (button == Button.LEFT) {
                piece.move(-1, 0);
                placementCooldown = 10;
            }
            if (button == Button.RIGHT) {
                piece.move(1, 0);
                placementCooldown = 10;
            }
            if (button == Button.DOWN) {
                if (piece.move(0, 1)) {
                    placePiece();
                }
                else {
                    placementCooldown = 10;
                }
            }
            if (button == Button.BUTTON1) {
                piece.hardDrop();
                placePiece();
            }
            if (button == Button.BUTTON2) {
                if (!switched) {
                    BlocktrisPiece oldHold = hold;
                    hold = piece;
                    if (oldHold != null) {
                        piece = oldHold;
                    } else {
                        piece = getNext();
                    }
                    piece.setPos(4, 1);
                    switched = true;
                }
            }
        }
    }

    @Override
    public synchronized void tick() {
        super.tick();
        if (stage == GameStage.PLAYING && ticks % 2 == 0) {
            if (controls.isButtonDown(Button.LEFT) && !controls.wasButtonDown(Button.LEFT)) {
                piece.move(-1, 0);
            }
            if (controls.isButtonDown(Button.RIGHT) && !controls.wasButtonDown(Button.RIGHT)) {
                piece.move(1, 0);
            }
            if (controls.isButtonDown(Button.DOWN) && !controls.wasButtonDown(Button.DOWN)) {
                if (piece.move(0, 1)) {
                    placePiece();
                }
            }
        }
        placementCooldown--;
        if (placementCooldown < 0) {
            placementCooldown = 0;
        }
    }

    private void placePiece() {
        switched = false;
        if (placementCooldown <= 0) {
            piece.place();

            int combo = 0;

            int y = grid.height() - 1;
            while (y >= 0) {
                boolean isFull = true;
                for (int x = 0; x < grid.width(); x++) {
                    if (grid.get(x, y) == 0) {
                        isFull = false;
                    }
                }
                if (isFull) {
                    combo++;
                    for (int line = y - 1; line >= 0; line--) {
                        for (int x = 0; x < grid.width(); x++) {
                            grid.set(x, line + 1, grid.get(x, line));
                        }
                    }
                    y++;
                }
                y--;
            }

            score += combo * combo;

            piece = getNext();
            piece.setPos(4, 1);
            if (piece.isTouching()) {
                die();
            }
        }
    }

    private BlocktrisPiece getNext() {
        BlocktrisPiece toReturn = nexts.get(0);

        nexts.remove(0);
        int type = random.nextInt(0, 7);
        nexts.add(
                new BlocktrisPiece(
                        BlocktrisPiece.PIECES.get(type).get(),
                        4, 1,
                        type,
                        grid
                )
        );
        toReturn.setPos(4, 1);
        toReturn.setRotation(0);

        return toReturn;
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
