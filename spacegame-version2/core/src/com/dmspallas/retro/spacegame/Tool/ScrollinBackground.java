package com.dmspallas.retro.spacegame.Tool;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;

public class ScrollinBackground {
    public static final int DEFAULT_SPEED = 80;
    public static final int ACCELERATION = 50;
    public static final int GOAL_REACH_ACCELERATION = 200;
    Texture image;
    float y1;
    float y2;
    int speed;
    int goalSpeed;
    float imageScale;
    boolean speedFixed;

    public ScrollinBackground() {
        this.image = new Texture("stars_background.png");
        this.y1 = 0.0f;
        this.y2 = (float)this.image.getHeight();
        this.speed = 0;
        this.goalSpeed = 80;
        this.imageScale = (float)(1366 / this.image.getWidth());
        this.speedFixed = true;
    }

    public void updateAndRender(final float deltaTime, final SpriteBatch batch) {
        if (this.speed < this.goalSpeed) {
            this.speed += (int)(200.0f * deltaTime);
            if (this.speed > this.goalSpeed) {
                this.speed = this.goalSpeed;
            }
        }
        else if (this.speed > this.goalSpeed) {
            this.speed -= (int)(200.0f * deltaTime);
            if (this.speed < this.goalSpeed) {
                this.speed = this.goalSpeed;
            }
        }
        if (!this.speedFixed) {
            this.speed += (int)(50.0f * deltaTime);
        }
        this.y1 -= this.speed * deltaTime;
        this.y2 -= this.speed * deltaTime;
        if (this.y1 + this.image.getHeight() * this.imageScale <= 0.0f) {
            this.y1 = this.y2 + this.image.getHeight() * this.imageScale;
        }
        if (this.y2 + this.image.getHeight() * this.imageScale <= 0.0f) {
            this.y2 = this.y1 + this.image.getHeight() * this.imageScale;
        }
        batch.draw(this.image, 0.0f, this.y1, 1366.0f, this.image.getHeight() * this.imageScale);
        batch.draw(this.image, 0.0f, this.y2, 1366.0f, this.image.getHeight() * this.imageScale);
    }

    public void setSpeed(final int goalSpeed) {
        this.goalSpeed = goalSpeed;
    }

    public void setSpeedFixed(final boolean speedFixed) {
        this.speedFixed = speedFixed;
    }
}
