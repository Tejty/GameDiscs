package net.tejty.gamediscs.games.graphics;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.tejty.gamediscs.GameDiscsMod;
import net.tejty.gamediscs.games.util.Particle;

public class ExplosionParticleRenderer extends Renderer {
    private final MultiImage image;
    private final Particle particle;

    public ExplosionParticleRenderer(Particle particle) {
        image = new MultiImage(new Identifier(GameDiscsMod.MOD_ID, "textures/games/sprite/explosion.png"), 2, 16, 8);
        this.particle = particle;
    }

    @Override
    public void render(DrawContext graphics, int posX, int posY) {
        image.setImage((int)((Math.sqrt(particle.getVelocity().lengthSquared())) * 4)).render(graphics, posX, posY);
    }
}
