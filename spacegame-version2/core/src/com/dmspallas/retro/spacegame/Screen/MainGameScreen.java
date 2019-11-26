package com.dmspallas.retro.spacegame.Screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.Gdx;
import com.dmspallas.retro.spacegame.Effect.Bullet;
import com.dmspallas.retro.spacegame.Effect.Enemy;
import com.dmspallas.retro.spacegame.Effect.Explosion;
import com.dmspallas.retro.spacegame.SpaceGame;
import com.dmspallas.retro.spacegame.Tool.CollisionEffect;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.Screen;

public class MainGameScreen implements Screen
{
    private static final float SPEED = 300.0f;
    private static final float SHIP_ANIMATION_SPEED = 0.5f;
    private static final int SHIP_WIDTH_PIXEL = 17;
    private static final int SHIP_HEIGHT_PIXEL = 32;
    private static final int SHIP_WIDTH = 51;
    private static final int SHIP_HEIGHT = 96;
    private static final float ROLL_TIMER_SWITCH_TIME = 0.25f;
    private static final float SHOOT_WAIT_TIME = 0.3f;
    private static final float MIN_ENEMY_SPAWN_TIME = 0.05f;
    private static final float MAX_ENEMY_SPAWN_TIME = 0.1f;
    Animation[] rolls;
    private float x;
    private float y;
    private int roll;
    private float rollTimer;
    private float stateTime;
    private float shootTimer;
    private float enemySpawnTimer;
    Random random;
    SpaceGame game;
    ArrayList<Bullet> bullets;
    ArrayList<Enemy> enemies;
    ArrayList<Explosion> explosions;
    Texture blank;
    Texture controls;
    BitmapFont scoreFont;
    CollisionEffect playerRect;
    private float health;
    private int score;
    private boolean showControls;

    public MainGameScreen(final SpaceGame game) {
        this.health = 1.0f;
        this.showControls = true;
        this.game = game;
        this.y = 15.0f;
        this.x = 658.0f;
        this.bullets = new ArrayList<>();
        this.enemies = new ArrayList<>();
        this.explosions = new ArrayList<>();
        this.scoreFont = new BitmapFont(Gdx.files.internal("fonts/score.fnt"));
        this.playerRect = new CollisionEffect(0.0f, 0.0f, 51, 96);
        this.blank = new Texture("blank.png");
        if (SpaceGame.IS_MOBILE) {
            this.controls = new Texture("controls.png");
        }
        this.score = 0;
        this.random = new Random();
        this.enemySpawnTimer = this.random.nextFloat() * 0.1f;
        this.shootTimer = 0.0f;
        this.roll = 2;
        this.rollTimer = 0.0f;
        this.rolls = new Animation[5];
        final TextureRegion[][] rollSpriteSheet = TextureRegion.split(new Texture("ship.png"), 17, 32);
        this.rolls[0] = new Animation(0.5f, (Object[])rollSpriteSheet[2]);
        this.rolls[1] = new Animation(0.5f, (Object[])rollSpriteSheet[1]);
        this.rolls[2] = new Animation(0.5f, (Object[])rollSpriteSheet[0]);
        this.rolls[3] = new Animation(0.5f, (Object[])rollSpriteSheet[3]);
        this.rolls[4] = new Animation(0.5f, (Object[])rollSpriteSheet[4]);
        game.scrollingBackground.setSpeedFixed(false);
    }

    public void show() {
    }

