package com.agmcleod.lastresort.components;

import com.agmcleod.lastresort.entities.GameEntity;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;

/**
 * Created by Aaron on 1/2/2016.
 */
public class HarpoonComponent implements Component {
    public boolean firing = false;
    public boolean fireTriggered = false;
    public boolean queueFireTrigger;
    public GameEntity targetEntity;
    public Body ropeBody = null;
    public float ropeWidth = 0;
    public float ropeHeight = 0;

    public void cleanup() {
        firing = false;
        fireTriggered = false;
        targetEntity = null;
        ropeBody = null;
    }
}
