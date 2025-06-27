package net.tejty.gamediscs.item;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.custom.GameDiscItem;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;

import java.util.function.Function;

public class ItemRegistry {
    public static final Item GAMING_CONSOLE = registerItem("gaming_console",
            settings -> new GamingConsoleItem(settings.rarity(Rarity.UNCOMMON)));

    public static final Item GAME_DISC_FLAPPY_BIRD = registerItem("game_disc_flappy_bird",
            settings -> new GameDiscItem(settings.rarity(Rarity.RARE), Text.translatable("gamediscs.flappy_bird").formatted(Formatting.YELLOW)));
    public static final Item GAME_DISC_SLIME = registerItem("game_disc_slime",
            settings -> new GameDiscItem(settings.rarity(Rarity.RARE), Text.translatable("gamediscs.slime").formatted(Formatting.DARK_GREEN)));
    public static final Item GAME_DISC_BLOCKTRIS = registerItem("game_disc_blocktris",
            settings -> new GameDiscItem(settings.rarity(Rarity.RARE), Text.translatable("gamediscs.blocktris").formatted(Formatting.BLUE)));
    public static final Item GAME_DISC_TNT_SWEEPER = registerItem("game_disc_tnt_sweeper",
            settings -> new GameDiscItem(settings.rarity(Rarity.RARE), Text.translatable("gamediscs.tnt_sweeper").formatted(Formatting.RED)));
    public static final Item GAME_DISC_PONG = registerItem("game_disc_pong",
            settings -> new GameDiscItem(settings.rarity(Rarity.RARE), Text.translatable("gamediscs.pong_game").formatted(Formatting.WHITE)));
    public static final Item GAME_DISC_FROGGIE = registerItem("game_disc_froggie",
            settings -> new GameDiscItem(settings.rarity(Rarity.RARE), Text.translatable("gamediscs.froggie").formatted(Formatting.GREEN)));
    public static final Item GAME_DISC_RABBIT = registerItem("game_disc_rabbit",
            settings -> new GameDiscItem(settings.rarity(Rarity.RARE), Text.translatable("gamediscs.rabbit").formatted(Formatting.GOLD)));



    public static final Item REDSTONE_CIRCUIT = registerItem("redstone_circuit", Item::new);

    public static final Item PROCESSOR = registerItem("processor", Item::new);

    public static final Item BATTERY = registerItem("battery", Item::new);

    public static final Item DISPLAY = registerItem("display", Item::new);

    public static final Item CONTROL_PAD = registerItem("control_pad", Item::new);


    private static Item registerItem(String name, Function<Item.Settings, Item> function) {
        return Registry.register(Registries.ITEM, Identifier.of(GameDiscsMod.MOD_ID, name),
                function.apply(new Item.Settings().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(GameDiscsMod.MOD_ID, name)))));
    }

    public static void registerModItems() {
        GameDiscsMod.LOGGER.info("Registering Mod Items for " + GameDiscsMod.MOD_ID);
        registerTooltips();
    }

    public static void registerTooltips() {
        ItemTooltipCallback.EVENT.register((itemStack, tooltipContext, tooltipType, list) ->{
            if (!(itemStack.getItem() instanceof GameDiscItem)) {
                return;
            }
            ((GameDiscItem) itemStack.getItem())
                    .renderTooltip(itemStack, tooltipContext, tooltipType, list);
        });
    }
}
