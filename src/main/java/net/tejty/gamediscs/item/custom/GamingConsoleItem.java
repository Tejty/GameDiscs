package net.tejty.gamediscs.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.tejty.gamediscs.client.screen.GamingConsoleScreen;
import net.tejty.gamediscs.component.BestScoreComponent;
import net.tejty.gamediscs.component.DataComponentRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class GamingConsoleItem extends Item {
    public GamingConsoleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            Minecraft.getInstance().setScreen(new GamingConsoleScreen(Component.translatable("gui.gamingconsole.title")));
        }

        return super.use(level, player, hand);
    }

    public void setBestScore(ItemStack stack, String game, int score, Player player) {
        stack.set(DataComponentRegistry.BEST_SCORE, new BestScoreComponent(game, player.getStringUUID(), score));
    }

    public static int getBestScore(ItemStack stack, String game, Player player) {
        BestScoreComponent component = stack.get(DataComponentRegistry.BEST_SCORE);
        if (component == null) return 0;
        if (component.game().equals(game) && component.stringUUID().equals(player.getStringUUID())) {
            return component.score();
        }
        return 0;
    }
}
