package net.tejty.gamediscs.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GameDiscItem extends Item {
    private final Component name;

    public GameDiscItem(Properties pProperties, Component name) {
        super(pProperties.stacksTo(1));
        this.name = name;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(name);

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
