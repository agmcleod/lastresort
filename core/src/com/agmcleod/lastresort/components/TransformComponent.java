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

    public TransformComponent(float x, float y, float width, float height, float rotation, float scaleX, float scaleY) {
        position = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.rotation = rotation;
        scale = new Vector2(scaleX, scaleY);
    }
}
