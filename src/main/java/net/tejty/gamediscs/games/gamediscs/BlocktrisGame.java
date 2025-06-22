package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.BasicParticleRenderer;
import net.tejty.gamediscs.games.graphics.MultiImage;
import net.tejty.gamediscs.games.graphics.ParticleColor;
import net.tejty.gamediscs.games.util.*;
import net.tejty.gamediscs.sounds.SoundRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlocktrisGame extends Game {
    public Grid grid;
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
                        Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/cubes.png"),
                        5, 40, 8));

        int type = random.nextInt(7);
        piece = new BlocktrisPiece(
                BlocktrisPiece.PIECES.get(type).get(),
                4, 1,
                type,
                this
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
                        Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/cubes.png"),
                        5, 40, 8));

        int type = random.nextInt(0, 7);
        piece = new BlocktrisPiece(
                BlocktrisPiece.PIECES.get(type).get(),
                4, 1,
                type,
                this
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
                            this
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
        return (int)(10f / ((float)score / 50f + 1f));
    }

    @Override
    public synchronized void render(DrawContext graphics, int posX, int posY) {
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

        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        Text text = Text.translatable("gui.gamingconsole.hold");
        graphics.drawText(font, text, 22 + posX - font.getWidth(text.asOrderedText()) / 2, 16 + posY, 0xFF555555, false);
        text = Text.translatable("gui.gamingconsole.next");
        graphics.drawText(font, text, 118 + posX - font.getWidth(text.asOrderedText()) / 2, 6 + posY, 0xFF555555, false);

        renderParticles(graphics, posX, posY);

        renderOverlay(graphics, posX, posY);
    }

    @Override
    public synchronized void buttonDown(Button button) {
        super.buttonDown(button);
        if (stage == GameStage.PLAYING && ticks > 5) {
            if (button == Button.UP) {
                piece.rotate();
                soundPlayer.play(SoundRegistry.SWING, 1.5f, 0.5f);
                placementCooldown = 10;
            }
            if (button == Button.LEFT) {
                piece.move(-1, 0);
                soundPlayer.play(SoundRegistry.SHOOT, 2.5f, 0.1f);
                placementCooldown = 10;
            }
            if (button == Button.RIGHT) {
                piece.move(1, 0);
                soundPlayer.play(SoundRegistry.SHOOT, 2.5f, 0.1f);
                placementCooldown = 10;
            }
            if (button == Button.DOWN) {
                soundPlayer.play(SoundRegistry.SHOOT, 2.5f, 0.1f);
                if (piece.move(0, 1)) {
                    placePiece();
                }
                else {
                    placementCooldown = 10;
                }
            }
            if (button == Button.BUTTON1) {
                soundPlayer.play(SoundRegistry.EXPLOSION, 0.7f, 0.5f);
                piece.hardDrop();
                placePiece();
            }
            if (button == Button.BUTTON2) {
                if (!switched) {
                    soundPlayer.play(SoundRegistry.SWITCH, 0.6f, 0.5f);
                    BlocktrisPiece oldHold = hold;
                    hold = piece;
                    piece = Objects.requireNonNullElseGet(oldHold, this::getNext);
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
                soundPlayer.play(SoundRegistry.SHOOT, 2.5f, 0.1f);
            }
            if (controls.isButtonDown(Button.RIGHT) && !controls.wasButtonDown(Button.RIGHT)) {
                piece.move(1, 0);
                soundPlayer.play(SoundRegistry.SHOOT, 2.5f, 0.1f);
            }
            if (controls.isButtonDown(Button.DOWN) && !controls.wasButtonDown(Button.DOWN)) {
                soundPlayer.play(SoundRegistry.SHOOT, 2.5f, 0.1f);
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
                            if (line == y - 1) {
                                spawnParticleExplosion(
                                        () ->
                                                new BasicParticleRenderer(ParticleColor.random(random)),
                                        new Vec2f(45 + x * 5, y * 5 - 15),
                                        4,
                                        3,
                                        5,
                                        ParticleLevel.RUNNING_GAME
                                );
                            }
                            grid.set(x, line + 1, grid.get(x, line));
                        }
                    }
                    y++;
                }
                y--;
            }

            score += combo * combo;
            if (combo > 0) {
                soundPlayer.playPoint();
            }

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
                        this
                )
        );
        toReturn.setPos(4, 1);
        toReturn.setRotation(0);

        return toReturn;
    }

    @Override
    public Identifier getBackground() {
        return Identifier.of(GameDiscsMod.MOD_ID, "textures/games/background/blocktris_bakground.png");
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
    public Text getName() {
        return Text.translatable("gamediscs.blocktris");
    }

    @Override
    public Identifier getIcon() {
        return Identifier.of(GameDiscsMod.MOD_ID, "textures/item/game_disc_blocktris.png");
    }
}
