package com.dmspallas.retro.spacegame.Tool;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class GameCamera
{
    private OrthographicCamera cam;
    private StretchViewport viewport;

    public GameCamera(final int width, final int height) {
        this.cam = new OrthographicCamera();
        (this.viewport = new StretchViewport((float)width, (float)height, (Camera)this.cam)).apply();
        this.cam.position.set((float)(width / 2), (float)(height / 2), 0.0f);
        this.cam.update();
    }

    public Matrix4 combined() {
        return this.cam.combined;
    }

    public void update(final int width, final int height) {
        this.viewport.update(width, height);
    }

    public Vector2 getInputInGameWorld() {
        final Vector3 inputScreen = new Vector3((float)Gdx.input.getX(), (float)(Gdx.graphics.getHeight() - Gdx.input.getY()), 0.0f);
        final Vector3 unprojected = this.cam.unproject(inputScreen);
        return new Vector2(unprojected.x, unprojected.y);
    }
}