package net.tejty.gamediscs.util.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipe.RecipeExporter;
import net.minecraft.data.recipe.RecipeGenerator;
import net.minecraft.data.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.util.TagRegistry;

import java.util.concurrent.CompletableFuture;

public class GameDiscsRecipeProvider extends FabricRecipeProvider {
    public GameDiscsRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeGenerator getRecipeGenerator(RegistryWrapper.WrapperLookup wrapperLookup, RecipeExporter recipeExporter) {
        return new RecipeGenerator(wrapperLookup, recipeExporter) {
            @Override
            public void generate() {
                save(battery(), Items.REDSTONE);
                save(controlPad(), Items.QUARTZ);
                save(display(), Items.REDSTONE);
                save(processor(), Items.COMPARATOR);
                save(redstone_circuit(), Items.REDSTONE);
                save(gaming_console(), Items.IRON_INGOT);
            }
            private ShapedRecipeJsonBuilder battery() {
                return createShaped(RecipeCategory.MISC, ItemRegistry.BATTERY)
                        .pattern(" Q ")
                        .pattern(" R ")
                        .pattern(" C ")
                        .input('R', Items.REDSTONE)
                        .input('Q', Items.QUARTZ)
                        .input('C', Items.COPPER_INGOT)
                        .group("gaming");
            }
            private ShapedRecipeJsonBuilder controlPad() {
                return createShaped(RecipeCategory.MISC, ItemRegistry.CONTROL_PAD)
                        .pattern("BBB")
                        .pattern("BQB")
                        .pattern("BBB")
                        .input('Q', Items.QUARTZ)
                        .input('B', ItemTags.BUTTONS)
                        .group("gaming");
            }
            private ShapedRecipeJsonBuilder display() {
                return createShaped(RecipeCategory.MISC, ItemRegistry.DISPLAY)
                        .pattern("RGR")
                        .pattern("GLG")
                        .pattern("RGR")
                        .input('R', Items.REDSTONE)
                        .input('L', TagRegistry.Items.GLASS_PANES)
                        .input('G', Items.GLOWSTONE_DUST)
                        .group("gaming");
            }
            private ShapedRecipeJsonBuilder processor() {
                return createShaped(RecipeCategory.MISC, ItemRegistry.PROCESSOR)
                        .pattern("CCC")
                        .pattern("CCC")
                        .pattern("CCC")
                        .input('C', Items.COMPARATOR)
                        .group("gaming");
            }
            private ShapedRecipeJsonBuilder redstone_circuit() {
                return createShaped(RecipeCategory.MISC, ItemRegistry.REDSTONE_CIRCUIT)
                        .pattern("TRT")
                        .pattern("RCR")
                        .pattern("TRT")
                        .input('R', Items.REDSTONE)
                        .input('C', Items.COMPARATOR)
                        .input('T', Items.REDSTONE_TORCH)
                        .group("gaming");
            }
            private ShapedRecipeJsonBuilder gaming_console() {
                return createShaped(RecipeCategory.MISC, ItemRegistry.GAMING_CONSOLE)
                        .pattern("IDI")
                        .pattern("RPR")
                        .pattern("BCB")
                        .input('I', Items.IRON_INGOT)
                        .input('D', ItemRegistry.DISPLAY)
                        .input('P', ItemRegistry.PROCESSOR)
                        .input('R', ItemRegistry.REDSTONE_CIRCUIT)
                        .input('B', ItemRegistry.BATTERY)
                        .input('C', ItemRegistry.CONTROL_PAD)
                        .group("gaming");
            }
            private void save(ShapedRecipeJsonBuilder builder, ItemConvertible has) {
                builder.criterion(hasItem(has), conditionsFromItem(has))
                        .offerTo(exporter);
            }
        };
    }

    @Override
    public String getName() {
        return "GameDiscs - Recipe";
    }
}
