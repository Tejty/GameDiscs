package net.tejty.gamediscs.datagen;

import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.util.TagRegistry;
import net.tejty.gamediscs.util.loot.ItemModifier;
import net.tejty.gamediscs.util.loot.ItemTagModifier;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class DiscLootModifierProvider extends GlobalLootModifierProvider {
    public DiscLootModifierProvider(PackOutput output) {
        super(output, GameDiscsMod.MOD_ID);
    }

    @Override
    protected void start() {
        // Define all loot tables and their drop chances using a HashMap
        Map<String, Float> lootTables = getLootTableWithChance();

        // List of mobs that drop a disc when killed by a skeleton with their corresponding item registrations
        Map<String, Object> mobDiscs = new HashMap<>();
        mobDiscs.put("bee", ItemRegistry.GAME_DISC_FLAPPY_BIRD.get());
        mobDiscs.put("slime", ItemRegistry.GAME_DISC_SLIME.get());
        mobDiscs.put("frog", ItemRegistry.GAME_DISC_FROGGIE.get());
        mobDiscs.put("rabbit", ItemRegistry.GAME_DISC_RABBIT.get());

        // Loop over each loot table, generating the necessary modifiers
        for (Map.Entry<String, Float> lootTableEntry : lootTables.entrySet()) {
            String lootTable = lootTableEntry.getKey();
            float chance = lootTableEntry.getValue();

            add("disc_from_" + lootTable, new ItemTagModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation(lootTable)).build(),
                    LootItemRandomChanceCondition.randomChance(chance).build()
            }, TagRegistry.Items.GAME_DISCS));
        }

        for (Map.Entry<String, Object> mobDiscEntry : mobDiscs.entrySet()) {
            String mobName = mobDiscEntry.getKey();
            Object gameDisc = mobDiscEntry.getValue();

            add("mob_drops/" + mobName + "_drops_game_disc", new ItemModifier(new LootItemCondition[]{
                    new LootTableIdCondition.Builder(new ResourceLocation("entities/" + mobName)).build(),
                    LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER, EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS).build()).build()
            }, (Item) gameDisc));
        }
    }

    private static float chance = 0.3f;

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
