package net.tejty.gamediscs.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.tejty.gamediscs.client.screen.GamingConsoleScreen;
import net.tejty.gamediscs.games.gamediscs.*;
import net.tejty.gamediscs.games.util.Game;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.item.custom.GameDiscItem;

import java.util.IdentityHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ClientUtils {
    public static void openConsoleScreen() {
        MinecraftClient.getInstance().setScreen(new GamingConsoleScreen(Text.translatable("gui.gamingconsole.title")));
    }

    private static final Map<Item, Supplier<Game>> GAMES = new IdentityHashMap<>();

    private static void registerGames() {
        GAMES.put(ItemRegistry.GAME_DISC_FLAPPY_BIRD, FlappyBirdGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_SLIME, SlimeGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_BLOCKTRIS, BlocktrisGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_TNT_SWEEPER, TntSweeperGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_PONG, PongGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_FROGGIE, FroggieGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_RABBIT, RabbitGame::new);
    }

    public static Game newGameFor(GameDiscItem item) {
        if (GAMES.isEmpty()) {
            registerGames();
        }

        Supplier<Game> sup = GAMES.get(item);
        if (sup == null) {
            throw new IllegalArgumentException("No game specified for " + item + " (" + Registries.ITEM.getKey(item) + ")");
        }
        return sup.get();
    }
}