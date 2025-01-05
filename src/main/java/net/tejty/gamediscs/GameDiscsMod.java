package net.tejty.gamediscs;

import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.tejty.gamediscs.component.DataComponentRegistry;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.sounds.SoundRegistry;
import net.tejty.gamediscs.util.creativetab.CreativeTabs;
import net.tejty.gamediscs.util.loot.LootModifiers;
import org.slf4j.Logger;

@Mod(GameDiscsMod.MOD_ID)
public class GameDiscsMod {
    public static final String MOD_ID = "gamediscs";
    public static final Logger LOGGER = LogUtils.getLogger();
    public GameDiscsMod(IEventBus eventBus) {
        ItemRegistry.register(eventBus);
        CreativeTabs.register(eventBus);
        LootModifiers.register(eventBus);
        SoundRegistry.register(eventBus);
        DataComponentRegistry.register(eventBus);
    }
}
