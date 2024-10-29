package net.tejty.gamediscs.networking;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.networking.packet.SetBestScorePacket;
import org.jetbrains.annotations.NotNull;

@EventBusSubscriber(modid = GameDiscsMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class GDMessages {

    @SubscribeEvent
    public static void registerPayloads(final @NotNull RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(GameDiscsMod.MOD_ID).versioned("GD.1");
        registrar.playToServer(
                SetBestScorePacket.TYPE,
                SetBestScorePacket.STREAM_CODEC,
                SetBestScorePacket::updateBestScore
        );
    }
}
