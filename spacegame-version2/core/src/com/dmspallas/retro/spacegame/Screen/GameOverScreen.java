package com.dmspallas.retro.spacegame.Screen;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.dmspallas.retro.spacegame.SpaceGame;
import com.badlogic.gdx.Screen;

public class GameOverScreen implements Screen
{
    private static final int BANNER_WIDTH = 350;
    private static final int BANNER_HEIGHT = 100;
    SpaceGame game;
    int score;
    int highscore;
    Texture gameOverBanner;
    BitmapFont scoreFont;

    public GameOverScreen(final SpaceGame game, final int score) {
        this.game = game;
        this.score = score;
        final Preferences prefs = Gdx.app.getPreferences("spacegame");
        this.highscore = prefs.getInteger("highscore", 0);
        if (score > this.highscore) {
            prefs.putInteger("highscore", score);
            prefs.flush();
        }
        this.gameOverBanner = new Texture("game_over.png");
        this.scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        game.scrollingBackground.setSpeedFixed(true);
        game.scrollingBackground.setSpeed(80);
    }

    public void show() {
    }

    public void render(final float delta) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(16384);
        this.game.batch.begin();
        this.game.scrollingBackground.updateAndRender(delta, this.game.batch);
        this.game.batch.draw(this.gameOverBanner, 508.0f, 671.0f, 350.0f, 100.0f);
        final GlyphLayout scoreLayout = new GlyphLayout(this.scoreFont, (CharSequence)("Score: \n" + this.score), Color.WHITE, 0.0f, 8, false);
        final GlyphLayout highscoreLayout = new GlyphLayout(this.scoreFont, (CharSequence)("Highscore: \n" + this.highscore), Color.WHITE, 0.0f, 8, false);
        this.scoreFont.draw((Batch)this.game.batch, scoreLayout, 683.0f - scoreLayout.width / 2.0f, 656.0f);
        this.scoreFont.draw((Batch)this.game.batch, highscoreLayout, 683.0f - highscoreLayout.width / 2.0f, 686.0f - scoreLayout.height - 45.0f);
        final float touchX = this.game.cam.getInputInGameWorld().x;
        final float touchY = 786.0f - this.game.cam.getInputInGameWorld().y;
        final GlyphLayout tryAgainLayout = new GlyphLayout(this.scoreFont, (CharSequence)"Try Again");
        final GlyphLayout mainMenuLayout = new GlyphLayout(this.scoreFont, (CharSequence)"Main Menu");
        final float tryAgainX = 683.0f - tryAgainLayout.width / 2.0f;
        final float tryAgainY = 393.0f - tryAgainLayout.height / 2.0f;
        final float mainMenuX = 683.0f - mainMenuLayout.width / 2.0f;
        final float mainMenuY = 393.0f - mainMenuLayout.height / 2.0f - tryAgainLayout.height - 15.0f;
        if (touchX >= tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY >= tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
            tryAgainLayout.setText(this.scoreFont, (CharSequence)"Try Again", Color.YELLOW, 0.0f, 8, false);
        }
        if (touchX >= mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY >= mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
            mainMenuLayout.setText(this.scoreFont, (CharSequence)"Main Menu", Color.YELLOW, 0.0f, 8, false);
        }
        if (Gdx.input.isTouched()) {
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                this.game.batch.end();
                this.game.setScreen((Screen)new MainGameScreen(this.game));
                return;
            }
            if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
                this.dispose();
                this.game.batch.end();
                this.game.setScreen((Screen)new Menu(this.game));
                return;
            }
        }
        this.scoreFont.draw((Batch)this.game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        this.scoreFont.draw((Batch)this.game.batch, mainMenuLayout, mainMenuX, mainMenuY);
        this.game.batch.end();
    }

    public void resize(final int width, final int height) {
    }

    public void pause() {
    }

    public void resume() {
    }

    public void hide() {
    }

    public void dispose() {
    }
}
