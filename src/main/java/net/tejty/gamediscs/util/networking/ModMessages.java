package net.tejty.gamediscs.util.networking;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.util.networking.packet.SetBestScoreC2SPacket;

public class ModMessages {
    public static final Identifier SET_BEST_SCORE_ID = new Identifier(GameDiscsMod.MOD_ID, "set_best_score");

    public static void registerC2SPackets() {
        ServerPlayNetworking.registerGlobalReceiver(SET_BEST_SCORE_ID, SetBestScoreC2SPacket::receive);
    }

    public static void registerS2CPackets() {

    }
}
