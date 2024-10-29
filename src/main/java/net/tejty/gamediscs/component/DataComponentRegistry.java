package net.tejty.gamediscs.component;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tejty.gamediscs.GameDiscsMod;

public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents REGISTRAR = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, GameDiscsMod.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BestScoreComponent>> BEST_SCORE = REGISTRAR.registerComponentType(
            "best_score",
            builder -> builder
                    .persistent(BestScoreComponent.CODEC)
                    .networkSynchronized(BestScoreComponent.STREAM_CODEC)
    );

    public static void register(IEventBus eventBus) {
        REGISTRAR.register(eventBus);
    }
}
