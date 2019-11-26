package com.dmspallas.retro.spacegame.Tool;

public class CollisionEffect
{
    float x;
    float y;
    int width;
    int height;

    public CollisionEffect(final float x, final float y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void move(final float x, final float y) {
        this.x = x;
        this.y = y;
    }

    public boolean collisionWith(final CollisionEffect effect) {
        return this.x < effect.x + effect.width && this.y < effect.y + effect.height && this.x + this.width > effect.x && this.y + this.height > effect.y;
    }
}
