package net.tejty.gamediscs.item.custom;

import net.minecraft.component.type.TooltipDisplayComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;

import java.util.function.Consumer;

public class GameDiscItem extends Item {
    private final Text name;

    public GameDiscItem(Item.Settings pProperties, Text name) {
        super(pProperties.maxCount(1));
        this.name = name;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, TooltipDisplayComponent displayComponent, Consumer<Text> textConsumer, TooltipType type) {
        textConsumer.accept(name);
        super.appendTooltip(stack, context, displayComponent, textConsumer, type);
    }
}
