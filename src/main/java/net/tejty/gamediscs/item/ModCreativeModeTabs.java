package net.tejty.gamediscs.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tejty.gamediscs.GameDiscsMod;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GameDiscsMod.MODID);



    public static final RegistryObject<CreativeModeTab> GAME_DISCS_TAB = CREATIVE_MODE_TABS.register("game_discs_tab",
            () -> CreativeModeTab
                    .builder()
                    .icon(() -> new ItemStack(ModItems.GAMING_CONSOLE.get()))
                    .title(Component.translatable("creativetab.game_discs_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.GAMING_CONSOLE.get());
                        output.accept(ModItems.GAME_DISC_FLAPPY_BIRD.get());
                        output.accept(ModItems.GAME_DISC_SLIME.get());
                    })
                    .build()
    );



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
