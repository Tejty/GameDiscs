package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.Image;
import net.tejty.gamediscs.games.graphics.MultiImage;
import net.tejty.gamediscs.games.util.*;

import java.util.ArrayList;
import java.util.List;

public class TntSweeperGame extends Game {
    private final MultiImage TILE = new MultiImage(
            ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/tnt_sweeper.png"), 6, 84, 14);
    private static final ResourceLocation SELECT = ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/sprite/select.png");

    // Start position of the actual game field
    private static final Vec2 GAME_POS = new Vec2(1, 9);

    // Size of the tile
    private static final int TILE_SIZE = 6;

    // Dimensions of the game field (in tiles)
    private static final int GAME_WIDTH = 23;
    private static final int GAME_HEIGHT = 15;

    // Count of TNTs
    private static final int TNT_COUNT = 70;
    private int flags;

    // List of TNTs
    private List<Vec2> bombs = new ArrayList<>();
    private boolean isTntOn(Vec2 pos) {
        for (Vec2 bomb : bombs) {
            if (VecUtil.is(bomb, pos)) {
                return true;
            }
        }
        return false;
    }

    private int calculateBombsAround(Vec2 pos) {
        int count = 0;
        for (Vec2 rel : VecUtil.RELATIVES) {
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
    private Vec2 selectionPos = VecUtil.round(new Vec2(GAME_WIDTH - 1, GAME_HEIGHT - 1).scale(0.5f));
    private Sprite selection = new Sprite(calcPos(selectionPos).add(VecUtil.of(-1)), VecUtil.of(TILE_SIZE + 2), new Image(SELECT, 8, 8));

    public TntSweeperGame() {
        super();
    }
    @Override
    public void prepare() {
        // Calls prepare of super
        super.prepare();

        grid = new Grid(GAME_WIDTH, GAME_HEIGHT, TILE_SIZE, TILE);
        bombs = new ArrayList<>();
        flags = TNT_COUNT;
    }

    @Override
    public void start() {
        // Calls start of super
        super.start();

        // Generates TNTs
        for (int i = 0; i < TNT_COUNT; i++) {
            Vec2 pos = null;
            while (true) {
                if (pos != null) {
                    if (!isTntOn(pos) && Math.sqrt(pos.distanceToSqr(selectionPos)) > 2) {
                        break;
                    }
                }
                pos = VecUtil.randomInt(Vec2.ZERO, new Vec2(GAME_WIDTH, GAME_HEIGHT), random);
            }
            bombs.add(pos);
        }
    }
    @Override
    public void gameTick() {
        // Calls game tick of super
        super.gameTick();
    }

    @Override
    public void tick() {
        super.tick();

        if ((stage == GameStage.PLAYING || stage == GameStage.START) && ticks % 2 == 0) {
            if (controls.isButtonDown(Button.UP) && controls.wasButtonDown(Button.UP)) {
                selectionPos = selectionPos.add(VecUtil.VEC_UP);
            }
            if (controls.isButtonDown(Button.DOWN) && controls.wasButtonDown(Button.DOWN)) {
                selectionPos = selectionPos.add(VecUtil.VEC_DOWN);
            }
            if (controls.isButtonDown(Button.LEFT) && controls.wasButtonDown(Button.LEFT)) {
                selectionPos = selectionPos.add(VecUtil.VEC_LEFT);
            }
            if (controls.isButtonDown(Button.RIGHT) && controls.wasButtonDown(Button.RIGHT)) {
                selectionPos = selectionPos.add(VecUtil.VEC_RIGHT);
            }
            selectionPos = new Vec2(Math.min(Math.max(selectionPos.x, 0), GAME_WIDTH - 1), Math.min(Math.max(selectionPos.y, 0), GAME_HEIGHT - 1));
            selection.setPos(calcPos(selectionPos).add(VecUtil.of(-1)));
        }
    }

    @Override
    public void render(GuiGraphics graphics, int posX, int posY) {
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
        Font font = Minecraft.getInstance().font;
        String text = String.valueOf(flags);
        graphics.drawString(font, text, posX + 2, posY + 2, 0xFF373737, false);
        graphics.drawString(font, text, posX + 1, posY + 1, Color.WHITE.getColor(), false);
        if (stage == GameStage.PLAYING) {
            text = String.valueOf(ticks / 20);
            graphics.drawString(font, text, posX + WIDTH - font.width(text) - 1, posY + 2, 0xFF373737, false);
            graphics.drawString(font, text, posX + WIDTH - font.width(text) - 2, posY + 1, Color.WHITE.getColor(), false);
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
    public void buttonDown(Button button) {
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
            selectionPos = new Vec2(Math.min(Math.max(selectionPos.x, 0), GAME_WIDTH - 1), Math.min(Math.max(selectionPos.y, 0), GAME_HEIGHT - 1));
            selection.setPos(calcPos(selectionPos).add(VecUtil.of(-1)));
            if (button == Button.BUTTON2) {
                if (grid.get(selectionPos) == NOTHING && flags > 0) {
                    grid.set(selectionPos, FLAG);
                    flags--;
                    checkForWin();
                    soundPlayer.play(SoundEvents.WOOL_PLACE);
                }
                else if (grid.get(selectionPos) == FLAG) {
                    grid.set(selectionPos, NOTHING);
                    flags++;
                    soundPlayer.play(SoundEvents.WOOL_BREAK);
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

    private void dig(Vec2 pos) {
        if (grid.isIn(pos) && grid.get((int)pos.x, (int)pos.y) == NOTHING) {
            if (isTntOn(pos)) {
                die();
            } else {
                score++;
                grid.set((int) pos.x, (int) pos.y, numberTile(calculateBombsAround(pos)));
                if (grid.get((int) pos.x, (int) pos.y) == EMPTY) {
                    for (Vec2 rel : VecUtil.RELATIVES) {
                        dig(pos.add(rel));
                    }
                }
                soundPlayer.play(SoundEvents.DEEPSLATE_BRICKS_BREAK);
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
    public void die() {
        super.die();

        for (Vec2 bomb : bombs) {
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
    public ResourceLocation getBackground() {
        return ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/games/background/tnt_sweeper_background.png");
    }
    @Override
    public Component getName() {
        return Component.translatable("gamediscs.tnt_sweeper");
    }
    @Override
    public ResourceLocation getIcon() {
        return ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/item/game_disc_tnt_sweeper.png");
    }
}
