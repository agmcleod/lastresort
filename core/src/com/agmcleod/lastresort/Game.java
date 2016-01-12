package com.agmcleod.lastresort;

import com.agmcleod.lastresort.screens.PlayScreen;

public class Game extends com.badlogic.gdx.Game {
    public static final float WORLD_TO_BOX = 0.01f;
    public static final float BOX_TO_WORLD = 100f;

    public static final short STATION_MASK = 0x0001;
    public static final short PLAYER_MASK = 0x0002;
    public static final short OBJECT_MASK = 0x0004;

    private PlayScreen playScreen;

    @Override
    public void create () {
        playScreen = new PlayScreen(this);
        setScreen(playScreen);
    }

}
