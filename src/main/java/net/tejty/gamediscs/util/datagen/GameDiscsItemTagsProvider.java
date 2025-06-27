package net.tejty.gamediscs.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.util.TagRegistry;

import java.util.concurrent.CompletableFuture;

public class GameDiscsItemTagsProvider extends FabricTagProvider.ItemTagProvider {
    public GameDiscsItemTagsProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        valueLookupBuilder(TagRegistry.Items.GAME_DISCS)
                .add(ItemRegistry.GAME_DISC_BLOCKTRIS)
                .add(ItemRegistry.GAME_DISC_FLAPPY_BIRD)
                .add(ItemRegistry.GAME_DISC_FROGGIE)
                .add(ItemRegistry.GAME_DISC_PONG)
                .add(ItemRegistry.GAME_DISC_RABBIT)
                .add(ItemRegistry.GAME_DISC_SLIME)
                .add(ItemRegistry.GAME_DISC_TNT_SWEEPER);

        valueLookupBuilder(TagRegistry.Items.GLASS_PANES)
                .add(Items.GLASS_PANE)
                .add(Items.WHITE_STAINED_GLASS_PANE)
                .add(Items.LIGHT_GRAY_STAINED_GLASS_PANE)
                .add(Items.GRAY_STAINED_GLASS_PANE)
                .add(Items.BLACK_STAINED_GLASS_PANE)
                .add(Items.BROWN_STAINED_GLASS_PANE)
                .add(Items.RED_STAINED_GLASS_PANE)
                .add(Items.ORANGE_STAINED_GLASS_PANE)
                .add(Items.YELLOW_STAINED_GLASS_PANE)
                .add(Items.LIME_STAINED_GLASS_PANE)
                .add(Items.GREEN_STAINED_GLASS_PANE)
                .add(Items.CYAN_STAINED_GLASS_PANE)
                .add(Items.LIGHT_BLUE_STAINED_GLASS_PANE)
                .add(Items.BLUE_STAINED_GLASS_PANE)
                .add(Items.PURPLE_STAINED_GLASS_PANE)
                .add(Items.MAGENTA_STAINED_GLASS_PANE)
                .add(Items.PINK_STAINED_GLASS_PANE);

        valueLookupBuilder(TagRegistry.Items.BEE_DROPS_GAME_DISC)
                .add(ItemRegistry.GAME_DISC_FLAPPY_BIRD);
        valueLookupBuilder(TagRegistry.Items.FROG_DROPS_GAME_DISC)
                .add(ItemRegistry.GAME_DISC_FROGGIE);
        valueLookupBuilder(TagRegistry.Items.RABBIT_DROPS_GAME_DISC)
                .add(ItemRegistry.GAME_DISC_RABBIT);
        valueLookupBuilder(TagRegistry.Items.SLIME_DROPS_GAME_DISC)
                .add(ItemRegistry.GAME_DISC_SLIME);
    }

    @Override
    public String getName() {
        return "GameDiscs - Item Tags";
    }
}
