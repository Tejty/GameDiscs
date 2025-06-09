package net.tejty.gamediscs.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;

public class TagRegistry {
    public static class Blocks {

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(GameDiscsMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> GAME_DISCS =
                createTag("game_discs");
        public static final TagKey<Item> GLASS_PANES =
                createTag("glass_panes");
        public static final TagKey<Item> BEE_DROPS_GAME_DISC =
                createTag("bee_drops_game_disc");
        public static final TagKey<Item> FROG_DROPS_GAME_DISC =
                createTag("frog_drops_game_disc");
        public static final TagKey<Item> RABBIT_DROPS_GAME_DISC =
                createTag("rabbit_drops_game_disc");
        public static final TagKey<Item> SLIME_DROPS_GAME_DISC =
                createTag("slime_drops_game_disc");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(GameDiscsMod.MOD_ID, name));
        }
    }
}
