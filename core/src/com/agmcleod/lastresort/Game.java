package com.agmcleod.lastresort;

import com.agmcleod.lastresort.screens.EndScreen;
import com.agmcleod.lastresort.screens.IntroScreen;
import com.agmcleod.lastresort.screens.PlayScreen;
import com.agmcleod.lastresort.screens.StartScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game extends com.badlogic.gdx.Game {
    public static final float WORLD_TO_BOX = 0.01f;
    public static final float BOX_TO_WORLD = 100f;

    public static final short STATION_MASK = 0x0001;
    public static final short PLAYER_MASK = 0x0002;
    public static final short OBJECT_MASK = 0x0004;

    public static final float FADE_TIMEOUT = 0.5f;

    private Color blackColor;
    private IntroScreen introScreen;
    private PlayScreen playScreen;
    private EndScreen endScreen;

    @Override
    public void create () {
        blackColor = new Color(0, 0, 0, 1);
        introScreen = new IntroScreen(this);
        playScreen = new PlayScreen(this);
        endScreen = new EndScreen(this);
        setScreen(new StartScreen(this));
    }

    public void drawBlackTransparentSquare(Camera camera, ShapeRenderer shapeRenderer, float percent, TransitionCallback callback) {
        Gdx.gl.glEnable(GL20.GL_BLEND);

        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        blackColor.set(0, 0, 0, percent);
        shapeRenderer.setColor(blackColor);
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        shapeRenderer.rect(camera.position.x - w / 2, camera.position.y - h / 2, w, h);
        shapeRenderer.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        if (percent > 1.0f || percent < 0f) {
            callback.callback();
        }
    }

    public void startEndScreen() {
        setScreen(endScreen);
    }

    public void startIntroScreen() {
        setScreen(introScreen);
    }

    public void startPlayScreen() {
        setScreen(playScreen);
    }

}
