package net.tejty.gamediscs.util.creativetab;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.tejty.gamediscs.GameDiscsMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.item.ItemRegistry;

public class CreativeTabs {
    public static final ItemGroup GAME_DISCS_TAB = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(GameDiscsMod.MOD_ID, "game_discs_tab"),
            FabricItemGroup.builder().displayName(Text.translatable("creativetab.game_discs_tab"))
                    .icon(() -> new ItemStack(ItemRegistry.GAMING_CONSOLE)).entries((displayContext, entries) -> {
                        entries.add(ItemRegistry.GAMING_CONSOLE);
                        entries.add(ItemRegistry.REDSTONE_CIRCUIT);
                        entries.add(ItemRegistry.PROCESSOR);
                        entries.add(ItemRegistry.BATTERY);
                        entries.add(ItemRegistry.DISPLAY);
                        entries.add(ItemRegistry.CONTROL_PAD);
                        entries.add(ItemRegistry.GAME_DISC_FLAPPY_BIRD);
                        entries.add(ItemRegistry.GAME_DISC_SLIME);
                        entries.add(ItemRegistry.GAME_DISC_BLOCKTRIS);
                        entries.add(ItemRegistry.GAME_DISC_TNT_SWEEPER);
                        entries.add(ItemRegistry.GAME_DISC_PONG);
                        entries.add(ItemRegistry.GAME_DISC_FROGGIE);
                    }).build());


    public static void registerItemGroups() {
        GameDiscsMod.LOGGER.info("Registering Item Groups for " + GameDiscsMod.MOD_ID);
    }
}
