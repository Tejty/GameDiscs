package net.tejty.gamediscs.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.tejty.gamediscs.GameDiscsMod;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, GameDiscsMod.MODID);

    public static final RegistryObject<SoundEvent> JUMP = registerSoundEvents("jump");
    public static final RegistryObject<SoundEvent> CLICK = registerSoundEvents("click");
    public static final RegistryObject<SoundEvent> POINT = registerSoundEvents("point");
    public static final RegistryObject<SoundEvent> NEW_BEST = registerSoundEvents("new_best");
    public static final RegistryObject<SoundEvent> GAME_OVER = registerSoundEvents("game_over");
    public static final RegistryObject<SoundEvent> SELECT = registerSoundEvents("select");
    public static final RegistryObject<SoundEvent> CONFIRM = registerSoundEvents("confirm");
    public static final RegistryObject<SoundEvent> EXPLOSION = registerSoundEvents("explosion");
    public static final RegistryObject<SoundEvent> SHOOT = registerSoundEvents("shoot");
    public static final RegistryObject<SoundEvent> SWING = registerSoundEvents("swing");
    public static final RegistryObject<SoundEvent> SWITCH = registerSoundEvents("switch");

    private static RegistryObject<SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(GameDiscsMod.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
