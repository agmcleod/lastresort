package com.agmcleod.lastresort.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Aaron on 1/2/2016.
 */
public class HarpoonComponent implements Component {
    public boolean firing = false;
    public boolean fireTriggered = false;
    public Vector2 target = new Vector2();
}
