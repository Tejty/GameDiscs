package net.tejty.gamediscs.util.networking.packet;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;

public class SetBestScoreC2SPacket {
    public static void receive(SetBestScoreC2SPayload payload, ServerPlayNetworking.Context context) {
        // Everything here happens ONLY on the Server!
        ServerPlayerEntity player = context.player();
        String game = payload.game();
        int score = payload.score();
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof GamingConsoleItem console) {
            console.setBestScore(stack, game, score, player);
        }
    }
}
