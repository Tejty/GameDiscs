package net.tejty.gamediscs.games.audio;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvent;
import net.tejty.gamediscs.sounds.SoundRegistry;

import java.util.Random;

@Environment(EnvType.CLIENT)
public class SoundPlayer {
    private final SoundManager manager;
    private final Random random;

    public SoundPlayer() {
        manager = MinecraftClient.getInstance().getSoundManager();
        random = new Random();
    }

    public void play(SoundEvent event) {
        play(event, 1f);
    }
    public void play(SoundEvent event, float pitch) {
        play(event, pitch, 1f);
    }
    public void play(SoundEvent event, float pitch, float volume) {
        manager.play(PositionedSoundInstance.master(event, pitch, volume));
    }
    public void playRandom(SoundEvent event, float minPitch, float maxPitch, float volume) {
        play(event, random.nextFloat(minPitch, maxPitch), volume);
    }
    public void playRandom(SoundEvent event, float minPitch, float maxPitch, float minVolume, float maxVolume) {
        playRandom(event, minPitch, maxPitch, random.nextFloat(minVolume, maxVolume));
    }

    public void playClick(boolean down) {
        playRandom(SoundRegistry.CLICK, down ? 0.3f : 1f, down ? 0.6f : 1.5f, down ? 0.7f : 0.2f, down ? 0.9f : 0.4f);
    }
    public void playJump() {
        playRandom(SoundRegistry.JUMP, 0.8f, 1.2f, 0.8f, 1.2f);
    }
    public void playPoint() {
        play(SoundRegistry.POINT, 1f, 0.7f);
    }
    public void playNewBest() {
        play(SoundRegistry.NEW_BEST, 1.5f, 2f);
    }
    public void playGameOver() {
        play(SoundRegistry.GAME_OVER, 0.9f, 2f);
    }
    public void playSelect() {
        play(SoundRegistry.SELECT, 0.5f, 0.5f);
    }
    public void playConfirm() {
        play(SoundRegistry.CONFIRM, 1f, 0.5f);
    }
}
