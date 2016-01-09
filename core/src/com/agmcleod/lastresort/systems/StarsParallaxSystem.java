package com.agmcleod.lastresort.systems;

import com.agmcleod.lastresort.components.StarsComponent;
import com.agmcleod.lastresort.entities.GameEntity;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by aaronmcleod on 2016-01-09.
 */
public class StarsParallaxSystem extends EntitySystem {
    private final int PARALLAX_FACTOR = 2;
    private ImmutableArray<Entity> entities;
    private Camera camera;

    public StarsParallaxSystem(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(StarsComponent.class).get());
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < entities.size(); i++) {
            GameEntity entity = (GameEntity) entities.get(i);
            entity.getTransform().position.set(
                    camera.position.x / PARALLAX_FACTOR,
                    camera.position.y / PARALLAX_FACTOR
            );
            Vector2 position = entity.getTransform().position;
        }
    }
}
