package net.tejty.gamediscs.util;

import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DiscLootTablesModifiers {
    public static void modifyLootTables() {
        // Define all loot tables and their drop chances using a HashMap
        Map<String, Float> lootTables = getLootTableWithChance();

        // List of mobs that drop a disc when killed by a skeleton with their corresponding item registrations
        Map<String, TagKey<Item>> mobDiscs = new HashMap<>();
        mobDiscs.put("bee", TagRegistry.Items.BEE_DROPS_GAME_DISC);
        mobDiscs.put("slime", TagRegistry.Items.SLIME_DROPS_GAME_DISC);
        mobDiscs.put("frog", TagRegistry.Items.FROG_DROPS_GAME_DISC);
        mobDiscs.put("rabbit", TagRegistry.Items.RABBIT_DROPS_GAME_DISC);

        LootTableEvents.MODIFY.register((key, tableBuilder, sources, registries) -> {
            // Loop over each loot table and game disc, generating the necessary modifiers
            for (Map.Entry<String, Float> lootTableEntry : lootTables.entrySet()) {
                String lootTable = lootTableEntry.getKey();
                float chance = lootTableEntry.getValue();

                if(Identifier.ofVanilla(lootTable).equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .rolls(ConstantLootNumberProvider.create(1.0f))
                            .with(TagEntry.expandBuilder(TagRegistry.Items.GAME_DISCS))
                            .conditionally(RandomChanceLootCondition.builder(chance))
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                    break;
                }
            }

            for (Map.Entry<String, TagKey<Item>> mobDiscEntry : mobDiscs.entrySet()) {
                String mobName = mobDiscEntry.getKey();
                TagKey<Item> gameDisc = mobDiscEntry.getValue();

                if(Identifier.of("entities/" + mobName).equals(key.getValue())) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .with(TagEntry.builder(gameDisc))
                            .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.ATTACKER,
                                    EntityPredicate.Builder.create()
                                            .type(registries.getOrThrow(RegistryKeys.ENTITY_TYPE),
                                                    EntityTypeTags.SKELETONS)))
                            .rolls(ConstantLootNumberProvider.create(1.0f))
                            .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1.0f)).build());

                    tableBuilder.pool(poolBuilder.build());
                }
            }
        });
    }

    private static final float chance = 0.3f;

    private static @NotNull Map<String, Float> getLootTableWithChance() {
        Map<String, Float> lootTables = new HashMap<>();
        lootTables.put("chests/simple_dungeon", chance);
        lootTables.put("chests/stronghold_corridor", chance);
        lootTables.put("chests/stronghold_crossing", chance);
        lootTables.put("chests/stronghold_library", chance);
        lootTables.put("chests/end_city_treasure", chance);
        lootTables.put("chests/woodland_mansion", chance);
        lootTables.put("chests/buried_treasure", chance);
        lootTables.put("chests/ruined_portal", chance);
        lootTables.put("chests/ancient_city", chance);
        lootTables.put("chests/ancient_city_ice_box", chance);
        lootTables.put("chests/abandoned_mineshaft", chance);
        lootTables.put("chests/jungle_temple", chance);
        lootTables.put("chests/desert_pyramid", chance);
        lootTables.put("chests/bastion_bridge", chance);
        lootTables.put("chests/bastion_hoglin_stable", chance);
        lootTables.put("chests/bastion_other", chance);
        lootTables.put("chests/bastion_treasure", chance);
        return lootTables;
    }
}
