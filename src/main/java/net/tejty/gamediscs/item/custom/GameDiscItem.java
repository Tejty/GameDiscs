package net.tejty.gamediscs.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tejty.gamediscs.games.util.Game;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GameDiscItem extends Item {
    private final Text name;

    public GameDiscItem(Item.Settings pProperties, Text name) {
        super(pProperties.maxCount(1));
        this.name = name;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(name);
        super.appendTooltip(stack, context, tooltip, type);
    }
}
