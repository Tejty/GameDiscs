package net.tejty.gamediscs.item.custom;

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
    private final Supplier<Game> gameSupplier;
    private final Text name;

    public GameDiscItem(Settings settings, Supplier<Game> gameSupplier, Text name) {
        super(settings.maxCount(1));
        this.gameSupplier = gameSupplier;
        this.name = name;
    }

    public Game getGame() {
        return gameSupplier.get();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(name);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
