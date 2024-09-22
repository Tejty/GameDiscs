package net.tejty.gamediscs.util.creativetab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.ItemRegistry;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GameDiscsMod.MODID);
    public static final RegistryObject<CreativeModeTab> GAME_DISCS_TAB = CREATIVE_MODE_TABS.register("game_discs_tab",
            () -> CreativeModeTab
                    .builder()
                    .icon(() -> new ItemStack(ItemRegistry.GAMING_CONSOLE.get()))
                    .title(Component.translatable("creativetab.game_discs_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ItemRegistry.GAMING_CONSOLE.get());
                        output.accept(ItemRegistry.REDSTONE_CIRCUIT.get());
                        output.accept(ItemRegistry.PROCESSOR.get());
                        output.accept(ItemRegistry.BATTERY.get());
                        output.accept(ItemRegistry.DISPLAY.get());
                        output.accept(ItemRegistry.CONTROL_PAD.get());
                        output.accept(ItemRegistry.GAME_DISC_FLAPPY_BIRD.get());
                        output.accept(ItemRegistry.GAME_DISC_SLIME.get());
                        output.accept(ItemRegistry.GAME_DISC_BLOCKTRIS.get());
                        output.accept(ItemRegistry.GAME_DISC_TNT_SWEEPER.get());
                        output.accept(ItemRegistry.GAME_DISC_PONG.get());
                        output.accept(ItemRegistry.GAME_DISC_FROGGIE.get());
                    })
                    .build()
    );
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
