package net.tejty.gamediscs.games.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.audio.SoundPlayer;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.controls.Controls;
import net.tejty.gamediscs.games.graphics.ParticleColor;
import net.tejty.gamediscs.games.graphics.Renderer;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;
import net.tejty.gamediscs.sounds.SoundRegistry;
import net.tejty.gamediscs.util.networking.ModMessages;
import net.tejty.gamediscs.util.networking.packet.SetBestScoreC2SPacket;
import net.tejty.gamediscs.util.networking.packet.SetBestScoreC2SPayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;

public class Game {
    // Current stage of the game
    public GameStage stage = GameStage.START;
    // Controls of the game
    public Controls controls = new Controls(this);
    // Sound player
    public SoundPlayer soundPlayer = new SoundPlayer();
    // Dimensions of the game area
    public static final int WIDTH = 140;
    public static final int HEIGHT = 100;
    // Random
    public final Random random = new Random();
    // Tick counter
    public int ticks = 0;
    // Score counter
    public int score = 0;
    // Lives
    public int lives = maxLives();

    public int maxLives() {
        return 1;
    }

    // Particles
    private List<Particle> particles = new ArrayList<>();
    public Game() {

    }

    /**
     * Resets all variables and prepares for start
     */
    public synchronized void prepare() {
        score = 0;
        lives = maxLives();
        respawn();
    }

    /**
     * Starts the game
     */
    public synchronized void start() {
        stage = GameStage.PLAYING;
        ticks = 1;
    }

    /**
     * Stops the game, shows die screen, and sets the best score
     */
    public synchronized void die() {
        if (getConsole().getItem() instanceof GamingConsoleItem) {
            // Tries to set the best score
            String gameName = this.getClass().getName().substring(this.getClass().getPackageName().length() + 1);
            if (GamingConsoleItem.getBestScore(getConsole(), gameName, MinecraftClient.getInstance().player) < score) {
                ClientPlayNetworking.send(new SetBestScoreC2SPayload(gameName, score));
                soundPlayer.playNewBest();
                spawnConfetti();
            } else {
                soundPlayer.playGameOver();
            }
        }

        // Sets game stage to DIED and resets tick counter
        stage = GameStage.DIED;
        ticks = 1;
    }

    public synchronized void lostLife() {
        lives--;
        soundPlayer.play(SoundRegistry.EXPLOSION);
        respawn();
        if (lives <= 0) {
            die();
        }
    }

    public synchronized void respawn() {
        stage = GameStage.START;
        ticks = 1;
        particles.clear();
    }

    /**
     * Stops the game, shows win screen, and sets the best score
     */
    public synchronized void win() {
        soundPlayer.playNewBest();
        spawnConfetti();
        if (getConsole().getItem() instanceof GamingConsoleItem) {
            // Tries to set the best score
            String gameName = this.getClass().getName().substring(this.getClass().getPackageName().length() + 1);
            if (GamingConsoleItem.getBestScore(getConsole(), gameName, MinecraftClient.getInstance().player) < score) {
                ClientPlayNetworking.send(new SetBestScoreC2SPayload(gameName, score));
            }
        }

        // Sets game stage to WON and resets tick counter
        stage = GameStage.WON;
        ticks = 1;
    }

    /**
     * @return Game Console this game is running at
     */
    private ItemStack getConsole() {
        // Gets player of this client
        PlayerEntity player = MinecraftClient.getInstance().player;
        assert player != null;

        // Returns item player has in mainhand or offhand, depending on if its Gaming Console
        ItemStack item = player.getMainHandStack();
        if (item.getItem() instanceof GamingConsoleItem) {
            return item;
        }
        else {
            item = player.getOffHandStack();
            if (item.getItem() instanceof GamingConsoleItem) {
                return item;
            }
        }
        // If there is no Gaming Console in mainhand or offhand of the player, it creates a new one
        return new ItemStack(ItemRegistry.GAMING_CONSOLE);
    }

    /**
     * Updates everything by one tick
     */
    public synchronized void tick() {
        // Calls gameTick depending on game stage and game tick duration
        if (stage == GameStage.PLAYING && ticks % gameTickDuration() == 0) {
            gameTick();
        }
        // Move particles
        int i = 0;
        while (i < particles.size()) {
            Particle particle = particles.get(i);
            particle.tick();
            if (particle.isDead()) {
                particles.remove(i);
                i--;
            }
            i++;
        }
        // Counts ticks
        ticks++;
    }

    /**
     * Updates everything in game by one tick
     */
    public synchronized void gameTick() {
    }

    /**
     * Renders the whole game
     * @param graphics DrawContext used for rendering
     * @param posX X position of the game area
     * @param posY Y position of the game area
     */
    public synchronized void render(DrawContext graphics, int posX, int posY) {
        // Renders background
        if (getBackground() != null) {
            graphics.drawTexture(RenderLayer::getGuiTextured, getBackground(), posX, posY, 0, 0, WIDTH, HEIGHT, WIDTH, HEIGHT);
        }
        // Renders overlay
        renderOverlay(graphics, posX, posY);
    }

