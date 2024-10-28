package net.tejty.gamediscs.games.graphics;

import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;

public class BasicParticleRenderer extends ParticleRenderer {
    private static final Identifier IMAGE = new Identifier(GameDiscsMod.MOD_ID, "textures/particle/basic_particle.png");

    public BasicParticleRenderer(ParticleColor color) {
        super(IMAGE, 2, 32, 0, color.value() * 2, 2, 2);
    }
}
