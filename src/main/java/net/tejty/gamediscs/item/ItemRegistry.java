package net.tejty.gamediscs.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.gamediscs.*;
import net.tejty.gamediscs.item.custom.GameDiscItem;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;
public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GameDiscsMod.MODID);

    public static final RegistryObject<Item> GAMING_CONSOLE = ITEMS.register("gaming_console",
            () -> new GamingConsoleItem(new Item.Properties().rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GAME_DISC_FLAPPY_BIRD = ITEMS.register("game_disc_flappy_bird",
            () -> new GameDiscItem(new Item.Properties().rarity(Rarity.RARE), FlappyBirdGame::new, Component.translatable("gamediscs.flappy_bird").withStyle(ChatFormatting.YELLOW)));
    public static final RegistryObject<Item> GAME_DISC_SLIME = ITEMS.register("game_disc_slime", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), SlimeGame::new, Component.translatable("gamediscs.slime").withStyle(ChatFormatting.DARK_GREEN)));
    public static final RegistryObject<Item> GAME_DISC_BLOCKTRIS = ITEMS.register("game_disc_blocktris", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), BlocktrisGame::new, Component.translatable("gamediscs.blocktris").withStyle(ChatFormatting.BLUE)));
    public static final RegistryObject<Item> GAME_DISC_TNT_SWEEPER = ITEMS.register("game_disc_tnt_sweeper", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), TntSweeperGame::new, Component.translatable("gamediscs.tnt_sweeper").withStyle(ChatFormatting.RED)));
    public static final RegistryObject<Item> GAME_DISC_PONG = ITEMS.register("game_disc_pong", () -> new GameDiscItem(
                    new Item.Properties().rarity(Rarity.RARE), PongGame::new, Component.translatable("gamediscs.pong_game").withStyle(ChatFormatting.WHITE)));



    public static final RegistryObject<Item> REDSTONE_CIRCUIT = ITEMS.register("redstone_circuit",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PROCESSOR = ITEMS.register("processor",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BATTERY = ITEMS.register("battery",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DISPLAY = ITEMS.register("display",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONTROL_PAD = ITEMS.register("control_pad",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
