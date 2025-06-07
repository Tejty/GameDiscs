package net.tejty.gamediscs.util.networking.packet;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;

public record SetBestScoreC2SPayload(String game, int score) implements CustomPayload {
    public static final Id<SetBestScoreC2SPayload> ID = new Id<>(Identifier.of(GameDiscsMod.MOD_ID, "set_best_score"));
    public static final PacketCodec<PacketByteBuf, SetBestScoreC2SPayload> CODEC =
            new PacketCodec<>() {
                @Override
                public SetBestScoreC2SPayload decode(PacketByteBuf buf) {
                    return new SetBestScoreC2SPayload(buf.readString(), buf.readInt());
                }

                @Override
                public void encode(PacketByteBuf buf, SetBestScoreC2SPayload value) {
                    buf.writeString(value.game).writeInt(value.score);
                }
            };
    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
