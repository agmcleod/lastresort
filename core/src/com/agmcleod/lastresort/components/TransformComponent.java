package com.agmcleod.lastresort.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Aaron on 12/20/2015.
 */
public class TransformComponent implements Component {
    public Vector2 position;
    public float rotation;
    public Vector2 scale;
    public float width;
    public float height;

    public TransformComponent() {
        position = new Vector2();
        rotation = 0;
        scale = new Vector2(1, 1);
    }
}
