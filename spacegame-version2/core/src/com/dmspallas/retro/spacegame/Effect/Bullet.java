package com.dmspallas.retro.spacegame.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Gdx;
import com.dmspallas.retro.spacegame.Tool.CollisionEffect;
import com.badlogic.gdx.graphics.Texture;

public class Bullet
{
    public static final int SPEED = 500;
    public static final int DEFAULT_Y = 40;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 12;
    private static Texture texture;
    float x;
    float y;
    CollisionEffect effect;
    public boolean remove;

    public Bullet(final float x) {
        this.remove = false;
        this.x = x;
        this.y = 40.0f;
        this.effect = new CollisionEffect(x, this.y, 3, 12);
        if (Bullet.texture == null) {
            Bullet.texture = new Texture("bullet.png");
        }
    }

    public void update(final float deltaTime) {
        this.y += 500.0f * deltaTime;
        if (this.y > Gdx.graphics.getHeight()) {
            this.remove = true;
            this.effect.move(this.x, this.y);
        }
    }

    public void render(final SpriteBatch batch) {
        batch.draw(Bullet.texture, this.x, this.y);
    }

    public CollisionEffect getCollisionEffect() {
        return this.effect;
    }
}