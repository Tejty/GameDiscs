package net.tejty.gamediscs.item;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.gamediscs.*;
import net.tejty.gamediscs.item.custom.GameDiscItem;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;

public class ItemRegistry {
    public static final Item GAMING_CONSOLE = registerItem("gaming_console",
            new GamingConsoleItem(new Item.Settings().rarity(Rarity.UNCOMMON)));

    public static final Item GAME_DISC_FLAPPY_BIRD = registerItem("game_disc_flappy_bird",
            new GameDiscItem(new Item.Settings().rarity(Rarity.RARE), FlappyBirdGame::new,
                    Text.translatable("gamediscs.flappy_bird").formatted(Formatting.YELLOW)));

    public static final Item GAME_DISC_SLIME = registerItem("game_disc_slime",
            new GameDiscItem(new Item.Settings().rarity(Rarity.RARE), SlimeGame::new,
                    Text.translatable("gamediscs.slime").formatted(Formatting.DARK_GREEN)));

    public static final Item GAME_DISC_BLOCKTRIS = registerItem("game_disc_blocktris",
            new GameDiscItem(new Item.Settings().rarity(Rarity.RARE), BlocktrisGame::new,
                    Text.translatable("gamediscs.blocktris").formatted(Formatting.BLUE)));

    public static final Item GAME_DISC_TNT_SWEEPER = registerItem("game_disc_tnt_sweeper",
            new GameDiscItem(new Item.Settings().rarity(Rarity.RARE), TntSweeperGame::new,
                    Text.translatable("gamediscs.tnt_sweeper").formatted(Formatting.RED)));

    public static final Item GAME_DISC_PONG = registerItem("game_disc_pong",
            new GameDiscItem(new Item.Settings().rarity(Rarity.RARE), PongGame::new,
                    Text.translatable("gamediscs.pong_game").formatted(Formatting.WHITE)));

    public static final Item GAME_DISC_FROGGIE = registerItem("game_disc_froggie",
            new GameDiscItem(new Item.Settings().rarity(Rarity.RARE), FroggieGame::new,
                    Text.translatable("gamediscs.froggie").formatted(Formatting.GREEN)));


    public static final Item REDSTONE_CIRCUIT = registerItem("redstone_circuit",
            new Item(new Item.Settings()));

    public static final Item PROCESSOR = registerItem("processor",
            new Item(new Item.Settings()));

    public static final Item BATTERY = registerItem("battery",
            new Item(new Item.Settings()));

    public static final Item DISPLAY = registerItem("display",
            new Item(new Item.Settings()));

    public static final Item CONTROL_PAD = registerItem("control_pad",
            new Item(new Item.Settings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(GameDiscsMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        GameDiscsMod.LOGGER.info("Registering Mod Items for " + GameDiscsMod.MOD_ID);
    }
}
