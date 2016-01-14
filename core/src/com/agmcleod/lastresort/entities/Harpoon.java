package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Aaron on 1/2/2016.
 */
public class Harpoon extends GameEntity {
    private GameEntity parent;
    public Harpoon(TextureRegion textureRegion, GameEntity parent) {
        this.parent = parent;

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.x = parent.getTransform().width / 2;
        transformComponent.position.y = parent.getTransform().height / 4;
        transformComponent.width = textureRegion.getRegionWidth();
        transformComponent.height = textureRegion.getRegionHeight();

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
