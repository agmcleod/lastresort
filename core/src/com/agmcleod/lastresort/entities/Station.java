package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by aaronmcleod on 2016-01-10.
 */
public class Station extends GameEntity {
    public Station(TextureRegion textureRegion) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.set(0, 0);
        transformComponent.width = textureRegion.getRegionWidth();
        transformComponent.height = textureRegion.getRegionHeight();

        this.add(transformComponent);
    }
}
