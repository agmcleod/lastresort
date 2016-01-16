package com.agmcleod.lastresort;

import com.agmcleod.lastresort.screens.EndScreen;
import com.agmcleod.lastresort.screens.PlayScreen;
import com.agmcleod.lastresort.screens.StartScreen;

public class Game extends com.badlogic.gdx.Game {
    public static final float WORLD_TO_BOX = 0.01f;
    public static final float BOX_TO_WORLD = 100f;

    public static final short STATION_MASK = 0x0001;
    public static final short PLAYER_MASK = 0x0002;
    public static final short OBJECT_MASK = 0x0004;

    private PlayScreen playScreen;
    private EndScreen endScreen;

    @Override
    public void create () {
        playScreen = new PlayScreen(this);
        endScreen = new EndScreen(this);
        setScreen(new StartScreen(this));
    }

    public void startEndScreen() {
        setScreen(endScreen);
    }

    public void startPlayScreen() {
        setScreen(playScreen);
    }

}
