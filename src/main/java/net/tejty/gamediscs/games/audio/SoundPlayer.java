package net.tejty.gamediscs.games.audio;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.tejty.gamediscs.sounds.SoundRegistry;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class SoundPlayer {
    private final SoundManager manager;
    private final Random random;

    public SoundPlayer() {
        manager = Minecraft.getInstance().getSoundManager();
        random = new Random();
    }

    public void play(SoundEvent event) {
        play(event, 1f);
    }
    public void play(SoundEvent event, float pitch) {
        play(event, pitch, 1f);
    }
    public void play(SoundEvent event, float pitch, float volume) {
        manager.play(SimpleSoundInstance.forUI(event, pitch, volume));
    }
    public void playRandom(SoundEvent event, float minPitch, float maxPitch, float volume) {
        play(event, random.nextFloat(minPitch, maxPitch), volume);
    }
    public void playRandom(SoundEvent event, float minPitch, float maxPitch, float minVolume, float maxVolume) {
        playRandom(event, minPitch, maxPitch, random.nextFloat(minVolume, maxVolume));
    }

    public void playClick(boolean down) {
        playRandom(SoundRegistry.CLICK.get(), down ? 0.3f : 1f, down ? 0.6f : 1.5f, down ? 0.7f : 0.2f, down ? 0.9f : 0.4f);
    }
    public void playJump() {
        playRandom(SoundRegistry.JUMP.get(), 0.8f, 1.2f, 0.8f, 1.2f);
    }
}
