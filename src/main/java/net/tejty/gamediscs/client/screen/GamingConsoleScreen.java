package net.tejty.gamediscs.client.screen;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.client.ClientUtils;
import net.tejty.gamediscs.games.controls.Button;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.item.custom.GameDiscItem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class GamingConsoleScreen extends Screen {
    private static final ResourceLocation BACKGROUD = ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/gui/gaming_console.png");
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
    private static final int QUIT = 81;
    private static final int RESTART = 82;

    private final Set<Integer> heldKeys = new HashSet<>();
    private boolean windowActive = true;

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

    // Game, the player is currently playing (if there is only Game, and not its child, it means there is no game selected, and game selection screen is shown)
    private Game game = new Game();

    public GamingConsoleScreen(Component title) {
        super(title);

        // Scans for games in player's inventory
        availableGames = scanForGames();
    }

    @Override
    public void tick() {
        if (updateWindowFocus()) {
            game.tick();
        }
    }

    // This screen doesn't pause the game when opened
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // Main rendering method
    @Override
    public void render(@NotNull GuiGraphics graphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(graphics, pMouseX, pMouseY, pPartialTick);
        super.render(graphics, pMouseX, pMouseY, pPartialTick);

        renderGameScreen(graphics, getConsoleX() + SCREEN_X, getConsoleY() + SCREEN_Y);
        renderButtons(graphics);
    }

    @Override
    public void renderBackground(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.renderBackground(graphics, mouseX, mouseY, partialTick);
        graphics.blit(BACKGROUD, getConsoleX(), getConsoleY(), 0, 0, 0, CONSOLE_WIDTH, CONSOLE_HEIGHT, 256, 256);
    }

    /**
     * Renders game screen, including game, game selection, scores, etc.
     * @param graphics GuiGraphics used for rendering
     * @param x X position of the game screen
     * @param y Y position of the game screen
     */
    private void renderGameScreen(GuiGraphics graphics, int x, int y) {
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
     * @param graphics GuiGraphics used for rendering
     * @param x X position
     * @param y Y position
     */
    private void renderGameSelection(GuiGraphics graphics, int x, int y) {
        // If there are some available games, it renders a selection marking on corresponding Y position
        if (!availableGames.isEmpty()) {
            graphics.blit(
                    ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "textures/gui/selected.png"),
                    x,
                    y + 3 + font.lineHeight + 18 * selected - (Math.max(0, selected - 3) * 18),
                    0, 0, 0, 140, 18, 140, 18
            );
        }

        // Rendering "Select game" title, on top of the screen
        graphics.drawString(
                font,
                Component.translatable("gui.gamingconsole.select_game").withStyle(ChatFormatting.BOLD),
                x + (Game.WIDTH - font.width(Component.translatable("gui.gamingconsole.select_game").withStyle(ChatFormatting.BOLD).getVisualOrderText())) / 2,
                y + 3 - (Math.max(0, selected - 3) * 18),
                0xace53b,
                false
        );

        // Rendering all available games
        for (int i = 0; i < availableGames.size(); i++) {
            // Rendering name of the game
            graphics.drawString(
                    font,
                    // If the game is currently selected, the title renders bold
                    Component.literal(availableGames.get(i).getName().getString()).withStyle(availableGames.get(i).getColor(), i == selected ? ChatFormatting.BOLD : ChatFormatting.ITALIC),
                    x + 22,
                    y + 4 + font.lineHeight + 18 * i + (18 - font.lineHeight) / 2 - (Math.max(0, selected - 3) * 18),
                    availableGames.get(i).getColor().getColor(),
                    false
            );
            // Rendering icon of the game
            graphics.blit(
                    availableGames.get(i).getIcon(),
                    x + 3,
                    y + 4 + font.lineHeight + 18 * i - (Math.max(0, selected - 3) * 18),
                    0, 0, 0, 16, 16, 16, 16
            );
        }
    }

    /**
     * Renders all visual buttons
     * @param graphics GuiGraphics used for rendering
     */
    private void renderButtons(GuiGraphics graphics) {
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
        Player player = Objects.requireNonNull(Minecraft.getInstance().player);

        // Going through each slot of player's inventory
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            // If the item is GameDisc, it creates the Game and adds it to the list
            if (player.getInventory().getItem(i).getItem() instanceof GameDiscItem disc) {
                games.add(ClientUtils.newGameFor(disc));
            }
        }
        return games;
    }

    private static Button buttonForKey(int key) {
        return switch (key) {
            case W -> Button.UP;
            case S -> Button.DOWN;
            case A -> Button.LEFT;
            case D -> Button.RIGHT;
            case SPACE -> Button.BUTTON1;
            case ENTER -> Button.BUTTON2;
            default -> null;
        };
    }

    private boolean updateWindowFocus() {
        boolean active = minecraft.isWindowActive();
        if (!active) {
            game.controls.reset();
        } else if (!windowActive) {
            // Focus loss can hide releases; held keys still need a release before reuse.
            heldKeys.removeIf(key -> !InputConstants.isKeyDown(minecraft.getWindow().getWindow(), key));
        }
        windowActive = active;
        return active;
    }

    @Override
    public boolean keyPressed(int key, int pScanCode, int pModifiers) {
        Button button = buttonForKey(key);
        if (button == null && key != QUIT && key != RESTART) {
            return super.keyPressed(key, pScanCode, pModifiers);
        }
        if (!updateWindowFocus()) {
            heldKeys.add(key);
            return true;
        }
        // Keep physical presses across game changes to consume OS repeats until release.
        if (!heldKeys.add(key)) {
            return true;
        }
        if (key == QUIT) {
            game.controls.reset();
            if (game.isEmpty()) {
                onClose();
            } else {
                game = new Game();
                game.soundPlayer.playConfirm();
            }
            return true;
        }
        if (key == RESTART) {
            if (!game.isEmpty()) {
                game.prepare();
                game.soundPlayer.playConfirm();
            }
            return true;
        }
        game.controls.setButton(button, true);
        if (game.isEmpty() && !availableGames.isEmpty()) {
            if (key == W || key == S) {
                selected = Math.floorMod(selected + (key == W ? -1 : 1), availableGames.size());
                game.soundPlayer.playSelect();
            } else if (key == SPACE || key == ENTER) {
                game.controls.reset();
                game = availableGames.get(selected);
                game.prepare();
                game.soundPlayer.playConfirm();
            }
        }
        return true;
    }

    @Override
    public boolean keyReleased(int pKeyCode, int pScanCode, int pModifiers) {
        Button button = buttonForKey(pKeyCode);
        if (button == null && pKeyCode != QUIT && pKeyCode != RESTART) {
            return super.keyReleased(pKeyCode, pScanCode, pModifiers);
        }
        updateWindowFocus();
        heldKeys.remove(pKeyCode);
        if (button != null) {
            game.controls.setButton(button, false);
        }
        return true;
    }

    @Override
    public void removed() {
        game.controls.reset();
        heldKeys.clear();
        super.removed();
    }
}
