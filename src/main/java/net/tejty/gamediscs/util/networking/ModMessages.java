package net.tejty.gamediscs.util.networking;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.util.networking.packet.SetBestScoreC2SPacket;
import net.tejty.gamediscs.util.networking.packet.SetBestScoreC2SPayload;

public class ModMessages {

    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(SetBestScoreC2SPayload.ID, SetBestScoreC2SPayload.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SetBestScoreC2SPayload.ID,
                SetBestScoreC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
