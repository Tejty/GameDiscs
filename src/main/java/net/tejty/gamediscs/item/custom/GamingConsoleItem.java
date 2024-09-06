package net.tejty.gamediscs.item.custom;

import net.minecraft.client.Minecraft;
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
}
