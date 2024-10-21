package net.tejty.gamediscs;

import com.mojang.logging.LogUtils;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tejty.gamediscs.sounds.SoundRegistry;
import net.tejty.gamediscs.util.loot.LootModifiers;
import net.tejty.gamediscs.util.creativetab.CreativeTabs;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.util.networking.ModMessages;
import org.slf4j.Logger;

@Mod(GameDiscsMod.MOD_ID)
public class GameDiscsMod {
    public static final String MOD_ID = "gamediscs";
    private static final Logger LOGGER = LogUtils.getLogger();
    public GameDiscsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.register(modEventBus);
        CreativeTabs.register(modEventBus);
        LootModifiers.register(modEventBus);
        SoundRegistry.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register();
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents { @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {

        }
    }
}
