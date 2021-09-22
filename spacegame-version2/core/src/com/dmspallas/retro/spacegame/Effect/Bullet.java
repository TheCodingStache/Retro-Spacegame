package com.dmspallas.retro.spacegame.Effect;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dmspallas.retro.spacegame.SpaceGame;
import com.dmspallas.retro.spacegame.Tool.CollisionEffect;

public class Bullet {

    public static final int SPEED = 500;
    public static final int DEFAULT_Y = 40;
    public static final int WIDTH = 3;
    public static final int HEIGHT = 12;
    private static Texture texture;

    float x, y;
    CollisionEffect rect;
    public boolean remove = false;

    public Bullet(float x) {
        this.x = x;
        this.y = DEFAULT_Y;
        this.rect = new CollisionEffect(x, y, WIDTH, HEIGHT);

        if (texture == null)
            texture = new Texture("bullet.png");
    }

    public void update(float deltaTime) {
        y += SPEED * deltaTime;
        if (y > SpaceGame.HEIGHT)
            remove = true;

        rect.move(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    public CollisionEffect getCollisionEffect() {
        return rect;
    }

}