package com.dmspallas.retro.spacegame.Effect;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dmspallas.retro.spacegame.SpaceGame;
import com.dmspallas.retro.spacegame.Tool.CollisionEffect;
import com.badlogic.gdx.graphics.Texture;

public class Asteroid {

    public static final int SPEED = 250;
    public static final int WIDTH = 32;
    public static final int HEIGHT = 32;
    private static Texture texture;

    float x, y;
    CollisionEffect rect;
    public boolean remove = false;

    public Asteroid (float x) {
        this.x = x;
        this.y = SpaceGame.HEIGHT;
        this.rect = new CollisionEffect(x, y, WIDTH, HEIGHT);

        if (texture == null)
            texture = new Texture("asteroid.png");
    }

    public void update (float deltaTime) {
        y -= SPEED * deltaTime;
        if (y < -HEIGHT)
            remove = true;

        rect.move(x, y);
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, x, y, WIDTH, HEIGHT);
    }

    public CollisionEffect getCollisionEffect () {
        return rect;
    }

    public float getX () {
        return x;
    }

    public float getY () {
        return y;
    }

}
