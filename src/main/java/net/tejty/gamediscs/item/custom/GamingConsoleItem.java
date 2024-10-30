package net.tejty.gamediscs.item.custom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.client.screen.GamingConsoleScreen;

public class GamingConsoleItem extends Item {
    public GamingConsoleItem(Settings properties) {
        super(properties.maxCount(1));
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            MinecraftClient.getInstance().setScreen(new GamingConsoleScreen(Text.translatable("gui.gamingconsole.title")));
        }

        return super.use(world, user, hand);
    }

    public void setBestScore(ItemStack stack, String game, int score, PlayerEntity player) {
        if (!stack.hasNbt()){
            stack.setNbt(new NbtCompound());
        }
        NbtCompound nbtData = stack.getNbt();
        nbtData.putInt(GameDiscsMod.MOD_ID + ":" + game + ";" + player.getDisplayName().getString(), score);
        stack.setNbt(nbtData);

        // TODO bestScore
    }

    public static int getBestScore(ItemStack stack, String game, PlayerEntity player) {
        if (!stack.hasNbt()){
            return 0;
        }
        else {
            return stack.getNbt().getInt(GameDiscsMod.MOD_ID + ":" + game + ";" + player.getDisplayName().getString());
        }
    }
}
