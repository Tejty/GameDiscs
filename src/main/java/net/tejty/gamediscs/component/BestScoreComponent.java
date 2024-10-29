package net.tejty.gamediscs.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public record BestScoreComponent(String game, String stringUUID, int score) {
    public static final Codec<BestScoreComponent> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.STRING.fieldOf("game").forGetter(BestScoreComponent::game),
                    Codec.STRING.fieldOf("sUUID").forGetter(BestScoreComponent::stringUUID),
                    Codec.INT.fieldOf("score").forGetter(BestScoreComponent::score)
            ).apply(inst, BestScoreComponent::new));

    public static final StreamCodec<ByteBuf, BestScoreComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8, BestScoreComponent::game,
            ByteBufCodecs.STRING_UTF8, BestScoreComponent::stringUUID,
            ByteBufCodecs.INT, BestScoreComponent::score,
            BestScoreComponent::new
    );
}
