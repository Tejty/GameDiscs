package net.tejty.gamediscs.item.custom;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.client.ClientUtils;
import net.tejty.gamediscs.util.component.DataComponentTypeRegistry;

public class GamingConsoleItem extends Item {
    public GamingConsoleItem(Settings properties) {
        super(properties.maxCount(1));
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient()) {
            ClientUtils.openConsoleScreen();
        }

        return super.use(world, user, hand);
    }

    public static void setBestScore(ItemStack stack, String game, int score, PlayerEntity player) {
        NbtCompound nbtData = stack.getOrDefault(DataComponentTypeRegistry.NBT, new NbtCompound());
        nbtData.putInt(GameDiscsMod.MOD_ID + ":" + game + ";" + player.getDisplayName().getString(), score);
        stack.set(DataComponentTypeRegistry.NBT, nbtData);

        // TODO bestScore
    }

    public static int getBestScore(ItemStack stack, String game, PlayerEntity player) {
        return stack.getOrDefault(DataComponentTypeRegistry.NBT, new NbtCompound()).getInt(GameDiscsMod.MOD_ID + ":" + game + ";" + player.getDisplayName().getString());
    }
}
