package net.tejty.gamediscs.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Item;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.EntityPropertiesLootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.entry.TagEntry;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.item.ItemRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DiscLootTablesModifiers {
    public static void modifyLootTables() {
        // Define all loot tables and their drop chances using a HashMap
        Map<String, Float> lootTables = getLootTableWithChance();

        // List of mobs that drop a disc when killed by a skeleton with their corresponding item registrations
        Map<String, Object> mobDiscs = new HashMap<>();
        mobDiscs.put("bee", ItemRegistry.GAME_DISC_FLAPPY_BIRD);
        mobDiscs.put("slime", ItemRegistry.GAME_DISC_SLIME);
        mobDiscs.put("frog", ItemRegistry.GAME_DISC_FROGGIE);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            // Loop over each loot table and game disc, generating the necessary modifiers
            for (Map.Entry<String, Float> lootTableEntry : lootTables.entrySet()) {
                String lootTable = lootTableEntry.getKey();
                float chance = lootTableEntry.getValue();

                if(Identifier.of(lootTable).equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .with(TagEntry.expandBuilder(TagRegistry.Items.GAME_DISCS))
                            .conditionally(RandomChanceLootCondition.builder(chance));

                    tableBuilder.pool(poolBuilder.build());
                }
            }

            for (Map.Entry<String, Object> mobDiscEntry : mobDiscs.entrySet()) {
                String mobName = mobDiscEntry.getKey();
                Object gameDisc = mobDiscEntry.getValue();

                if(Identifier.of("entities/" + mobName).equals(id)) {
                    LootPool.Builder poolBuilder = LootPool.builder()
                            .with(ItemEntry.builder((Item) gameDisc))
                            .conditionally(EntityPropertiesLootCondition.builder(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.create().type(EntityTypeTags.SKELETONS)));

                    tableBuilder.pool(poolBuilder.build());
                }
            }
        });
    }

    private static @NotNull Map<String, Float> getLootTableWithChance() {
        Map<String, Float> lootTables = new HashMap<>();
        lootTables.put("chests/simple_dungeon", 0.1f);
        lootTables.put("chests/stronghold_corridor", 0.1f);
        lootTables.put("chests/stronghold_crossing", 0.1f);
        lootTables.put("chests/stronghold_library", 0.1f);
        lootTables.put("chests/end_city_treasure", 0.1f);
        lootTables.put("chests/woodland_mansion", 0.1f);
        lootTables.put("chests/buried_treasure", 0.1f);
        lootTables.put("chests/ruined_portal", 0.1f);
        lootTables.put("chests/ancient_city", 0.1f);
        lootTables.put("chests/ancient_city_ice_box", 0.1f);
        lootTables.put("chests/abandoned_mineshaft", 0.1f);
        lootTables.put("chests/jungle_temple", 0.1f);
        lootTables.put("chests/desert_pyramid", 0.1f);
        lootTables.put("chests/bastion_bridge", 0.1f);
        lootTables.put("chests/bastion_hoglin_stable", 0.1f);
        lootTables.put("chests/bastion_other", 0.1f);
        lootTables.put("chests/bastion_treasure", 0.1f);
        return lootTables;
    }
}
