package net.tejty.gamediscs.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.Map;

public record BestScoreComponent(Map<String, Integer> gameScores, String stringUUID) {
    public static final Codec<BestScoreComponent> CODEC = RecordCodecBuilder.create(inst ->
            inst.group(
                    Codec.unboundedMap(Codec.STRING, Codec.INT).fieldOf("gameScores").forGetter(BestScoreComponent::gameScores),
                    Codec.STRING.fieldOf("sUUID").forGetter(BestScoreComponent::stringUUID)
            ).apply(inst, BestScoreComponent::new));

    public static final StreamCodec<ByteBuf, BestScoreComponent> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.map(HashMap::new, ByteBufCodecs.STRING_UTF8, ByteBufCodecs.INT), BestScoreComponent::gameScores,
            ByteBufCodecs.STRING_UTF8, BestScoreComponent::stringUUID,
            BestScoreComponent::new
    );
}
