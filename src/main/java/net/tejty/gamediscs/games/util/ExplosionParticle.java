package net.tejty.gamediscs.games.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.math.Vec2f;
import net.tejty.gamediscs.games.graphics.ExplosionParticleRenderer;
import net.tejty.gamediscs.games.graphics.Renderer;

public class ExplosionParticle extends Particle {
    private ExplosionParticleRenderer renderer = null;
    public ExplosionParticle(Vec2f pos, int lifetime, ParticleLevel level) {
        super(pos, new Renderer(), lifetime, level);
    }

    @Override
    public void render(DrawContext graphics, int gameX, int gameY, GameStage stage) {
        if (level.isFor(stage)) {
            if (renderer == null) {
                renderer = new ExplosionParticleRenderer(this);
            }
            renderer.render(graphics, gameX + (int)getX(), gameY + (int)getY());
        }
    }
}
