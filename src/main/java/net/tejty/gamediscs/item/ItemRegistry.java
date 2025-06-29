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

    public static final DeferredItem<Item> GAMING_CONSOLE = ITEMS.registerItem("gaming_console",
            GamingConsoleItem::new, new Item.Properties().rarity(Rarity.UNCOMMON));
    public static final DeferredItem<Item> GAME_DISC_FLAPPY_BIRD = ITEMS.registerItem("game_disc_flappy_bird",
            prop -> new GameDiscItem(prop, Component.translatable("gamediscs.flappy_bird").withStyle(ChatFormatting.YELLOW)),
            new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GAME_DISC_SLIME = ITEMS.registerItem("game_disc_slime", prop -> new GameDiscItem(
                    prop, Component.translatable("gamediscs.slime").withStyle(ChatFormatting.DARK_GREEN)), new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GAME_DISC_BLOCKTRIS = ITEMS.registerItem("game_disc_blocktris", prop -> new GameDiscItem(
                    prop, Component.translatable("gamediscs.blocktris").withStyle(ChatFormatting.BLUE)), new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GAME_DISC_TNT_SWEEPER = ITEMS.registerItem("game_disc_tnt_sweeper", prop -> new GameDiscItem(
                    prop, Component.translatable("gamediscs.tnt_sweeper").withStyle(ChatFormatting.RED)), new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GAME_DISC_PONG = ITEMS.registerItem("game_disc_pong", prop -> new GameDiscItem(
                    prop, Component.translatable("gamediscs.pong").withStyle(ChatFormatting.WHITE)), new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GAME_DISC_FROGGIE = ITEMS.registerItem("game_disc_froggie", prop -> new GameDiscItem(
                    prop, Component.translatable("gamediscs.froggie").withStyle(ChatFormatting.GREEN)), new Item.Properties().rarity(Rarity.RARE));
    public static final DeferredItem<Item> GAME_DISC_RABBIT = ITEMS.registerItem("game_disc_rabbit", prop -> new GameDiscItem(
                    prop, Component.translatable("gamediscs.rabbit").withStyle(ChatFormatting.GOLD)), new Item.Properties().rarity(Rarity.RARE));

    public static final DeferredItem<Item> REDSTONE_CIRCUIT = ITEMS.registerItem("redstone_circuit", Item::new);
    public static final DeferredItem<Item> PROCESSOR = ITEMS.registerItem("processor", Item::new);
    public static final DeferredItem<Item> BATTERY = ITEMS.registerItem("battery", Item::new);
    public static final DeferredItem<Item> DISPLAY = ITEMS.registerItem("display", Item::new);
    public static final DeferredItem<Item> CONTROL_PAD = ITEMS.registerItem("control_pad", Item::new);

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
