package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.TransformComponent;

/**
 * Created by Aaron on 1/2/2016.
 */
public class Harpoon extends GameEntity {
    private GameEntity parent;
    public Harpoon(GameEntity parent) {
        this.parent = parent;

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.x = 0;
        transformComponent.position.y = 0;
        transformComponent.width = 20;
        transformComponent.height = 50;

        this.add(transformComponent);
    }
}
