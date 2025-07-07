package net.tejty.gamediscs.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@ParametersAreNonnullByDefault
public class GameDiscItem extends Item {
    private final Component name;

    public GameDiscItem(Properties pProperties, Component name) {
        super(pProperties.stacksTo(1));
        this.name = name;
    }

    public void renderTooltips(ItemStack stack, TooltipContext context,
                               @Nullable Player player, TooltipFlag flag, List<Component> tooltips) {
        tooltips.add(name);
    }
}
