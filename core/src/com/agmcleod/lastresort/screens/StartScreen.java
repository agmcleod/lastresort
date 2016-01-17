package com.agmcleod.lastresort.screens;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.TransitionCallback;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by aaronmcleod on 2016-01-13.
 */
public class StartScreen implements Screen, InputProcessor {
    private TextureAtlas atlas;
    private Game game;
    private TextureRegion background;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private float fadeTimer;
    private ShapeRenderer shapeRenderer;
    private boolean transitioning;
    private TransitionCallback transitionCallback;

    public StartScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        atlas = new TextureAtlas(Gdx.files.internal("sprites.txt"));
        background = atlas.findRegion("title");
        shapeRenderer = new ShapeRenderer();
        transitioning = false;
        transitionCallback = new TransitionCallback() {
            @Override
            public void callback() {
                game.startIntroScreen();
            }
        };
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, background.getRegionWidth(), background.getRegionHeight());
        batch.end();

        if (transitioning) {
            game.drawBlackTransparentSquare(camera, shapeRenderer, fadeTimer / Game.FADE_TIMEOUT, transitionCallback);
            fadeTimer += delta;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        atlas.dispose();
        batch.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (!transitioning) {
            transitioning = true;
            fadeTimer = 0;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
