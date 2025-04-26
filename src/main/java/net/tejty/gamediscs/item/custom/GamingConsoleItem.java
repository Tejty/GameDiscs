package net.tejty.gamediscs.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;

import java.util.function.Supplier;

public class GamingConsoleItem extends Item {
    public GamingConsoleItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> () -> {
                // Using Supplier to lazy load GamingConsoleScreen
                getScreenSetter().get().run(Component.translatable("gui.gamingconsole.title"));
            });
        }
        return super.use(level, player, hand);
    }

    private static Supplier<ScreenSetter> screenSetter;

    private static Supplier<ScreenSetter> getScreenSetter() {
        if (screenSetter == null) {
            screenSetter = () -> (title) -> net.minecraft.client.Minecraft.getInstance().setScreen(
                    new net.tejty.gamediscs.client.screen.GamingConsoleScreen(title)
            );
        }
        return screenSetter;
    }

    @FunctionalInterface
    private interface ScreenSetter {
        void run(Component title);
    }

    public void setBestScore(ItemStack stack, String game, int score, Player player) {
        if (!stack.hasTag()){
            stack.setTag(new CompoundTag());
        }
        CompoundTag nbtData = stack.getTag();
        if (nbtData == null) return;
        nbtData.putInt("gamediscs:" + game + ";" + player.getStringUUID(), score);
        stack.setTag(nbtData);

        // TODO bestScore
    }

    public static int getBestScore(ItemStack stack, String game, Player player) {
        if (!stack.hasTag()){
            return 0;
        }
        else {
            CompoundTag nbtData = stack.getTag();
            if (nbtData != null) {
                return nbtData.getInt("gamediscs:" + game + ";" + player.getStringUUID());
            } else {
                return 0; // default value
            }
        }
    }
}