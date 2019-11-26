package com.dmspallas.retro.spacegame.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dmspallas.retro.spacegame.Tool.CollisionEffect;
import com.badlogic.gdx.graphics.Texture;

public class Enemy
{
    public static final int SPEED = 250;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    private static Texture texture;
    float x;
    float y;
    CollisionEffect effect;
    public boolean remove;

    public Enemy(final float x) {
        this.remove = false;
        this.x = x;
        this.y = 786.0f;
        this.effect = new CollisionEffect(x, this.y, 32, 32);
        if (Enemy.texture == null) {
            Enemy.texture = new Texture("asteroid.png");
        }
    }

    public void update(final float deltaTime) {
        this.y -= 250.0f * deltaTime;
        if (this.y < -32.0f) {
            this.remove = true;
        }
        this.effect.move(this.x, this.y);
    }

    public void render(final SpriteBatch batch) {
        batch.draw(Enemy.texture, this.x, this.y, 32.0f, 32.0f);
    }

    public CollisionEffect getCollisionEffect() {
        return this.effect;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }
}
