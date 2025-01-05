package net.tejty.gamediscs.item.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.tejty.gamediscs.client.ClientUtils;
import net.tejty.gamediscs.client.screen.GamingConsoleScreen;
import net.tejty.gamediscs.component.BestScoreComponent;
import net.tejty.gamediscs.component.DataComponentRegistry;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;

@ParametersAreNonnullByDefault
public class GamingConsoleItem extends Item {
    public GamingConsoleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            if (level.isClientSide()) {
                ClientUtils.openConsoleScreen();
            }
        }

        return super.use(level, player, hand);
    }

    public static int getBestScore(ItemStack stack, String game, Player player) {
        BestScoreComponent component = stack.get(DataComponentRegistry.BEST_SCORE);
        if (component == null) return 0;
        if (component.stringUUID().equals(player.getStringUUID())) {
            return component.gameScores().getOrDefault(game, 0);
        }
        return 0;
    }

    public void setBestScore(ItemStack stack, String game, int score, Player player) {
        BestScoreComponent component = stack.getOrDefault(DataComponentRegistry.BEST_SCORE, new BestScoreComponent(Map.of(), player.getStringUUID()));
        // The component can be null if not present
        if (!component.stringUUID().equals(player.getStringUUID())) {
            return;
        }
        Map<String, Integer> scores = new HashMap<>(component.gameScores());
        scores.put(game, score);
        component = new BestScoreComponent(Map.copyOf(scores), component.stringUUID());
        stack.set(DataComponentRegistry.BEST_SCORE, component);
    }
}
