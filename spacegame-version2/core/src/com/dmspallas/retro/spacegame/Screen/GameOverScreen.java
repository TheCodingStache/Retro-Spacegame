package com.dmspallas.retro.spacegame.Screen;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Align;
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

    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        game.scrollingBackground.updateAndRender(delta, game.batch);

        game.batch.draw(gameOverBanner, SpaceGame.WIDTH / 2 - BANNER_WIDTH / 2, SpaceGame.HEIGHT - BANNER_HEIGHT - 15, BANNER_WIDTH, BANNER_HEIGHT);

        GlyphLayout scoreLayout = new GlyphLayout(scoreFont, "Score: \n" + score, Color.WHITE, 0, Align.left, false);
        GlyphLayout highscoreLayout = new GlyphLayout(scoreFont, "Highscore: \n" + highscore, Color.WHITE, 0, Align.left, false);
        scoreFont.draw(game.batch, scoreLayout, SpaceGame.WIDTH / 2 - scoreLayout.width / 2, SpaceGame.HEIGHT - BANNER_HEIGHT - 15 * 2);
        scoreFont.draw(game.batch, highscoreLayout, SpaceGame.WIDTH / 2 - highscoreLayout.width / 2, SpaceGame.HEIGHT - BANNER_HEIGHT - scoreLayout.height - 15 * 3);

        float touchX = game.cam.getInputInGameWorld().x, touchY = SpaceGame.HEIGHT - game.cam.getInputInGameWorld().y;

        GlyphLayout tryAgainLayout = new GlyphLayout(scoreFont, "Try Again");
        GlyphLayout mainMenuLayout = new GlyphLayout(scoreFont, "Main Menu");

        float tryAgainX = SpaceGame.WIDTH / 2 - tryAgainLayout.width /2;
        float tryAgainY = SpaceGame.HEIGHT / 2 - tryAgainLayout.height / 2;
        float mainMenuX = SpaceGame.WIDTH / 2 - mainMenuLayout.width /2;
        float mainMenuY = SpaceGame.HEIGHT / 2 - mainMenuLayout.height / 2 - tryAgainLayout.height - 15;

        //Checks if hovering over try again button
        if (touchX >= tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY >= tryAgainY - tryAgainLayout.height && touchY < tryAgainY)
            tryAgainLayout.setText(scoreFont, "Try Again", Color.YELLOW, 0, Align.left, false);

        //Checks if hovering over main menu button
        if (touchX >= mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY >= mainMenuY - mainMenuLayout.height && touchY < mainMenuY)
            mainMenuLayout.setText(scoreFont, "Main Menu", Color.YELLOW, 0, Align.left, false);

        //If try again and main menu is being pressed
        if (Gdx.input.isTouched()) {
            //Try again
            if (touchX > tryAgainX && touchX < tryAgainX + tryAgainLayout.width && touchY > tryAgainY - tryAgainLayout.height && touchY < tryAgainY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new MainGameScreen(game));
                return;
            }

            //main menu
            if (touchX > mainMenuX && touchX < mainMenuX + mainMenuLayout.width && touchY > mainMenuY - mainMenuLayout.height && touchY < mainMenuY) {
                this.dispose();
                game.batch.end();
                game.setScreen(new Menu(game));
                return;
            }
        }

        //Draw buttons
        scoreFont.draw(game.batch, tryAgainLayout, tryAgainX, tryAgainY);
        scoreFont.draw(game.batch, mainMenuLayout, mainMenuX, mainMenuY);

        game.batch.end();
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
