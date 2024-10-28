package net.tejty.gamediscs.networking;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.tejty.gamediscs.GameDiscsMod;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = GameDiscsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModMessages {

    @SubscribeEvent
    public static void registerPayloads(final @NotNull RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(GameDiscsMod.MOD_ID).versioned("GD.1");
    }
}
