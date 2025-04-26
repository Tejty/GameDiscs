package net.tejty.gamediscs.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tejty.gamediscs.games.util.Game;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GameDiscItem extends Item {
    private final Text name;

    public GameDiscItem(FabricItemSettings pProperties, Text name) {
        super(pProperties.maxCount(1));
        this.name = name;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(name);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
