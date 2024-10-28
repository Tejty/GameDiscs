package net.tejty.gamediscs;

import net.fabricmc.api.ModInitializer;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.util.DiscLootTablesModifiers;
import net.tejty.gamediscs.sounds.SoundRegistry;
import net.tejty.gamediscs.util.creativetab.CreativeTabs;
import net.tejty.gamediscs.util.networking.ModMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GameDiscsMod implements ModInitializer {
    public static final String MOD_ID = "gamediscs";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        ItemRegistry.registerModItems();
        CreativeTabs.registerItemGroups();
        DiscLootTablesModifiers.modifyLootTables();
        SoundRegistry.registerSounds();
        ModMessages.registerC2SPackets();
    }
}
