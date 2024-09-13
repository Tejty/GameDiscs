package net.tejty.gamediscs.games.util;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec2;
import net.tejty.gamediscs.games.graphics.Image;
import net.tejty.gamediscs.games.graphics.Renderer;

public class Sprite {
    // Properties of the Sprite
    private Vec2 pos = Vec2.ZERO;
    private Vec2 size = Vec2.ZERO;
    private Vec2 vel = Vec2.ZERO;
    private Renderer image = new Renderer();
    public Sprite(Vec2 pos, Vec2 size, Renderer image) {
        this.pos = pos;
        this.size = size;
        this.image = image;
    }

    public Sprite(Vec2 pos, Vec2 size, ResourceLocation image) {
        this.pos = pos;
        this.size = size;
        this.image = new Image(image, (int)size.x, (int)size.y);
    }

    // getters and setters
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
    public Renderer getImage() {
        return image;
    }
    public void setImage(Renderer image) {
        this.image = image;
    }
    public void setImage(ResourceLocation image) {
        this.image = new Image(image, (int)this.size.x, (int)this.size.y);
    }
    public void tick() {
        this.pos = this.pos.add(this.vel);
    }

    /**
     * @param other Sprite to check if it's colliding with it
     * @return True, if they collide together, false otherwise
     */
    public boolean isTouching(Sprite other) {
        return (
                this.getX() < other.getX() + other.getWidth() &&
                this.getX() + this.getWidth() > other.getX() &&
                this.getY() < other.getY() + other.getHeight() &&
                this.getY() + this.getHeight() > other.getY()
        );
    }

    /**
     * Renders the sprite
     * @param graphics GuiGraphics used for rendering
     * @param gameX X position of game
     * @param gameY Y position of game
     */
    public void render(GuiGraphics graphics, int gameX, int gameY) {
        image.render(graphics, gameX + (int)this.pos.x, gameY + (int)this.pos.y);
    }
}
