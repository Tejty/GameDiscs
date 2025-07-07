package net.tejty.gamediscs.item;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.custom.GameDiscItem;

@EventBusSubscriber(modid = GameDiscsMod.MOD_ID)
public class ItemTooltipHelper {
    @SubscribeEvent
    public static void modifyItemTooltips(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        // Check if the item is the game disc item
        if (stack.getItem() instanceof GameDiscItem discItem) {
            discItem.renderTooltips(stack, event.getContext(), event.getEntity(),
                    event.getFlags(), event.getToolTip());
        }
    }
}
