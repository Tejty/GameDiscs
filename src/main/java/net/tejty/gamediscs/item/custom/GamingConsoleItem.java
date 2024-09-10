package net.tejty.gamediscs.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tejty.gamediscs.client.gui.screens.GamingConsoleScreen;
import net.tejty.gamediscs.game.Game;

public class GamingConsoleItem extends Item {
    public GamingConsoleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            Minecraft.getInstance().setScreen(new GamingConsoleScreen(Component.translatable("gui.gamingconsole.title")));
        }

        return super.use(level, player, hand);
    }

    public void setBestScore(ItemStack stack, String game, int score, Player player) {
        if (!stack.hasTag()){
            stack.setTag(new CompoundTag());
        }
        CompoundTag nbtData = stack.getTag();
        nbtData.putInt("gamediscs:" + game + ";" + player.getDisplayName().getString(), score);
        stack.setTag(nbtData);

        // TODO bestScore
    }

    public static int getBestScore(ItemStack stack, String game, Player player) {
        if (!stack.hasTag()){
            return 0;
        }
        else {
            return stack.getTag().getInt("gamediscs:" + game + ";" + player.getDisplayName().getString());
        }
    }
}
