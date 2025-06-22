package net.tejty.gamediscs.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.List;

public class GameDiscItem extends Item {
    private final Text name;

    public GameDiscItem(Item.Settings pProperties, Text name) {
        super(pProperties.maxCount(1));
        this.name = name;
    }


    public void renderTooltip(ItemStack stack, TooltipContext context, TooltipType type, List<Text> tooltips) {
        tooltips.add(name);
    }
}
