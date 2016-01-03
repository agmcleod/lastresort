package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.components.TransformComponent;

/**
 * Created by Aaron on 1/2/2016.
 */
public class Harpoon extends GameEntity {
    private GameEntity parent;
    public Harpoon(GameEntity parent) {
        this.parent = parent;

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.x = parent.getTransform().width / 2;
        transformComponent.position.y = parent.getTransform().height / 4;
        transformComponent.width = 20;
        transformComponent.height = 50;

        this.add(transformComponent);
        this.add(new HarpoonRotateToTargetComponent());
    }

    public HarpoonRotateToTargetComponent getHarpoonRotateToTargetComponent() {
        return this.getComponent(HarpoonRotateToTargetComponent.class);
    }

    public GameEntity getParent() {
        return parent;
    }
}
