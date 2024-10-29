package net.tejty.gamediscs.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;
import org.jetbrains.annotations.NotNull;

public record SetBestScorePacket(String game, int score) implements CustomPacketPayload {
    public static final Type<SetBestScorePacket> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, "update_best_score"));

    public static final StreamCodec<FriendlyByteBuf, SetBestScorePacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, SetBestScorePacket::game,
            ByteBufCodecs.INT, SetBestScorePacket::score,
            SetBestScorePacket::new
    );

    public static void updateBestScore(SetBestScorePacket payload, @NotNull IPayloadContext context) {
        context.enqueueWork(() -> {
            ServerPlayer player = (ServerPlayer) context.player();

            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof GamingConsoleItem console) {
                console.setBestScore(stack, payload.game, payload.score, player);
            }
        });
    }

    @Override
    public @NotNull Type<SetBestScorePacket> type() {
        return TYPE;
    }
}
