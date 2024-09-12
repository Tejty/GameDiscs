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
     * To chance the value set something between 1.0F - 0.0F "1.0F is 100% and 0.0F 0%"
     */

    @Override
    protected void start() {
        add("flappy_bird_disc_from_simple_dungeon", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build() },
                ItemRegistry.GAME_DISC_FLAPPY_BIRD.get()));
        add("slime_disc_from_simple_dungeon", new ItemModifier(new LootItemCondition[] {
                new LootTableIdCondition.Builder(new ResourceLocation("chests/simple_dungeon")).build(),
                LootItemRandomChanceCondition.randomChance(1.0F).build() },
                ItemRegistry.GAME_DISC_SLIME.get()));
    }
}
