package net.tejty.gamediscs.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.tejty.gamediscs.GameDiscsMod;

public class TagRegistry {
    public static class Blocks {
//        public static final TagKey<Block> METAL_DETECTOR_VALUABLES = tag("metal_detector_valuables");
//        public static final TagKey<Block> NEEDS_SAPPHIRE_TOOL = tag("needs_sapphire_tool");
//
//
//        private static TagKey<Block> tag(String name) {
//            return BlockTags.create(new ResourceLocation(TutorialMod.MOD_ID, name));
//        }
    }

    public static class Items {

        public static final TagKey<Item> GAME_DISCS =
                tag("game_discs");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(GameDiscsMod.MOD_ID, name));
        }
    }
}
