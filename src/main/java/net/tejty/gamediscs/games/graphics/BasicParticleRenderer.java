package net.tejty.gamediscs.games.graphics;

import net.minecraft.resources.ResourceLocation;

public class BasicParticleRenderer extends ParticleRenderer {
    private static final ResourceLocation IMAGE = new ResourceLocation("gamediscs:textures/particle/basic_particle.png");

    public BasicParticleRenderer(ParticleColor color) {
        super(IMAGE, 2, 32, 0, color.value() * 2, 2, 2);
    }
}
