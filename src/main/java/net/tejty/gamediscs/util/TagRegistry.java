package net.tejty.gamediscs.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.tejty.gamediscs.GameDiscsMod;

public class TagRegistry {
    public static class Blocks {

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> GAME_DISCS = createTag("game_discs");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, name));
        }
    }
}