    /**
     * Renders all overlay things including score, die screen, and "press any key" text
     * @param graphics DrawContext used for rendering
     * @param posX X position
     * @param posY Y position
     */
    public synchronized void renderOverlay(DrawContext graphics, int posX, int posY) {
        // saving font
        TextRenderer font = MinecraftClient.getInstance().textRenderer;

        // If outside the game
        if (stage != GameStage.PLAYING) {
            // Render "press any key" text
            if (showPressAnyKey()) {
                graphics.drawText(
                        font,
                        Text.translatable("gui.gamingconsole.press_any_key"),
                        posX + (WIDTH - font.getWidth(Text.translatable("gui.gamingconsole.press_any_key").asOrderedText())) / 2 + 1,
                        posY + HEIGHT - 1 - font.fontHeight - 1 + (ticks % 40 <= 20 ? 0 : 1),
                        0x373737,
                        false
                );
                graphics.drawText(
                        font,
                        Text.translatable("gui.gamingconsole.press_any_key"),
                        posX + (WIDTH - font.getWidth(Text.translatable("gui.gamingconsole.press_any_key").asOrderedText())) / 2,
                        posY + HEIGHT - 1 - font.fontHeight - 2 + (ticks % 40 <= 20 ? 0 : 1),
                        0xFFFFFF,
                        false
                );
            }
            // Renders died / won screen
            if (stage == GameStage.DIED || stage == GameStage.WON) {
                // Renders score board
                graphics.drawTexture(RenderLayer::getGuiTextured, Identifier.of(GameDiscsMod.MOD_ID, "textures/gui/score_board.png"), posX, posY, 0, 0, 140, 100, 140, 100);

                // Text based on won or died
                Text component = stage == GameStage.DIED ? Text.translatable("gui.gamingconsole.died").formatted(Formatting.BOLD, Formatting.DARK_RED) : Text.translatable("gui.gamingconsole.won").formatted(Formatting.BOLD, Formatting.DARK_GREEN);

                // Renders the outline of the text (four times renders the same text pushed by 1px to all directions)
                graphics.drawText(
                        font,
                        component,
                        posX + (WIDTH - font.getWidth(component.asOrderedText())) / 2,
                        posY + 29,
                        Objects.requireNonNull(component.getStyle().getColor()).getRgb(),
                        false
                );
                graphics.drawText(
                        font,
                        component,
                        posX + (WIDTH - font.getWidth(component.asOrderedText())) / 2,
                        posY + 31,
                        component.getStyle().getColor().getRgb(),
                        false
                );
                graphics.drawText(
                        font,
                        component,
                        posX + (WIDTH - font.getWidth(component.asOrderedText())) / 2 + 1,
                        posY + 30,
                        component.getStyle().getColor().getRgb(),
                        false
                );
                graphics.drawText(
                        font,
                        component,
                        posX + (WIDTH - font.getWidth(component.asOrderedText())) / 2 - 1,
                        posY + 30,
                        component.getStyle().getColor().getRgb(),
                        false
                );

                // Sets the text to be with lighter color
                component = stage == GameStage.DIED ? Text.translatable("gui.gamingconsole.died").formatted(Formatting.BOLD, Formatting.RED) : Text.translatable("gui.gamingconsole.won").formatted(Formatting.BOLD, Formatting.GREEN);

                // Renders the text
                graphics.drawText(
                        font,
                        component,
                        posX + (WIDTH - font.getWidth(component.asOrderedText())) / 2,
                        posY + 30,
                        Objects.requireNonNull(component.getStyle().getColor()).getRgb(),
                        false
                );

                // Renders score text
                component = Text.translatable("gui.gamingconsole.score").append(": ").append(String.valueOf(score)).formatted(Formatting.YELLOW);
                graphics.drawText(
                        font,
                        component,
                        posX + (WIDTH - font.getWidth(component.asOrderedText())) / 2,
                        posY + 35 + font.fontHeight,
                        Objects.requireNonNull(component.getStyle().getColor()).getRgb(),
                        false
                );

                // Renders best score text
                int bestScore = GamingConsoleItem.getBestScore(getConsole(), this.getClass().getName().substring(this.getClass().getPackageName().length() + 1), MinecraftClient.getInstance().player);
                component = Text.translatable(score >= bestScore ? "gui.gamingconsole.new_best_score" : "gui.gamingconsole.best_score").append(": ").append(String.valueOf(bestScore)).formatted(score >= bestScore ? Formatting.GREEN : Formatting.YELLOW);
                graphics.drawText(
                        font,
                        component,
                        posX + (WIDTH - font.getWidth(component.asOrderedText())) / 2,
                        posY + 50 + font.fontHeight,
                        Objects.requireNonNull(component.getStyle().getColor()).getRgb(),
                        false
                );
            }
        }
        else {
            // If current game has score box, it renders it
            if (showScoreBox() && showScore()) {
                graphics.drawTexture(RenderLayer::getGuiTextured, Identifier.of(GameDiscsMod.MOD_ID, "textures/gui/score_box.png"), posX, posY, 0, 0, 140, 100, 140, 100);
            }

            if (showScore()) {
                // Renders score
                graphics.drawText(
                        font,
                        (scoreText() ? Text.translatable("gui.gamingconsole.score").append(": ") : Text.empty()).append(String.valueOf(score)),
                        posX + 2,
                        posY + 2,
                        0x373737,
                        false
                );
                graphics.drawText(
                        font,
                        (scoreText() ? Text.translatable("gui.gamingconsole.score").append(": ") : Text.empty()).append(String.valueOf(score)),
                        posX + 1,
                        posY + 1,
                        scoreColor(),
                        false
                );
            }
        }

        for (Particle particle : particles) {
            if (particle.isForOverlay()) {
                particle.render(graphics, posX, posY, stage);
            }
        }
    }

