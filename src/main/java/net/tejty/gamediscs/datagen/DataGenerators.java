package net.tejty.gamediscs.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.tejty.gamediscs.GameDiscsMod;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = GameDiscsMod.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherServerData(GatherDataEvent.Server event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new DiscLootModifierProvider(packOutput, lookupProvider));
        generator.addProvider(true, new DiscsModelProvider(packOutput));
    }
    @SubscribeEvent
    public static void gatherClientData(GatherDataEvent.Client event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new DiscLootModifierProvider(packOutput, lookupProvider));
        generator.addProvider(true, new DiscsModelProvider(packOutput));
    }
}