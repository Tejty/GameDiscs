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
import net.tejty.gamediscs.game.games.FlappyBirdGame;
import net.tejty.gamediscs.item.custom.GameDiscItem;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, GameDiscsMod.MODID);



    public static final RegistryObject<Item> GAMING_CONSOLE = ITEMS.register("gaming_console",
            () -> new GamingConsoleItem(
                    new Item.Properties()
                            .rarity(Rarity.UNCOMMON)
            )
    );
    public static final RegistryObject<Item> GAME_DISC_FLAPPY_BIRD = ITEMS.register("game_disc_flappy_bird",
            () -> new GameDiscItem(
                    new Item.Properties()
                            .rarity(Rarity.RARE),
                    () -> new FlappyBirdGame(),
                    Component.translatable("gamediscs.flappy_bird").withStyle(ChatFormatting.YELLOW)
            )
    );



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
