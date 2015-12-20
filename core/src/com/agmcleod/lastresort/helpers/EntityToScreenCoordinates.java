package com.agmcleod.lastresort.helpers;

import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.GameEntity;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Aaron on 12/20/2015.
 */
public class EntityToScreenCoordinates {
    private static Vector2 cache = new Vector2();

    public static Vector2 transform(GameEntity entity) {
        TransformComponent transformComponent = entity.getTransform();
        cache.set(transformComponent.position.x - transformComponent.width / 2, transformComponent.position.y - transformComponent.height / 2);
        return cache;
    }
}
