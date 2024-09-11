package net.tejty.gamediscs.game;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;

public class Sprite {
    private Vec2 pos;
    private Vec2 size;
    private Vec2 vel;
    private ResourceLocation image;
    public Sprite(Vec2 pos, Vec2 size, ResourceLocation image) {
        this.pos = pos;
        this.size = size;
        this.image = image;
        this.vel = Vec2.ZERO;
    }

    public Vec2 getPos() {
        return pos;
    }
    public float getX() {
        return pos.x;
    }
    public float getY() {
        return pos.y;
    }
    public Vec2 getCenterPos() {
        return pos.add(size.scale(0.5f));
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }
    public void moveBy(Vec2 offset) {
        pos = pos.add(offset);
    }

    public Vec2 getSize() {
        return size;
    }
    public float getWidth() {
        return size.x;
    }
    public float getHeight() {
        return size.y;
    }

    public void setSize(Vec2 size) {
        this.size = size;
    }

    public Vec2 getVelocity() {
        return vel;
    }
    public void setVelocity(Vec2 vel) {
        this.vel = vel;
    }
    public void addVelocity(Vec2 vel) {
        this.vel = this.vel.add(vel);
    }

    public ResourceLocation getImage() {
        return image;
    }
    public void setImage(ResourceLocation image) {
        this.image = image;
    }

    public void tick() {
        this.pos = this.pos.add(this.vel);
    }

    public boolean isTouching(Sprite other) {
        return (
                this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY()
        );
    }

    public void render(GuiGraphics graphics, int gameX, int gameY) {
        graphics.blit(image, gameX + (int)pos.x, gameY + (int)pos.y, 0, 0, 0, (int)size.x, (int)size.y, (int)size.x, (int)size.y);
    }
}
