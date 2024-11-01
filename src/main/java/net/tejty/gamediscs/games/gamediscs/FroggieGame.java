package net.tejty.gamediscs.games.gamediscs;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.graphics.DirectionalImage;
import net.tejty.gamediscs.games.graphics.Image;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.games.util.GameStage;
import net.tejty.gamediscs.games.util.Sprite;
import net.tejty.gamediscs.games.util.VecUtil;

import java.util.ArrayList;
import java.util.List;

public class FroggieGame extends Game {
    private Sprite frog = new Sprite(
            new Vec2f(0, 0),
            new Vec2f(7, 7),
            new DirectionalImage(
                    Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/frog.png"),
                    7,
                    28
            )
    );
    private int moveCooldown = 0;

    private List<Sprite> minecarts = new ArrayList<>();
    private List<Sprite> logs = new ArrayList<>();

    private static final int TILE_SIZE = 7;

    private List<Integer> holes = List.of(
            2,
            6,
            10,
            14,
            18
    );
    private List<Boolean> isHoleFull;

    private int lastLine = 0;

    private static Vec2f getPos(Vec2f tile) {
        return tile.multiply(TILE_SIZE);
    }

    private static Vec2f getTile(Vec2f pos) {
        return VecUtil.round(pos.multiply(1f / TILE_SIZE));
    }

