package net.tejty.gamediscs.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.util.loot.ItemModifier;
public class DiscLootModifierProvider extends GlobalLootModifierProvider{
    public DiscLootModifierProvider(PackOutput output) {
        super(output, GameDiscsMod.MODID);
    }

    /**
     * The Generation chance is depending on randomChance.
     * To chance the value set something between 0.05F - 0.0F "0.05F is 100% and 0.0F 0%"
     */

    @Override
    protected void start() {

        /*
         * Simple Dungeon
         */
        add("flappy_bird_disc_from_simple_dungeon", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_simple_dungeon", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Stronghold Corridor
         */
        add("flappy_bird_disc_from_stronghold_corridor", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_corridor")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_stronghold_corridor", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_corridor")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Stronghold Crossing
         */
        add("flappy_bird_disc_from_stronghold_crossing", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_crossing")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_stronghold_crossing", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_crossing")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Stronghold Library
         */
        add("flappy_bird_disc_from_stronghold_library", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_library")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_stronghold_library", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/stronghold_library")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * End City Treasure
         */
        add("flappy_bird_disc_from_end_city_treasure", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/end_city_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_end_city_treasure", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/end_city_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Woodland Mansion
         */
        add("flappy_bird_disc_from_woodland_mansion", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_woodland_mansion", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/woodland_mansion")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
          Buried Treasure
         */
        add("flappy_bird_disc_from_buried_treasure", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/buried_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_buried_treasure", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/buried_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Ruined Portal
         */
        add("flappy_bird_disc_from_ruined_portal", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ruined_portal")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_ruined_portal", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ruined_portal")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Ancient City
         */
        add("flappy_bird_disc_from_ancient_city", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_ancient_city", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ancient_city")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Ancient City Ice Box
         */
        add("flappy_bird_disc_from_ancient_city_ice_box", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ancient_city_ice_box")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_ancient_city_ice_box", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/ancient_city_ice_box")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Abandoned Mineshaft
         */
        add("flappy_bird_disc_from_abandoned_mineshaft", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/abandoned_mineshaft")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_abandoned_mineshaft", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/abandoned_mineshaft")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Jungle Temple
         */
        add("flappy_bird_disc_from_jungle_temple", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_jungle_temple", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/jungle_temple")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Desert Pyramid
         */
        add("flappy_bird_disc_from_desert_pyramid", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/desert_pyramid")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_desert_pyramid", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/desert_pyramid")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Bastion Bridge
         */
        add("flappy_bird_disc_from_bastion_bridge", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_bridge")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_bastion_bridge", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_bridge")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Bastion Hoglin Stable
         */
        add("flappy_bird_disc_from_bastion_hoglin_stable", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_hoglin_stable")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_bastion_hoglin_stable", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_hoglin_stable")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Bastion Other
         */
        add("flappy_bird_disc_from_bastion_other", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_other")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_bastion_other", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_other")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));


        /*
         * Bastion Treasure
         */
        add("flappy_bird_disc_from_bastion_treasure", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_bastion_treasure", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/bastion_treasure")).build(),
                LootItemRandomChanceCondition.randomChance(0.05F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));
    }
}
