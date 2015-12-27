package com.agmcleod.lastresort;

import com.agmcleod.lastresort.screens.PlayScreen;

public class Game extends com.badlogic.gdx.Game {
    public static final float WORLD_TO_BOX = 0.01f;
    public static final float BOX_TO_WORLD = 100f;

    private PlayScreen playScreen;

    @Override
    public void create () {
        playScreen = new PlayScreen();
        setScreen(playScreen);
    }

}
