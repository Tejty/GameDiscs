package net.tejty.gamediscs.sounds;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;

public class SoundRegistry {
    public static final SoundEvent JUMP = registerSoundEvent("jump");
    public static final SoundEvent CLICK = registerSoundEvent("click");
    public static final SoundEvent POINT = registerSoundEvent("point");
    public static final SoundEvent NEW_BEST = registerSoundEvent("new_best");
    public static final SoundEvent GAME_OVER = registerSoundEvent("game_over");
    public static final SoundEvent SELECT = registerSoundEvent("select");
    public static final SoundEvent CONFIRM = registerSoundEvent("confirm");
    public static final SoundEvent EXPLOSION = registerSoundEvent("explosion");
    public static final SoundEvent SHOOT = registerSoundEvent("shoot");
    public static final SoundEvent SWING = registerSoundEvent("swing");
    public static final SoundEvent SWITCH = registerSoundEvent("switch");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.of(GameDiscsMod.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerSounds() {
        GameDiscsMod.LOGGER.info("Registering Sounds for " + GameDiscsMod.MOD_ID);
    }
}
