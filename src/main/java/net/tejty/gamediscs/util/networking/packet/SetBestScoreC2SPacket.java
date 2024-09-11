package net.tejty.gamediscs.util.networking.packet;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;
import net.tejty.gamediscs.item.custom.GamingConsoleItem;

import java.util.function.Supplier;
public class SetBestScoreC2SPacket {
    private final String game;
    private final int score;
    public SetBestScoreC2SPacket(String game, int score) {
        this.game = game;
        this.score = score;
    }
    public SetBestScoreC2SPacket(FriendlyByteBuf buf) {
        this.game = buf.readUtf();
        this.score = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUtf(game);
        buf.writeInt(score);
    }
    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            // This is on server
            ServerPlayer player = context.getSender();
            assert player != null;
            ItemStack stack = player.getMainHandItem();
            if (stack.getItem() instanceof GamingConsoleItem console) {
                console.setBestScore(stack, game, score, player);
            }
        });
    }
}
