package net.tejty.gamediscs.games.util;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import net.tejty.gamediscs.games.graphics.AnimatedImage;
import net.tejty.gamediscs.games.graphics.Image;
import net.tejty.gamediscs.games.graphics.Renderer;

public class Sprite {
    // Properties of the Sprite
    private Vec2f pos = Vec2f.ZERO;
    private Vec2f size = Vec2f.ZERO;
    private Vec2f vel = Vec2f.ZERO;
    private Renderer image = new Renderer();
    private boolean shown = true;
    public Sprite(Vec2f pos, Vec2f size, Renderer image) {
        this.pos = pos;
        this.size = size;
        this.image = image;
    }

    public Sprite(Vec2f pos, Vec2f size, Identifier image) {
        this.pos = pos;
        this.size = size;
        this.image = new Image(image, (int)size.x, (int)size.y);
    }

    // getters and setters
    public Vec2f getPos() {
        return pos;
    }
    public float getX() {
        return pos.x;
    }
    public float getY() {
        return pos.y;
    }
    public Vec2f getCenterPos() {
        return pos.add(size.multiply(0.5f));
    }
    public void setPos(Vec2f pos) {
        this.pos = pos;
    }
    public void setX(float x) {
        this.pos = new Vec2f(x, getY());
    }
    public void setY(float y) {
        this.pos = new Vec2f(getX(), y);
    }
    public void moveBy(Vec2f offset) {
        pos = pos.add(offset);
    }
    public Vec2f getSize() {
        return size;
    }
    public float getWidth() {
        return size.x;
    }
    public float getHeight() {
        return size.y;
    }
    public void setSize(Vec2f size) {
        this.size = size;
    }
    public Vec2f getVelocity() {
        return vel;
    }
    public Sprite setVelocity(Vec2f vel) {
        this.vel = vel;
        return this;
    }
    public Sprite addVelocity(Vec2f vel) {
        this.vel = this.vel.add(vel);
        return this;
    }
    public Renderer getImage() {
        return image;
    }
    public void setImage(Renderer image) {
        this.image = image;
    }
    public void setImage(Identifier image) {
        this.image = new Image(image, (int)this.size.x, (int)this.size.y);
    }
    public void show() {
        shown = true;
    }
    public void hide() {
        shown = false;
    }
    public void tick() {
        this.pos = this.pos.add(this.vel);
    }
    public void animTick() {
        if (this.image instanceof AnimatedImage animation) {
            animation.tick();
        }
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
    public void render(DrawContext graphics, int gameX, int gameY) {
        if (shown) {
            image.render(graphics, gameX + (int) this.pos.x, gameY + (int) this.pos.y);
        }
    }
}
