package net.tejty.gamediscs.networking.packet;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.tejty.gamediscs.GameDiscsMod;

public record SetBestScorePacket(String game, int score) implements CustomPacketPayload {
    public static final Type<SetBestScorePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "update_best_score"));

    @Override
    public Type<SetBestScorePacket> type() {
        return TYPE;
    }
}
