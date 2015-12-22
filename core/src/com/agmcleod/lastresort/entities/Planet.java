package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Aaron on 12/21/2015.
 */
public class Planet extends GameEntity {
    public Planet(float x, float y, World world) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.x = x;
        transformComponent.position.y = y;
        transformComponent.width = 150;
        transformComponent.height = 150;
        this.add(transformComponent);
        this.add(new PhysicsComponent(world, this, BodyDef.BodyType.StaticBody));
    }
}