    public FroggieGame() {
        super();
        isHoleFull = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            isHoleFull.add(false);
        }
    }

    @Override
    public synchronized void prepare() {
        isHoleFull = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            isHoleFull.add(false);
        }

        // Calls prepare of super
        super.prepare();

        // Resets everything
        minecarts.clear();
        logs.clear();
        if (true) {
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(20, 12)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(19, 12)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(18, 12)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(12, 12)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(11, 12)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );


            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(0, 11)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(2f, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(10, 11)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(2f, 0))
            );


            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(15, 10)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-4, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(16, 10)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-4, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(5, 10)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-4, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(6, 10)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-4, 0))
            );


            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(13, 9)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(6, 0))
            );


            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(8, 8)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(9, 8)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(10, 8)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(16, 8)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(17, 8)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );
            minecarts.add(
                    new Sprite(
                            getPos(new Vec2f(18, 8)),
                            new Vec2f(7, 7),
                            Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/minecart.png")
                    ).addVelocity(new Vec2f(-2, 0))
            );


            logs.add(
                    new Sprite(
                            getPos(new Vec2f(1, 5)),
                            new Vec2f(21, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/log.png"), 63, 7, 0, 0, 21, 7)
                    ).addVelocity(new Vec2f(1, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(5, 5)),
                            new Vec2f(21, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/log.png"), 63, 7, 0, 0, 21, 7)
                    ).addVelocity(new Vec2f(1, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(16, 5)),
                            new Vec2f(21, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/log.png"), 63, 7, 0, 0, 21, 7)
                    ).addVelocity(new Vec2f(1, 0))
            );


            logs.add(
                    new Sprite(
                            getPos(new Vec2f(1, 6)),
                            new Vec2f(14, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/turtles.png"), 28, 7, 0, 0, 14, 7)
                    ).addVelocity(new Vec2f(-1, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(4, 6)),
                            new Vec2f(14, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/turtles.png"), 28, 7, 0, 0, 14, 7)
                    ).addVelocity(new Vec2f(-1, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(9, 6)),
                            new Vec2f(14, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/turtles.png"), 28, 7, 0, 0, 14, 7)
                    ).addVelocity(new Vec2f(-1, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(15, 6)),
                            new Vec2f(14, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/turtles.png"), 28, 7, 0, 0, 14, 7)
                    ).addVelocity(new Vec2f(-1, 0))
            );


            logs.add(
                    new Sprite(
                            getPos(new Vec2f(2, 4)),
                            new Vec2f(63, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/log.png"), 63, 7, 0, 0, 63, 7)
                    ).addVelocity(new Vec2f(2, 0))
            );


            logs.add(
                    new Sprite(
                            getPos(new Vec2f(3, 3)),
                            new Vec2f(28, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/turtles.png"), 28, 7, 0, 0, 28, 7)
                    ).addVelocity(new Vec2f(-2, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(9, 3)),
                            new Vec2f(28, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/turtles.png"), 28, 7, 0, 0, 28, 7)
                    ).addVelocity(new Vec2f(-2, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(15, 3)),
                            new Vec2f(28, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/turtles.png"), 28, 7, 0, 0, 28, 7)
                    ).addVelocity(new Vec2f(-2, 0))
            );


            logs.add(
                    new Sprite(
                            getPos(new Vec2f(1, 2)),
                            new Vec2f(35, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/log.png"), 63, 7, 0, 0, 35, 7)
                    ).addVelocity(new Vec2f(2, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(8, 2)),
                            new Vec2f(35, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/log.png"), 63, 7, 0, 0, 35, 7)
                    ).addVelocity(new Vec2f(2, 0))
            );
            logs.add(
                    new Sprite(
                            getPos(new Vec2f(14, 2)),
                            new Vec2f(35, 7),
                            new Image(Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/log.png"), 63, 7, 0, 0, 35, 7)
                    ).addVelocity(new Vec2f(2, 0))
            );
        }
    }

    @Override
    public synchronized void respawn() {
        super.respawn();

        frog.setPos(new Vec2f(WIDTH / 2 - TILE_SIZE / 2, 13 * TILE_SIZE));
        frog.setImage(new DirectionalImage(
                Identifier.of(GameDiscsMod.MOD_ID, "textures/games/sprite/frog.png"),
                7,
                28
        ));
        lastLine = (int)getTile(frog.getPos()).y;
    }

    @Override
    public synchronized void start() {

        // Calls start of super
        super.start();

        // Make everything on start
        // here
    }
    @Override
    public synchronized void tick() {
        // Calls tick of super
        super.tick();

        // Make animation tick or anything that ticks even if the game is in DIED state
        // here
    }
    @Override
    public synchronized void gameTick() {
        // Calls game tick of super
        super.gameTick();

        // Ticks all sprites and everything that ticks only when the game is running
        int i = 0;
        while (i < minecarts.size()) {
            Sprite minecart = minecarts.get(i);
            int oldX = (int)minecart.getX();
            minecart.tick();
            int newX = (int)minecart.getX();
            if (oldX > 0 && newX <= 0) {
                minecarts.add(
                        new Sprite(new Vec2f(WIDTH, minecart.getY()), minecart.getSize(), minecart.getImage()).addVelocity(minecart.getVelocity())
                );
            }
            if (newX + minecart.getWidth() < 0) {
                minecarts.remove(i);
                i--;
            }
            if (oldX + minecart.getWidth() < WIDTH && newX + minecart.getWidth() >= WIDTH) {
                minecarts.add(
                        new Sprite(new Vec2f(0 - minecart.getWidth(), minecart.getY()), minecart.getSize(), minecart.getImage()).addVelocity(minecart.getVelocity())
                );
            }
            if (newX > WIDTH) {
                minecarts.remove(i);
                i--;
            }

            if (minecart.isTouching(frog)) {
                lostLife();
            }

            i++;
        }

        Sprite logOn = null;

        i = 0;
        while (i < logs.size()) {
            Sprite log = logs.get(i);
            int oldX = (int)log.getX();
            log.tick();
            int newX = (int)log.getX();
            if (oldX > 0 && newX <= 0) {
                logs.add(
                        new Sprite(new Vec2f(WIDTH, log.getY()), log.getSize(), log.getImage()).addVelocity(log.getVelocity())
                );
            }
            if (newX + log.getWidth() < 0) {
                logs.remove(i);
                i--;
            }
            if (oldX + log.getWidth() < WIDTH && newX + log.getWidth() >= WIDTH) {
                logs.add(
                        new Sprite(new Vec2f(0 - log.getWidth(), log.getY()), log.getSize(), log.getImage()).addVelocity(log.getVelocity())
                );
            }
            if (newX > WIDTH) {
                logs.remove(i);
                i--;
            }

            Vec2f frogPos = frog.getCenterPos();
            if (frogPos.x > log.getX() && frogPos.y > log.getY() && frogPos.x < log.getX() + log.getWidth() && frogPos.y < log.getY() + log.getHeight()) {
                logOn = log;
            }

            i++;
        }

        int height = (int)getTile(frog.getPos()).y;
        if ((height > 1 && height < 7) && logOn == null) {
            lostLife();
        }
        else {
            if (logOn != null) {
                frog.moveBy(logOn.getVelocity());
            }
        }

        Vec2f frogPos = frog.getCenterPos();
        if (frogPos.x > WIDTH || frogPos.x < 0 || frogPos.y > HEIGHT || frogPos.y < 0) {
            lostLife();
        }

        frogPos = getTile(frog.getPos());
        if (frogPos.y == 1) {
            for (int j = 0; j < holes.size(); j++) {
                int hole = holes.get(j);
                if (frogPos.x > hole - 2 && frogPos.x < hole + 1) {
                    if (isHoleFull.get(j)) {
                        lostLife();
                    }
                    else {
                        isHoleFull.set(j, true);
                        score += 5;
                        respawn();
                        boolean flag = true;
                        for (boolean checkHole : isHoleFull) {
                            if (!checkHole) {
                                flag = false;
                            }
                        }
                        if (flag) {
                            score += 100;
                            win();
                        }
                    }
                }
            }
        }

        if (moveCooldown < 2) {
            moveCooldown++;
        }
    }

    @Override
    public int gameTickDuration() {
        return 2;
    }

    @Override
    public synchronized void die() {
        // Calling die of super
        super.die();

        // Execute everything that happens when player dies
        // here
        // Example: yourSprite.hide();
    }

    @Override
    public int maxLives() {
        return 5;
    }

    @Override
    public synchronized void render(DrawContext graphics, int posX, int posY) {
        // Calls render of super
        super.render(graphics, posX, posY);

        // Renders all sprites and everything
        for (Sprite minecart : minecarts) {
            minecart.render(graphics, posX, posY);
        }
        for (Sprite log : logs) {
            log.render(graphics, posX, posY);
        }

        frog.render(graphics, posX, posY);
        Vec2f frogPos = frog.getPos();
        int image = 0;
        if (frog.getImage() instanceof DirectionalImage direcional) {
            image = direcional.current();
        }

        for (int i = 0; i < holes.size(); i++) {
            if (isHoleFull.get(i)) {
                frog.setPos(getPos(new Vec2f(holes.get(i) - 0.5f, 1)));
                if (frog.getImage() instanceof DirectionalImage direcional) {
                    direcional.setImage(2);
                }
                frog.render(graphics, posX, posY);
            }
        }

        frog.setPos(frogPos);
        if (frog.getImage() instanceof DirectionalImage direcional) {
            direcional.setImage(image);
        }

        // Renders particles
        renderParticles(graphics, posX, posY);

        TextRenderer font = MinecraftClient.getInstance().textRenderer;
        graphics.drawText(font, Text.literal(String.valueOf(score)),  posX + 2, posY + HEIGHT - font.fontHeight, 0xFFFFFF, true);

        // Renders overlay
        renderOverlay(graphics, posX, posY);
    }
    @Override
    public synchronized void buttonDown(Button button) {
        // Calls buttonDown of super
        super.buttonDown(button);

        // Execute code when a specific button is pressed
        if (stage == GameStage.PLAYING && ticks > 5) {
            if (moveCooldown >= 2) {
                if (button == Button.UP) {
                    frog.moveBy(VecUtil.VEC_UP.multiply(TILE_SIZE));
                    if (frog.getImage() instanceof DirectionalImage image) {
                        image.setImage(0);
                    }
                    if (getTile(frog.getPos()).y < lastLine) {
                        lastLine = (int)getTile(frog.getPos()).y;
                        score++;
                    }
                    moveCooldown = 0;
                    soundPlayer.playJump();
                }
                if (button == Button.RIGHT) {
                    frog.moveBy(VecUtil.VEC_RIGHT.multiply(TILE_SIZE));
                    if (frog.getImage() instanceof DirectionalImage image) {
                        image.setImage(1);
                    }
                    moveCooldown = 0;
                    soundPlayer.playJump();
                }
                if (button == Button.DOWN) {
                    frog.moveBy(VecUtil.VEC_DOWN.multiply(TILE_SIZE));
                    if (frog.getImage() instanceof DirectionalImage image) {
                        image.setImage(2);
                    }
                    moveCooldown = 0;
                    soundPlayer.playJump();
                }
                if (button == Button.LEFT) {
                    frog.moveBy(VecUtil.VEC_LEFT.multiply(TILE_SIZE));
                    if (frog.getImage() instanceof DirectionalImage image) {
                        image.setImage(3);
                    }
                    moveCooldown = 0;
                    soundPlayer.playJump();
                }
            }
        }
    }
    @Override
    public Identifier getBackground() {
        // Change here:
        return Identifier.of(GameDiscsMod.MOD_ID, "textures/games/background/froggie_background.png");
    }
    @Override
    public boolean showScore() {
        return false;
    }
    @Override
    public Text getName() {
        // Change to name of your game
        return Text.translatable("gamediscs.froggie");
    }
    @Override
    public Identifier getIcon() {
        // Change icon here:
        return Identifier.of(GameDiscsMod.MOD_ID, "textures/item/game_disc_froggie.png");
    }
}
