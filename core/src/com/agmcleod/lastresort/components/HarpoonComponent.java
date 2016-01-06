package com.agmcleod.lastresort.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Joint;

/**
 * Created by Aaron on 1/2/2016.
 */
public class HarpoonComponent implements Component {
    public boolean firing = false;
    public boolean fireTriggered = false;
    public Joint joint;
    public Vector2 target = new Vector2();
}
