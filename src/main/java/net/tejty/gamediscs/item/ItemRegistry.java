package net.tejty.gamediscs.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.custom.GameDiscItem;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(GameDiscsMod.MOD_ID);

    public static final DeferredItem<Item> GAMING_CONSOLE = ITEMS.register("gaming_console",
            () -> new GamingConsoleItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final DeferredItem<Item> GAME_DISC_FLAPPY_BIRD = ITEMS.register("game_disc_flappy_bird",
            () -> new GameDiscItem(new Item.Properties().rarity(Rarity.RARE), Component.translatable("gamediscs.flappy_bird").withStyle(ChatFormatting.YELLOW)));
    public static final DeferredItem<Item> GAME_DISC_SLIME = ITEMS.register("game_disc_slime", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), Component.translatable("gamediscs.slime").withStyle(ChatFormatting.DARK_GREEN)));
    public static final DeferredItem<Item> GAME_DISC_BLOCKTRIS = ITEMS.register("game_disc_blocktris", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), Component.translatable("gamediscs.blocktris").withStyle(ChatFormatting.BLUE)));
    public static final DeferredItem<Item> GAME_DISC_TNT_SWEEPER = ITEMS.register("game_disc_tnt_sweeper", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), Component.translatable("gamediscs.tnt_sweeper").withStyle(ChatFormatting.RED)));
    public static final DeferredItem<Item> GAME_DISC_PONG = ITEMS.register("game_disc_pong", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), Component.translatable("gamediscs.pong_game").withStyle(ChatFormatting.WHITE)));
    public static final DeferredItem<Item> GAME_DISC_FROGGIE = ITEMS.register("game_disc_froggie", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), Component.translatable("gamediscs.froggie").withStyle(ChatFormatting.GREEN)));
    public static final DeferredItem<Item> GAME_DISC_RABBIT = ITEMS.register("game_disc_rabbit", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), Component.translatable("gamediscs.rabbit").withStyle(ChatFormatting.GOLD)));

    public static final DeferredItem<Item> REDSTONE_CIRCUIT = ITEMS.register("redstone_circuit",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PROCESSOR = ITEMS.register("processor",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> BATTERY = ITEMS.register("battery",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> DISPLAY = ITEMS.register("display",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> CONTROL_PAD = ITEMS.register("control_pad",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
