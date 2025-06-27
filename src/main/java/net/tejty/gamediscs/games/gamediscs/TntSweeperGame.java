package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.Image;
import net.tejty.gamediscs.games.graphics.MultiImage;
import net.tejty.gamediscs.games.util.*;

import java.util.ArrayList;
import java.util.List;

public class TntSweeperGame extends Game {
    private final MultiImage TILE = new MultiImage(
            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/tnt_sweeper.png"), 6, 84, 14);
    private static final Identifier SELECT = Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/select.png");

    // Start position of the actual game field
    private static final Vec2f GAME_POS = new Vec2f(1, 9);

    // Size of the tile
    private static final int TILE_SIZE = 6;

    // Dimensions of the game field (in tiles)
    private static final int GAME_WIDTH = 23;
    private static final int GAME_HEIGHT = 15;

    // Count of TNTs
    private static final int TNT_COUNT = 70;
    private int flags;

    // List of TNTs
    private List<Vec2f> bombs = new ArrayList<>();
    private boolean isTntOn(Vec2f pos) {
        for (Vec2f bomb : bombs) {
            if (VecUtil.is(bomb, pos)) {
                return true;
            }
        }
        return false;
    }

    private int calculateBombsAround(Vec2f pos) {
        int count = 0;
        for (Vec2f rel : VecUtil.RELATIVES) {
            if (isTntOn(pos.add(rel))) {
                count++;
            }
        }
        return count;
    }

    // Game grid
    private Grid grid = new Grid(GAME_WIDTH, GAME_HEIGHT, TILE_SIZE, TILE);

    // Tile types
    private static final int NOTHING = 0;
    private static final int FLAG = 1;
    private static final int EMPTY = 2;
    private static final int ONE = 3;
    private static final int TWO = 4;
    private static final int THREE = 5;
    private static final int FOUR = 6;
    private static final int FIVE = 7;
    private static final int SIX = 8;
    private static final int SEVEN = 9;
    private static final int EIGHT = 10;
    private static final int TNT = 11;
    private static final int BLOWN_TNT = 12;
    private static final int WRONG_FLAG = 13;

    private static int numberTile(int number) {
        return number + 2;
    }

    // Selection sprite
    private Vec2f selectionPos = VecUtil.round(new Vec2f(GAME_WIDTH - 1, GAME_HEIGHT - 1).multiply(0.5f));
    private Sprite selection = new Sprite(calcPos(selectionPos).add(VecUtil.of(-1)), VecUtil.of(TILE_SIZE + 2), new Image(SELECT, 8, 8));

    public TntSweeperGame() {
        super();
    }
    @Override
    public synchronized void prepare() {
        // Calls prepare of super
        super.prepare();

        grid = new Grid(GAME_WIDTH, GAME_HEIGHT, TILE_SIZE, TILE);
        bombs = new ArrayList<>();
        flags = TNT_COUNT;
    }

    @Override
    public synchronized void start() {
        // Calls start of super
        super.start();

        // Generates TNTs
        for (int i = 0; i < TNT_COUNT; i++) {
            Vec2f pos = null;
            while (true) {
                if (pos != null) {
                    if (!isTntOn(pos) && Math.sqrt(pos.distanceSquared(selectionPos)) > 2) {
                        break;
                    }
                }
                pos = VecUtil.randomInt(Vec2f.ZERO, new Vec2f(GAME_WIDTH, GAME_HEIGHT), random);
            }
            bombs.add(pos);
        }
    }
    @Override
    public synchronized void gameTick() {
        // Calls game tick of super
        super.gameTick();
    }

    @Override
    public synchronized void tick() {
        super.tick();

        if ((stage == GameStage.PLAYING || stage == GameStage.START) && ticks % 2 == 0) {
            if (controls.isButtonDown(Button.UP) && !controls.wasButtonDown(Button.UP)) {
                selectionPos = selectionPos.add(VecUtil.VEC_UP);
            }
            if (controls.isButtonDown(Button.DOWN) && !controls.wasButtonDown(Button.DOWN)) {
                selectionPos = selectionPos.add(VecUtil.VEC_DOWN);
            }
            if (controls.isButtonDown(Button.LEFT) && !controls.wasButtonDown(Button.LEFT)) {
                selectionPos = selectionPos.add(VecUtil.VEC_LEFT);
            }
            if (controls.isButtonDown(Button.RIGHT) && !controls.wasButtonDown(Button.RIGHT)) {
                selectionPos = selectionPos.add(VecUtil.VEC_RIGHT);
            }
            selectionPos = new Vec2f(Math.min(Math.max(selectionPos.x, 0), GAME_WIDTH - 1), Math.min(Math.max(selectionPos.y, 0), GAME_HEIGHT - 1));
            selection.setPos(calcPos(selectionPos).add(VecUtil.of(-1)));
        }
    }

    @Override
    public synchronized void render(DrawContext graphics, int posX, int posY) {
        // Calls render of super
        super.render(graphics, posX, posY);

        // Rendering grid
        grid.render(graphics, posX + (int)GAME_POS.x, posY + (int)GAME_POS.y);

        // Rendering selection
        selection.render(graphics, posX, posY);

        // Renders particles
        renderParticles(graphics, posX, posY);

        // Renders overlay
        renderOverlay(graphics, posX, posY);
        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        String text = String.valueOf(flags);
        graphics.drawText(font, text, posX + 2, posY + 2, 0xFF373737, false);
        graphics.drawText(font, text, posX + 1, posY + 1, 0xFFFFFFFF, false);
        if (stage == GameStage.PLAYING) {
            text = String.valueOf(ticks / 20);
            graphics.drawText(font, text, posX + WIDTH - font.getWidth(text) - 1, posY + 2, 0xFF373737, false);
            graphics.drawText(font, text, posX + WIDTH - font.getWidth(text) - 2, posY + 1, 0xFFFFFFFF, false);
        }
    }

    /**
     * @param pos Position on screen
     * @return Tile position
     */
    private Vec2f calcTile(Vec2f pos) {
        return pos.add(GAME_POS.negate()).multiply((float) 1 / TILE_SIZE);
    }

    /**
     * @param tile Tile position
     * @return Position on screen
     */
    private Vec2f calcPos(Vec2f tile) {
        return tile.multiply(TILE_SIZE).add(GAME_POS);
    }

    @Override
    public synchronized void buttonDown(Button button) {
        soundPlayer.playClick(true);

        // Prepares the game
        if (stage == GameStage.PLAYING || stage == GameStage.START) {
            if (button == Button.UP) {
                selectionPos = selectionPos.add(VecUtil.VEC_UP);
            }
            if (button == Button.DOWN) {
                selectionPos = selectionPos.add(VecUtil.VEC_DOWN);
            }
            if (button == Button.LEFT) {
                selectionPos = selectionPos.add(VecUtil.VEC_LEFT);
            }
            if (button == Button.RIGHT) {
                selectionPos = selectionPos.add(VecUtil.VEC_RIGHT);
            }
            selectionPos = new Vec2f(Math.min(Math.max(selectionPos.x, 0), GAME_WIDTH - 1), Math.min(Math.max(selectionPos.y, 0), GAME_HEIGHT - 1));
            selection.setPos(calcPos(selectionPos).add(VecUtil.of(-1)));
            if (button == Button.BUTTON2) {
                if (grid.get(selectionPos) == NOTHING && flags > 0) {
                    grid.set(selectionPos, FLAG);
                    flags--;
                    checkForWin();
                    soundPlayer.play(SoundEvents.BLOCK_WOOL_PLACE);
                }
                else if (grid.get(selectionPos) == FLAG) {
                    grid.set(selectionPos, NOTHING);
                    flags++;
                    soundPlayer.play(SoundEvents.BLOCK_WOOL_BREAK);
                }
            }
        }

        if (stage == GameStage.START) {
            if (button == Button.BUTTON1) {
                start();
            }
        }

        if ((stage == GameStage.WON || stage == GameStage.DIED) && ticks > 8) {
            prepare();
        }

        if (stage == GameStage.PLAYING) {
            if (button == Button.BUTTON1) {
                dig(selectionPos);
            }
        }
    }

    private void dig(Vec2f pos) {
        if (grid.isIn(pos) && grid.get((int)pos.x, (int)pos.y) == NOTHING) {
            if (isTntOn(pos)) {
                die();
            } else {
                score++;
                grid.set((int) pos.x, (int) pos.y, numberTile(calculateBombsAround(pos)));
                if (grid.get((int) pos.x, (int) pos.y) == EMPTY) {
                    for (Vec2f rel : VecUtil.RELATIVES) {
                        dig(pos.add(rel));
                    }
                }
                soundPlayer.play(SoundEvents.BLOCK_DEEPSLATE_BRICKS_BREAK);
                checkForWin();
            }
        }
    }

    private void checkForWin() {
        if (flags == 0 && score == GAME_WIDTH * GAME_HEIGHT - TNT_COUNT) {
            win();
        }
    }

    @Override
    public synchronized void die() {
        super.die();

        for (Vec2f bomb : bombs) {
            grid.set(bomb, TNT);
            spawnParticleExplosion(calcPos(bomb), 10, 2, 20, ParticleLevel.GAME);
        }

        grid.set(selectionPos, BLOWN_TNT);
    }

    @Override
    public boolean showScore() {
        return false;
    }

    @Override
    public boolean showPressAnyKey() {
        return false;
    }

    @Override
    public Identifier getBackground() {
        return Identifier.of(GameDiscsMod.MOD_ID, "textures/games/background/tnt_sweeper_background.png");
    }
    @Override
    public Text getName() {
        return Text.translatable("gamediscs.tnt_sweeper");
    }
    @Override
    public Identifier getIcon() {
        return Identifier.of(GameDiscsMod.MOD_ID, "textures/item/game_disc_tnt_sweeper.png");
    }
}