    public void render(final float delta) {
        this.shootTimer += delta;
        if (this.isRight() || (this.isLeft() && this.shootTimer >= 0.3f)) {
            this.shootTimer = 0.0f;
            this.showControls = false;
            int offset = 4;
            if (this.roll == 1 || this.roll == 3) {
                offset = 8;
            }
            if (this.roll == 0 || this.roll == 4) {
                offset = 16;
            }
            this.bullets.add(new Bullet(this.x + offset));
            this.bullets.add(new Bullet(this.x + 51.0f - offset));
        }
        this.enemySpawnTimer -= delta;
        if (this.enemySpawnTimer <= 0.0f) {
            this.enemySpawnTimer = this.random.nextFloat() * 0.05f + 0.05f;
            this.enemies.add(new Enemy((float)this.random.nextInt(1334)));
        }
        final ArrayList<Enemy> enemiesToRemove = new ArrayList<Enemy>();
        for (final Enemy enemy : this.enemies) {
            enemy.update(delta);
            if (enemy.remove) {
                enemiesToRemove.add(enemy);
            }
        }
        final ArrayList<Bullet> bulletsToRemove = new ArrayList<Bullet>();
        for (final Bullet bullet : this.bullets) {
            bullet.update(delta);
            if (bullet.remove) {
                bulletsToRemove.add(bullet);
            }
        }
        final ArrayList<Explosion> explosionsToRemove = new ArrayList<Explosion>();
        for (final Explosion explosion : this.explosions) {
            explosion.update(delta);
            if (explosion.remove) {
                explosionsToRemove.add(explosion);
            }
        }
        this.explosions.removeAll(explosionsToRemove);
        if (this.isLeft()) {
            this.x -= 300.0f * Gdx.graphics.getDeltaTime();
            if (this.x < 0.0f) {
                this.x = 0.0f;
            }
            if (this.isJustLeft() && !this.isRight() && this.roll > 0) {
                this.rollTimer = 0.0f;
                --this.roll;
            }
            this.rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(this.rollTimer) > 0.25f && this.roll > 0) {
                this.rollTimer -= 0.25f;
                --this.roll;
            }
        }
        else if (this.roll < 2) {
            this.rollTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(this.rollTimer) > 0.25f && this.roll < 4) {
                this.rollTimer -= 0.25f;
                ++this.roll;
            }
        }
        if (this.isRight()) {
            this.x += 300.0f * Gdx.graphics.getDeltaTime();
            if (this.x + 51.0f > 1366.0f) {
                this.x = 1315.0f;
            }
            if (this.isJustRight() && !this.isLeft() && this.roll > 0) {
                this.rollTimer = 0.0f;
                --this.roll;
            }
            this.rollTimer += Gdx.graphics.getDeltaTime();
            if (Math.abs(this.rollTimer) > 0.25f && this.roll < 4) {
                this.rollTimer -= 0.25f;
                ++this.roll;
            }
        }
        else if (this.roll > 2) {
            this.rollTimer -= Gdx.graphics.getDeltaTime();
            if (Math.abs(this.rollTimer) > 0.25f && this.roll > 0) {
                this.rollTimer -= 0.25f;
                --this.roll;
            }
        }
        this.playerRect.move(this.x, this.y);
        for (final Bullet bullet2 : this.bullets) {
            for (final Enemy enemy2 : this.enemies) {
                if (bullet2.getCollisionEffect().collisionWith(enemy2.getCollisionEffect())) {
                    bulletsToRemove.add(bullet2);
                    enemiesToRemove.add(enemy2);
                    this.score += 100;
                }
            }
        }
        this.bullets.removeAll(bulletsToRemove);
        for (final Enemy enemy3 : this.enemies) {
            if (enemy3.getCollisionEffect().collisionWith(this.playerRect)) {
                enemiesToRemove.add(enemy3);
                this.health -= (float)0.1;
                if (this.health <= 0.0f) {
                    this.dispose();
                    this.game.setScreen((Screen)new GameOverScreen(this.game, this.score));
                    return;
                }
                continue;
            }
        }
        this.enemies.removeAll(enemiesToRemove);
        this.stateTime += delta;
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(16384);
        this.game.batch.begin();
        this.game.scrollingBackground.updateAndRender(delta, this.game.batch);
        final GlyphLayout scoreLayout = new GlyphLayout(this.scoreFont, (CharSequence)("" + this.score));
        this.scoreFont.draw((Batch)this.game.batch, scoreLayout, 683.0f - scoreLayout.width / 2.0f, 786.0f - scoreLayout.height - 10.0f);
        for (final Bullet bullet3 : this.bullets) {
            bullet3.render(this.game.batch);
        }
        for (final Enemy enemy4 : this.enemies) {
            enemy4.render(this.game.batch);
        }
        for (final Explosion explosion2 : this.explosions) {
            explosion2.render(this.game.batch);
        }
        if (this.health > 0.6f) {
            this.game.batch.setColor(Color.GREEN);
        }
        else if (this.health > 0.2f) {
            this.game.batch.setColor(Color.ORANGE);
        }
        else {
            this.game.batch.setColor(Color.RED);
        }
        this.game.batch.draw(this.blank, 0.0f, 0.0f, 1366.0f * this.health, 5.0f);
        this.game.batch.setColor(Color.WHITE);
        this.game.batch.draw((TextureRegion)this.rolls[this.roll].getKeyFrame(this.stateTime, true), this.x, this.y, 51.0f, 96.0f);
        if (this.showControls) {
            if (SpaceGame.IS_MOBILE) {
                this.game.batch.setColor(Color.RED);
                this.game.batch.draw(this.controls, 0.0f, 0.0f, 683.0f, 786.0f, 0, 0, 683, 786, false, false);
                this.game.batch.setColor(Color.BLUE);
                this.game.batch.draw(this.controls, 683.0f, 0.0f, 683.0f, 786.0f, 0, 0, 683, 786, true, false);
                this.game.batch.setColor(Color.WHITE);
            }
            else {
                final GlyphLayout instructionsLayout = new GlyphLayout(this.scoreFont, (CharSequence)"Press Left/Right Arrowkeys to Shoot!", Color.WHITE, 1316.0f, 1, true);
                this.scoreFont.draw((Batch)this.game.batch, instructionsLayout, 683.0f - instructionsLayout.width / 2.0f, 150.0f);
            }
        }
        this.game.batch.end();
    }

    private boolean isRight() {
        return Gdx.input.isKeyPressed(22) || (Gdx.input.isTouched() && this.game.cam.getInputInGameWorld().x >= 683.0f);
    }

    private boolean isLeft() {
        return Gdx.input.isKeyPressed(21) || (Gdx.input.isTouched() && this.game.cam.getInputInGameWorld().x < 683.0f);
    }

    private boolean isJustRight() {
        return Gdx.input.isKeyJustPressed(22) || (Gdx.input.justTouched() && this.game.cam.getInputInGameWorld().x >= 683.0f);
    }

    private boolean isJustLeft() {
        return Gdx.input.isKeyJustPressed(21) || (Gdx.input.justTouched() && this.game.cam.getInputInGameWorld().x < 683.0f);
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