    public synchronized void renderParticles(DrawContext graphics, int posX, int posY) {
        for (Particle particle : particles) {
            particle.render(graphics, posX, posY, stage);
        }
    }

    /**
     * On button down
     * @param button The button that was pressed
     */
    public synchronized void buttonDown(Button button) {
        soundPlayer.playClick(true);
        // Starts the game
        if ((stage == GameStage.START || stage == GameStage.RETRY) && ticks > 8) {
            start();
        }
        // Prepares the game
        else if ((stage == GameStage.WON || stage == GameStage.DIED) && ticks > 8) {
            prepare();
        }
    }

    public Particle addParticle(Particle particle) {
        particles.add(particle);
        return particle;
    }
    public void spawnParticleExplosion(Supplier<Renderer> renderer, Vec2f pos, int count, int speed, int lifetime, ParticleLevel level) {
        for (int i = 0; i < count; i++) {
            Particle particle = new Particle(pos, renderer.get(), random.nextInt(lifetime / 2, lifetime), level);
            particle.setVelocity(new Vec2f(random.nextFloat(-speed, speed), random.nextFloat(-speed, speed)));
            particles.add(particle);
        }
    }
    public void spawnParticleExplosion(Vec2f pos, int count, int speed, int lifetime, ParticleLevel level) {
        soundPlayer.play(SoundEvents.ENTITY_GENERIC_EXPLODE.value(), 1.5f, 0.1f);
        for (int i = 0; i < count; i++) {
            Particle particle = new ExplosionParticle(pos, random.nextInt(lifetime / 2, lifetime), level);
            particle.setVelocity(new Vec2f(random.nextFloat(-speed, speed), random.nextFloat(-speed, speed)));
            particles.add(particle);
        }
    }

    public void spawnConfetti() {
        for (int i = 0; i < 30; i++) {
            Particle particle = new ConfettiParticle(new Vec2f(0, HEIGHT), ParticleColor.random(random), random.nextInt(50, 70), ParticleLevel.OVERLAY);
            particle.setVelocity(new Vec2f(random.nextFloat(1, 10), random.nextFloat(-25, -10)));
            particles.add(particle);
        }
        for (int i = 0; i < 30; i++) {
            Particle particle = new ConfettiParticle(new Vec2f(WIDTH, HEIGHT), ParticleColor.random(random), random.nextInt(50, 70), ParticleLevel.OVERLAY);
            particle.setVelocity(new Vec2f(random.nextFloat(-10, -1), random.nextFloat(-25, -10)));
            particles.add(particle);
        }
    }

    /**
     * On button up
     * @param button The button that was released
     */
    public synchronized void buttonUp(Button button) {
        soundPlayer.playClick(false);
    }

    /**
     * @return Game tickt duration
     */
    public int gameTickDuration() {
        return 1; // Determines how often the game ticks (1 game tick duration = 20 game ticks per second, 2 game tick duration = 10 game ticks per second)
    }

    /**
     * @return Resource location of background image
     */
    public Identifier getBackground() {
        return null;
    }

    /**
     * @return Determines if this game shows score box
     */
    public boolean showScoreBox() {
        return true;
    }

    public boolean showScore() {
        return true;
    }

    public boolean showPressAnyKey() {
        return true;
    }

    /**
     * @return Color of score
     */
    public int scoreColor() {
        return 0xFFFFFF; // Default is white
    }

    /**
     * @return Determines if score will be rendered with "Score:" before the number
     */
    public boolean scoreText() {
        return true; // Default is true
    }

    /**
     * @return Display name of the game
     */
    public Text getName() {return Text.empty();}

    /**
     * @return Resource location to icon of the game
     */
    public Identifier getIcon() {return null;}

    /**
     * @return Display color of the game
     */
    public Formatting getColor() {return Formatting.YELLOW;}

    /**
     * @return True if the game is an empty game (default Game, not its child), false otherwise
     */
    public boolean isEmpty() {
        return this.getClass().equals(Game.class);
    }
}
