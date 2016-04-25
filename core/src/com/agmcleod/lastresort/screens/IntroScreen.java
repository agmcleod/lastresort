package com.agmcleod.lastresort.screens;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.TransitionCallback;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Align;

/**
 * Created by aaronmcleod on 2016-01-17.
 */
public class IntroScreen implements Screen, InputProcessor {
    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion background;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Music currentClip;
    private int currentClipIndex;
    private FileHandle[] clipFiles;
    private String[] clipTexts;
    private float fadeTimer = Game.FADE_TIMEOUT;
    private BitmapFont font;
    private Game game;
    private final float CLIP_TIMEOUT = 0.3f;
    private float nextClipTimeout = 0;
    private ShapeRenderer shapeRenderer;
    private boolean transitioningIn;
    private boolean transitioningOut;
    private TransitionCallback fadeInCallback;
    private TransitionCallback fadeOutCallback;

    public IntroScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        atlas = new TextureAtlas(Gdx.files.internal("sprites.txt"));
        background = atlas.findRegion("stars");
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        currentClipIndex = -1;
        currentClip = null;
        clipFiles = new FileHandle[] {
                Gdx.files.internal("intro1.ogg"), Gdx.files.internal("intro2.ogg"),
                Gdx.files.internal("intro3.ogg"), Gdx.files.internal("intro4.ogg"),
                Gdx.files.internal("intro5.ogg"), Gdx.files.internal("intro6.ogg")
        };

        clipTexts = new String[] {
                "The day is coming when we can no longer call earth our home.",
                "The human race has been a pinnacle of scientific discovery, but for our gains in science & industry, our planet has paid the price.",
                "We put great effort towards fixing the problem. While we have improved the situation, I'm afraid we have only slowed it down.",
                "We are embarking on a project to build a new home. To do this, we need resources, ones we cannot find on our planet.",
                "You are one of many who will be deployed into the far reaches of outer space.",
                "Out there we need you to help collect materials that we can use to build our new home. Good luck pilot."
        };

        font = new BitmapFont(Gdx.files.internal("xolo24.fnt"), Gdx.files.internal("xolo24.png"), false);

        shapeRenderer = new ShapeRenderer();

        transitioningIn = true;
        transitioningOut = false;

        fadeInCallback = new TransitionCallback() {
            @Override
            public void callback() {
                transitioningIn = false;
                nextClip();
            }
        };

        fadeOutCallback = new TransitionCallback() {
            @Override
            public void callback() {
                game.startPlayScreen();
            }
        };
        Gdx.input.setInputProcessor(this);
    }

    private void nextClip() {
        currentClipIndex++;
        if (currentClipIndex >= clipFiles.length) {
            currentClip.dispose();
            transitioningOut = true;
        } else {
            if (currentClip != null) {
                currentClip.dispose();
            }

            currentClip = Gdx.audio.newMusic(clipFiles[currentClipIndex]);
            currentClip.play();
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
        float bgWidth = background.getRegionWidth();
        float bgHeight = background.getRegionHeight();

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(background, bgWidth, 0);
        batch.draw(background, 0, bgHeight);
        batch.draw(background, bgWidth, bgHeight);
        if (currentClipIndex >= 0 && currentClipIndex < clipTexts.length) {
            font.draw(batch, clipTexts[currentClipIndex], (Gdx.graphics.getWidth() - 800) / 2, 400, 800, Align.center, true);
        }
        batch.end();

        if (transitioningIn ||  transitioningOut) {
            if (transitioningIn) {
                fadeTimer -= delta;
                game.drawBlackTransparentSquare(camera, shapeRenderer, fadeTimer / Game.FADE_TIMEOUT, fadeInCallback);
            } else {
                fadeTimer += delta;
                game.drawBlackTransparentSquare(camera, shapeRenderer, fadeTimer / Game.FADE_TIMEOUT, fadeOutCallback);
            }
        } else {
            if (!currentClip.isPlaying() && nextClipTimeout <= 0) {
                nextClipTimeout = CLIP_TIMEOUT;
            }

            if (nextClipTimeout > 0) {
                nextClipTimeout -= delta;
                if (nextClipTimeout <= 0) {
                    nextClip();
                }
            }
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
        batch.dispose();
        atlas.dispose();
        font.dispose();
        shapeRenderer.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (currentClip != null) {
            currentClip.stop();
            currentClip.dispose();
        }
        transitioningOut = true;
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
