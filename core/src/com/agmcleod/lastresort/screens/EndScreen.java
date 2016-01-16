package com.agmcleod.lastresort.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by aaronmcleod on 2016-01-15.
 */
public class EndScreen implements Screen {
    private SpriteBatch batch;
    private Game game;
    private BitmapFont uiFont;

    public EndScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        uiFont = new BitmapFont(Gdx.files.internal("xolo24.fnt"), Gdx.files.internal("xolo24.png"), false);
        batch = new SpriteBatch();
    }

    @Override
    public void render(float delta) {
        Gdx.gl20.glClearColor(0, 0, 0, 1);
        Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        uiFont.draw(batch, "Thanks for playing", 330, 400);
        uiFont.draw(batch, "Game made by Aaron McLeod @agmcleod", 150, 280);
        uiFont.draw(batch, "Voice work done by Christer Kaitila @mcfunkypants", 100, 190);
        batch.end();
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
        uiFont.dispose();
        batch.dispose();
    }
}
