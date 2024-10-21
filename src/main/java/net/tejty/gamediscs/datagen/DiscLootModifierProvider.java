package net.tejty.gamediscs.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.util.loot.ItemModifier;
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

        // List of game discs with their corresponding item registrations
        Map<String, Object> gameDiscs = new HashMap<>();
        gameDiscs.put("flappy_bird", ItemRegistry.GAME_DISC_FLAPPY_BIRD.get());
        gameDiscs.put("slime", ItemRegistry.GAME_DISC_SLIME.get());
        gameDiscs.put("blocktris", ItemRegistry.GAME_DISC_BLOCKTRIS.get());
        gameDiscs.put("tnt_sweeper", ItemRegistry.GAME_DISC_TNT_SWEEPER.get());
        gameDiscs.put("pong", ItemRegistry.GAME_DISC_PONG.get());
        gameDiscs.put("froggie", ItemRegistry.GAME_DISC_FROGGIE.get());

        // Loop over each loot table and game disc, generating the necessary modifiers
        for (Map.Entry<String, Float> lootTableEntry : lootTables.entrySet()) {
            String lootTable = lootTableEntry.getKey();
            float chance = lootTableEntry.getValue();

            for (Map.Entry<String, Object> gameDiscEntry : gameDiscs.entrySet()) {
                String gameName = gameDiscEntry.getKey();
                Object gameDisc = gameDiscEntry.getValue();

                add(gameName + "_disc_from_" + lootTable, new ItemModifier(new LootItemCondition[]{
                        new LootTableIdCondition.Builder(new ResourceLocation(lootTable)).build(),
                        LootItemRandomChanceCondition.randomChance(chance).build()
                }, (Item) gameDisc));
            }
        }
    }

    private static @NotNull Map<String, Float> getLootTableWithChance() {
        Map<String, Float> lootTables = new HashMap<>();
        lootTables.put("chests/simple_dungeon", 0.2F);
        lootTables.put("chests/stronghold_corridor", 0.2F);
        lootTables.put("chests/stronghold_crossing", 0.2F);
        lootTables.put("chests/stronghold_library", 0.2F);
        lootTables.put("chests/end_city_treasure", 0.2F);
        lootTables.put("chests/woodland_mansion", 0.2F);
        lootTables.put("chests/buried_treasure", 0.2F);
        lootTables.put("chests/ruined_portal", 0.2F);
        lootTables.put("chests/ancient_city", 0.2F);
        lootTables.put("chests/ancient_city_ice_box", 0.2F);
        lootTables.put("chests/abandoned_mineshaft", 0.2F);
        lootTables.put("chests/jungle_temple", 0.2F);
        lootTables.put("chests/desert_pyramid", 0.2F);
        lootTables.put("chests/bastion_bridge", 0.2F);
        lootTables.put("chests/bastion_hoglin_stable", 0.2F);
        lootTables.put("chests/bastion_other", 0.2F);
        lootTables.put("chests/bastion_treasure", 0.2F);
        return lootTables;
    }
}
