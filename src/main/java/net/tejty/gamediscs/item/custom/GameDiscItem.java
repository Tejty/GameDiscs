package net.tejty.gamediscs.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.tejty.gamediscs.games.util.Game;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class GameDiscItem extends Item {
    private final Component name;

    public GameDiscItem(Properties pProperties, Component name) {
        super(pProperties.stacksTo(1));
        this.name = name;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        tooltipComponents.add(name);
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
