package net.tejty.gamediscs;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.tejty.gamediscs.util.loot.LootModifiers;
import net.tejty.gamediscs.util.creativetab.CreativeTabs;
import net.tejty.gamediscs.item.ItemRegistry;
import net.tejty.gamediscs.util.networking.ModMessages;
import org.slf4j.Logger;
@Mod(GameDiscsMod.MODID) //The value here should match an entry in the META-INF/mods.toml file
public class GameDiscsMod {
    public static final String MODID = "gamediscs"; //Define mod id in a common place for everything to reference
    private static final Logger LOGGER = LogUtils.getLogger(); //Directly reference a slf4j logger
    public GameDiscsMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.register(modEventBus); //Registering items
        CreativeTabs.register(modEventBus); //Register creative tab
        LootModifiers.register(modEventBus); //Register loot modifier class

        modEventBus.addListener(this::commonSetup); //Register the commonSetup method for modloading
        MinecraftForge.EVENT_BUS.register(this); //Register ourselves for server and other game events we are interested in
    }
    private void commonSetup(final FMLCommonSetupEvent event) {
        ModMessages.register(); //Some common setup code
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting"); //Do something when the server starts
    }
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents { @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP"); //Some client setup code
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
