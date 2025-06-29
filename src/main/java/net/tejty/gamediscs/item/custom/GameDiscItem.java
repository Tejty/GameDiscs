package net.tejty.gamediscs.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Consumer;

@ParametersAreNonnullByDefault
public class GameDiscItem extends Item {
    private final Component name;

    public GameDiscItem(Properties pProperties, Component name) {
        super(pProperties.stacksTo(1));
        this.name = name;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay tooltipDisplay, Consumer<Component> tooltipAdder, TooltipFlag flag) {
        tooltipAdder.accept(name);
        super.appendHoverText(stack, context, tooltipDisplay, tooltipAdder, flag);
    }
}
