package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.ComponentMappers;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Aaron on 12/20/2015.
 */
public class Player extends GameEntity {
    public Player(World world) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.x = Gdx.graphics.getWidth() / 2;
        transformComponent.position.y = Gdx.graphics.getHeight() / 2;
        transformComponent.width = 100;
        transformComponent.height = 100;
        this.add(transformComponent);
        this.add(new PhysicsComponent(world, this, BodyDef.BodyType.DynamicBody));
    }

    public TransformComponent getTransform() {
        return ComponentMappers.transform.get(this);
    }
}
