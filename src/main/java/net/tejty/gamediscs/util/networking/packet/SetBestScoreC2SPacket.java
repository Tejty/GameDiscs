package net.tejty.gamediscs.util.networking.packet;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;

public class SetBestScoreC2SPacket {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler,
                               PacketByteBuf buf, PacketSender responseSender) {
        // Everything here happens ONLY on the Server!
        String game = buf.readString();
        int score = buf.readVarInt();
        ItemStack stack = player.getMainHandStack();
        if (stack.getItem() instanceof GamingConsoleItem console) {
            console.setBestScore(stack, game, score, player);
        }
    }
}
