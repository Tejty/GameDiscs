package net.tejty.gamediscs.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.ItemRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Stream;

public class DiscsModelProvider extends ModelProvider {
    public DiscsModelProvider(PackOutput output) {
        super(output, GameDiscsMod.MOD_ID);
    }

    @Override
    protected void registerModels(@NotNull BlockModelGenerators blockModels, @NotNull ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ItemRegistry.GAMING_CONSOLE.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.BATTERY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.REDSTONE_CIRCUIT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.PROCESSOR.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.CONTROL_PAD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.DISPLAY.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ItemRegistry.GAME_DISC_BLOCKTRIS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GAME_DISC_FLAPPY_BIRD.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GAME_DISC_FROGGIE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GAME_DISC_PONG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GAME_DISC_SLIME.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GAME_DISC_RABBIT.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ItemRegistry.GAME_DISC_TNT_SWEEPER.get(), ModelTemplates.FLAT_ITEM);
    }

    @Override
    protected @NotNull Stream<? extends Holder<Item>> getKnownItems() {
        return ItemRegistry.ITEMS.getEntries().stream();
    }
}
