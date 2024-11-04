package net.tejty.gamediscs.util.creativetab;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.ItemRegistry;

import java.util.function.Supplier;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, GameDiscsMod.MOD_ID);

    public static final Supplier<CreativeModeTab> GAME_DISCS_TAB = CREATIVE_MODE_TABS.register("game_discs_tab",
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
                        // output.accept(ItemRegistry.GAME_DISC_RABBIT.get());
                    })
                    .build()
    );
    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
