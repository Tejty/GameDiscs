package net.tejty.gamediscs.sounds;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.tejty.gamediscs.GameDiscsMod;
import org.jetbrains.annotations.NotNull;

public class SoundRegistry {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, GameDiscsMod.MOD_ID);

    public static final DeferredHolder<SoundEvent, SoundEvent> JUMP = registerSoundEvents("jump");
    public static final DeferredHolder<SoundEvent, SoundEvent> CLICK = registerSoundEvents("click");
    public static final DeferredHolder<SoundEvent, SoundEvent> POINT = registerSoundEvents("point");
    public static final DeferredHolder<SoundEvent, SoundEvent> NEW_BEST = registerSoundEvents("new_best");
    public static final DeferredHolder<SoundEvent, SoundEvent> GAME_OVER = registerSoundEvents("game_over");
    public static final DeferredHolder<SoundEvent, SoundEvent> SELECT = registerSoundEvents("select");
    public static final DeferredHolder<SoundEvent, SoundEvent> CONFIRM = registerSoundEvents("confirm");
    public static final DeferredHolder<SoundEvent, SoundEvent> EXPLOSION = registerSoundEvents("explosion");
    public static final DeferredHolder<SoundEvent, SoundEvent> SHOOT = registerSoundEvents("shoot");
    public static final DeferredHolder<SoundEvent, SoundEvent> SWING = registerSoundEvents("swing");
    public static final DeferredHolder<SoundEvent, SoundEvent> SWITCH = registerSoundEvents("switch");

    private static @NotNull DeferredHolder<SoundEvent, SoundEvent> registerSoundEvents(String name) {
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(GameDiscsMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
