package net.tejty.gamediscs.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.custom.GameDiscItem;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;

public class ItemRegistry {
    public static final Item GAMING_CONSOLE = registerItem("gaming_console",
            new GamingConsoleItem(new FabricItemSettings().rarity(Rarity.UNCOMMON)));

    public static final Item GAME_DISC_FLAPPY_BIRD = registerItem("game_disc_flappy_bird",
            new GameDiscItem(new FabricItemSettings().rarity(Rarity.RARE), Text.translatable("gamediscs.flappy_bird").formatted(Formatting.YELLOW)));
    public static final Item GAME_DISC_SLIME = registerItem("game_disc_slime",
            new GameDiscItem(new FabricItemSettings().rarity(Rarity.RARE), Text.translatable("gamediscs.slime").formatted(Formatting.DARK_GREEN)));
    public static final Item GAME_DISC_BLOCKTRIS = registerItem("game_disc_blocktris",
            new GameDiscItem(new FabricItemSettings().rarity(Rarity.RARE), Text.translatable("gamediscs.blocktris").formatted(Formatting.BLUE)));
    public static final Item GAME_DISC_TNT_SWEEPER = registerItem("game_disc_tnt_sweeper",
            new GameDiscItem(new FabricItemSettings().rarity(Rarity.RARE), Text.translatable("gamediscs.tnt_sweeper").formatted(Formatting.RED)));
    public static final Item GAME_DISC_PONG = registerItem("game_disc_pong",
            new GameDiscItem(new FabricItemSettings().rarity(Rarity.RARE), Text.translatable("gamediscs.pong_game").formatted(Formatting.WHITE)));
    public static final Item GAME_DISC_FROGGIE = registerItem("game_disc_froggie",
            new GameDiscItem(new FabricItemSettings().rarity(Rarity.RARE), Text.translatable("gamediscs.froggie").formatted(Formatting.GREEN)));
    public static final Item GAME_DISC_RABBIT = registerItem("game_disc_rabbit",
            new GameDiscItem(new FabricItemSettings().rarity(Rarity.RARE), Text.translatable("gamediscs.rabbit").formatted(Formatting.GOLD)));



    public static final Item REDSTONE_CIRCUIT = registerItem("redstone_circuit",
            new Item(new FabricItemSettings()));

    public static final Item PROCESSOR = registerItem("processor",
            new Item(new FabricItemSettings()));

    public static final Item BATTERY = registerItem("battery",
            new Item(new FabricItemSettings()));

    public static final Item DISPLAY = registerItem("display",
            new Item(new FabricItemSettings()));

    public static final Item CONTROL_PAD = registerItem("control_pad",
            new Item(new FabricItemSettings()));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(GameDiscsMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        GameDiscsMod.LOGGER.info("Registering Mod Items for " + GameDiscsMod.MOD_ID);
    }
}
