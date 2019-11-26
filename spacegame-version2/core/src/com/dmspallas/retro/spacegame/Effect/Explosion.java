package com.dmspallas.retro.spacegame.Effect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public class Explosion
{
    public static final float FRAME_LENGTH = 0.2f;
    public static final int OFFSET = 8;
    public static final int SIZE = 64;
    public static final int IMAGE_SIZE = 32;
    private static Animation<TextureRegion> anim;
    float x;
    float y;
    float statetime;
    public boolean remove;

    public Explosion(final float x, final float y) {
        this.remove = false;
        this.x = x - 8.0f;
        this.y = y - 8.0f;
        this.statetime = 0.0f;
        if (Explosion.anim == null) {
            Explosion.anim = (Animation<TextureRegion>)new Animation(0.2f, (Object[])TextureRegion.split(new Texture("explosion.png"), 32, 32)[0]);
        }
    }

    public void update(final float deltatime) {
        this.statetime += deltatime;
        if (Explosion.anim.isAnimationFinished(this.statetime)) {
            this.remove = true;
        }
    }

    public void render(final SpriteBatch batch) {
        batch.draw((TextureRegion)Explosion.anim.getKeyFrame(this.statetime), this.x, this.y, 64.0f, 64.0f);
    }

    static {
        Explosion.anim = null;
    }
}
