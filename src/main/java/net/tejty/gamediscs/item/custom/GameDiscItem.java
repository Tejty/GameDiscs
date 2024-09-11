package net.tejty.gamediscs.item.custom;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.tejty.gamediscs.games.util.Game;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Supplier;

public class GameDiscItem extends Item {
    private final Supplier<Game> gameSupplier;
    private final Component name;

    public GameDiscItem(Properties pProperties, Supplier<Game> gameSupplier, Component name) {
        super(pProperties.stacksTo(1));
        this.gameSupplier = gameSupplier;
        this.name = name;
    }

    public Game getGame() {
        return gameSupplier.get();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(name);

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
