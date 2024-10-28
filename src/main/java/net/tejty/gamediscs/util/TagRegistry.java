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
            return TagKey.of(RegistryKeys.BLOCK, new Identifier(GameDiscsMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> GAME_DISCS =
                createTag("game_discs");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, new Identifier(GameDiscsMod.MOD_ID, name));
        }
    }
}
