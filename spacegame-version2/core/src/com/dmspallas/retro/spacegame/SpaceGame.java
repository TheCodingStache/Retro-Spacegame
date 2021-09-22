package com.dmspallas.retro.spacegame;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.dmspallas.retro.spacegame.Screen.Menu;
import com.dmspallas.retro.spacegame.Tool.GameCamera;
import com.dmspallas.retro.spacegame.Tool.ScrollinBackground;

public class SpaceGame extends Game {
    public static final int WIDTH = 768;
    public static final int HEIGHT = 1366;
    public static boolean IS_MOBILE = false;

    public SpriteBatch batch;
    public ScrollinBackground scrollingBackground;
    public GameCamera cam;

    @Override
    public void create() {
        batch = new SpriteBatch();
        cam = new GameCamera(WIDTH, HEIGHT);

        if (Gdx.app.getType() == Application.ApplicationType.Android || Gdx.app.getType() == Application.ApplicationType.iOS)
            IS_MOBILE = true;
        IS_MOBILE = true;

        this.scrollingBackground = new ScrollinBackground();
        this.setScreen(new Menu(this));
    }

    @Override
    public void render() {
        batch.setProjectionMatrix(cam.combined());
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        cam.update(width, height);
        super.resize(width, height);
    }
}
