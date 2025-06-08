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
                createTag(GameDiscsMod.MOD_ID, "game_discs");
        public static final TagKey<Item> GLASS_PANES =
                createTag("c", "glass_panes");

        private static TagKey<Item> createTag(String namespace, String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(namespace, name));
        }
    }
}
