package net.tejty.gamediscs.util.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;
import net.minecraft.client.data.Models;
import net.tejty.gamediscs.item.ItemRegistry;

public class GameDiscsModelProvider extends FabricModelProvider {
    public GameDiscsModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        // skip
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ItemRegistry.GAME_DISC_BLOCKTRIS, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GAME_DISC_SLIME, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GAME_DISC_PONG, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GAME_DISC_FROGGIE, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GAME_DISC_RABBIT, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GAME_DISC_FLAPPY_BIRD, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.GAME_DISC_TNT_SWEEPER, Models.GENERATED);

        itemModelGenerator.register(ItemRegistry.GAMING_CONSOLE, Models.GENERATED);

        itemModelGenerator.register(ItemRegistry.REDSTONE_CIRCUIT, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.PROCESSOR, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.BATTERY, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.DISPLAY, Models.GENERATED);
        itemModelGenerator.register(ItemRegistry.CONTROL_PAD, Models.GENERATED);
    }

    @Override
    public String getName() {
        return "Game Discs - Model";
    }
}
