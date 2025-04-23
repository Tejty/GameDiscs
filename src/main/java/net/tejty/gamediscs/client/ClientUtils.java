package net.tejty.gamediscs.client;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
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
        Minecraft.getInstance().setScreen(new GamingConsoleScreen(Component.translatable("gui.gamingconsole.title")));
    }

    private static final Map<Item, Supplier<Game>> GAMES = new IdentityHashMap<>();

    private static void registerGames() {
        GAMES.put(ItemRegistry.GAME_DISC_FLAPPY_BIRD.get(), FlappyBirdGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_SLIME.get(), SlimeGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_BLOCKTRIS.get(), BlocktrisGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_TNT_SWEEPER.get(), TntSweeperGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_PONG.get(), PongGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_FROGGIE.get(), FroggieGame::new);
        GAMES.put(ItemRegistry.GAME_DISC_RABBIT.get(), RabbitGame::new);
    }

    public static Game newGameFor(GameDiscItem item) {
        if (GAMES.isEmpty()) {
            registerGames();
        }

        Supplier<Game> sup = GAMES.get(item);
        if (sup == null) {
            throw new IllegalArgumentException("No game specified for " + item + " (" + BuiltInRegistries.ITEM.getKey(item) + ")");
        }
        return sup.get();
    }
}
