package net.tejty.gamediscs.client.screen;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.client.ClientUtils;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.item.custom.GameDiscItem;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GamingConsoleScreen extends Screen {
    private static final Identifier BACKGROUD = Identifier.of(GameDiscsMod.MOD_ID, "textures/gui/gaming_console.png");
    private static final int CONSOLE_WIDTH = 160;
    private static final int CONSOLE_HEIGHT = 198;

    // Position of the game screen relative to the left top corner of the console
    private static final int SCREEN_X = 10;
    private static final int SCREEN_Y = 10;

    // Key codes of the using keys
    // TODO configurable keys
    private static final int W = 87;
    private static final int S = 83;
    private static final int A = 65;
    private static final int D = 68;
    private static final int SPACE = 32;
    private static final int ENTER = 257;

    // Visual buttons that allow you to visually see if the button is pressed or not
    private static final VisualButton W_BUTTON = new VisualButton(BACKGROUD, 256, 256, 33, 121, 14, 24, 183, 0, 24);
    private static final VisualButton A_BUTTON = new VisualButton(BACKGROUD, 256, 256, 17, 137, 23, 15, 160, 0, 24);
    private static final VisualButton D_BUTTON = new VisualButton(BACKGROUD, 256, 256, 40, 137, 23, 15, 197, 0, 24);
    private static final VisualButton S_BUTTON = new VisualButton(BACKGROUD, 256, 256, 33, 145, 14, 23, 220, 0, 24);
    private static final VisualButton B1_BUTTON = new VisualButton(BACKGROUD, 256, 256, 96, 136, 16, 16, 234, 0, 24);
    private static final VisualButton B2_BUTTON = new VisualButton(BACKGROUD, 256, 256, 128, 128, 16, 16, 234, 0, 24);

    // Calculates position of the console on screen
    private int getConsoleX() {
        return (this.width - CONSOLE_WIDTH) / 2;
    }
    private int getConsoleY() {
        return (this.height - CONSOLE_HEIGHT) / 2;
    }

    // List of games that are available to play
    private List<Game> availableGames = new ArrayList<>();

    // Currently selected game
    private int selected = 0;

    // Game, the player is currently playing (if there is only Game, and not its child, it means there is no game selected, and game selection screen is showed)
    private Game game = new Game();

    public GamingConsoleScreen(Text title) {
        super(title);

        // Scans for games in player's inventory
        availableGames = scanForGames();

        // Creates timer that calls tick() in Game every 50ms (20 times per second)
        Timer timer = new Timer(50, e -> {
            if (game != null) {
                game.tick();
            }
        });

        // Starts the timer
        timer.start();
    }

    // This screen doesn't pause the game when opened
    @Override
    public boolean shouldPause() {
        return false;
    }

    // Main rendering method
    @Override
    public void render(DrawContext graphics, int pMouseX, int pMouseY, float pPartialTick) {
        super.render(graphics, pMouseX, pMouseY, pPartialTick);
        renderGameScreen(graphics, getConsoleX() + SCREEN_X, getConsoleY() + SCREEN_Y);

        renderButtons(graphics);
    }

    @Override
    public void renderBackground(DrawContext context, int mouseX, int mouseY, float delta) {
        super.renderBackground(context, mouseX, mouseY, delta);
        context.drawTexture(RenderPipelines.GUI_TEXTURED, BACKGROUD, getConsoleX(), getConsoleY(), 0, 0, CONSOLE_WIDTH, CONSOLE_HEIGHT, 256, 256);
    }

    /**
     * Renders game screen, including game, game selection, scores, etc.
     * @param graphics DrawContext used for rendering
     * @param x X position of the game screen
     * @param y Y position of the game screen
     */
    private void renderGameScreen(DrawContext graphics, int x, int y) {
        // Makes sure that nothing is rendered outside the game screen
        graphics.enableScissor(x, y, x + Game.WIDTH, y + Game.HEIGHT);

        // Renders the game itself
        if (!game.isEmpty()) {
            game.render(graphics, x, y);
        }
        else {
            // If the game is empty, renders game selection screen
            renderGameSelection(graphics, x, y);
        }

        // Allowing rendering everywhere
        graphics.disableScissor();
    }

    /**
     * Renders game selection screen
     * @param graphics DrawContext used for rendering
     * @param x X position
     * @param y Y position
     */
    private void renderGameSelection(DrawContext graphics, int x, int y) {
        // If there are some available games, it renders a selection marking on corresponding Y position
        if (!availableGames.isEmpty()) {
            graphics.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    Identifier.of(GameDiscsMod.MOD_ID, "textures/gui/selected.png"),
                    x, y + 3 + textRenderer.fontHeight + 18 * selected - (Math.max(0, selected - 3) * 18),
                    0, 0, 140, 18, 140, 18
            );
        }
        // Rendering "Select game" title, on top of the screen
        graphics.drawText(
                textRenderer,
                Text.translatable("gui.gamingconsole.select_game").formatted(Formatting.BOLD),
                x + (Game.WIDTH - textRenderer.getWidth(Text.translatable("gui.gamingconsole.select_game").formatted(Formatting.BOLD).asOrderedText())) / 2,
                y + 3 - (Math.max(0, selected - 3) * 18),
                0xace53b,
                false
        );

        // Rendering all available games
        for (int i = 0; i < availableGames.size(); i++) {
            // Rendering name of the game
            graphics.drawText(
                    textRenderer,
                    // If the game is currently selected, the title renders bold
                    Text.literal(availableGames.get(i).getName().getString()).formatted(availableGames.get(i).getColor(), i == selected ? Formatting.BOLD : Formatting.ITALIC),
                    x + 22,
                    y + 4 + textRenderer.fontHeight + 18 * i + (18 - textRenderer.fontHeight) / 2 - (Math.max(0, selected - 3) * 18),
                    availableGames.get(i).getColor().getColorValue(),
                    false
            );
            // Rendering icon of the game
            graphics.drawTexture(
                    RenderPipelines.GUI_TEXTURED,
                    availableGames.get(i).getIcon(),
                    x + 3,
                    y + 4 + textRenderer.fontHeight + 18 * i - (Math.max(0, selected - 3) * 18),
                    0, 0, 16, 16, 16, 16
            );
        }
    }

    /**
     * Renders all visual buttons
     * @param graphics DrawContext used for rendering
     */
    private void renderButtons(DrawContext graphics) {
        W_BUTTON.render(graphics, getConsoleX(), getConsoleY(), game.controls.isButtonDown(Button.UP));
        A_BUTTON.render(graphics, getConsoleX(), getConsoleY(), game.controls.isButtonDown(Button.LEFT));
        D_BUTTON.render(graphics, getConsoleX(), getConsoleY(), game.controls.isButtonDown(Button.RIGHT));
        S_BUTTON.render(graphics, getConsoleX(), getConsoleY(), game.controls.isButtonDown(Button.DOWN));
        B1_BUTTON.render(graphics, getConsoleX(), getConsoleY(), game.controls.isButtonDown(Button.BUTTON1));
        B2_BUTTON.render(graphics, getConsoleX(), getConsoleY(), game.controls.isButtonDown(Button.BUTTON2));
    }

    /**
     * Scans for all games, player currently has in inventory
     * @return List of found games
     */
    public List<Game> scanForGames() {
        // Creating the list of the games
        List<Game> games = new ArrayList<>();
        PlayerEntity player = Objects.requireNonNull(MinecraftClient.getInstance().player);

        // Going through each slot of player's inventory
        for (int i = 0; i < player.getInventory().size(); i++) {
            // If the item is GameDisc, it creates the Game and adds it to the list
            if (player.getInventory().getStack(i).getItem() instanceof GameDiscItem disc) {
                games.add(ClientUtils.newGameFor(disc));
            }
        }

        return games;
    }

    // Main method for pressed keys
    @Override
    public boolean keyPressed(int key, int pScanCode, int pModifiers) {
        // This method should return true if the key press was consumed. So it saves it in this flag variable
        boolean flag = false;

        // If the game is not empty, tries to quit or reset the game if the corresponding key was pressed
        if (!game.isEmpty()) {
            // If the key was Q, returns to game selection (sets game to empty Game)
            if (key == 81) {
                game = new Game();
                flag = true;
                game.soundPlayer.playConfirm();
            }
            // If the key was R, resets the game by calling prepare() method
            else if (key == 82) {
                game.prepare();
                flag = true;
                game.soundPlayer.playConfirm();
            }
        }
        else {
            if (key == 81) {
                this.client.setScreen(null);
                return true;
            }
        }
        if (game != null) {
            // Sets the buttons in game's controls to pressed state if the button was pressed
            switch (key) {
                case W -> {
                    game.controls.setButton(Button.UP, true);
                    flag = true;
                }
                case S -> {
                    game.controls.setButton(Button.DOWN, true);
                    flag = true;
                }
                case A -> {
                    game.controls.setButton(Button.LEFT, true);
                    flag = true;
                }
                case D -> {
                    game.controls.setButton(Button.RIGHT, true);
                    flag = true;
                }
                case SPACE -> {
                    game.controls.setButton(Button.BUTTON1, true);
                    flag = true;
                }
                case ENTER -> {
                    game.controls.setButton(Button.BUTTON2, true);
                    flag = true;
                }
            }
        }
        if (game.isEmpty()) {
            // If we are in game selection, it must handle the keys by itself
            // W moves the selection up
            if (key == W) {
                int newSelected = selected - 1;
                if (newSelected < 0) {
                    newSelected = availableGames.size() - 1;
                }
                selected = newSelected;
                flag = true;
                game.soundPlayer.playSelect();
            }
            // S moves the selection down
            if (key == S) {
                int newSelected = selected + 1;
                if (newSelected > availableGames.size() - 1) {
                    newSelected = 0;
                }
                selected = newSelected;
                flag = true;
                game.soundPlayer.playSelect();
            }
            // Space selects game
            if ((key == SPACE || key == ENTER) && !availableGames.isEmpty()) {
                Game newGame = availableGames.get(selected);
                newGame.prepare();
                game = newGame;
                flag = true;
                game.soundPlayer.playConfirm();
            }
        }
        return (super.keyPressed(key, pScanCode, pModifiers) || flag);
    }

    // Main method for handling key releasing
    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        // This method should return true if the key release was consumed, so it saves it in this flag variable
        boolean flag = false;

        if (game != null) {
            // Trying to set button in game's controls to released state if the corresponding key was released
            switch (pKeyCode) {
                case W -> {
                    game.controls.setButton(Button.UP, false);
                    flag = true;
                }
                case S -> {
                    game.controls.setButton(Button.DOWN, false);
                    flag = true;
                }
                case A -> {
                    game.controls.setButton(Button.LEFT, false);
                    flag = true;
                }
                case D -> {
                    game.controls.setButton(Button.RIGHT, false);
                    flag = true;
                }
                case SPACE -> {
                    game.controls.setButton(Button.BUTTON1, false);
                    flag = true;
                }
                case ENTER -> {
                    game.controls.setButton(Button.BUTTON2, false);
                    flag = true;
                }
            }
        }
        return (super.keyPressed(pKeyCode, pScanCode, pModifiers) || flag);
    }

    @Override
    public void close() {
        super.close();

        game = null;
    }
}
