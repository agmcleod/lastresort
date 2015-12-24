package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Aaron on 12/21/2015.
 */
public class Planet extends GameEntity {
    public Planet(float x, float y, Sprite sprite, World world) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.x = x;
        transformComponent.position.y = y;
        transformComponent.width = sprite.getWidth();
        transformComponent.height = sprite.getHeight();
        this.add(transformComponent);
        CircleShape shape = new CircleShape();
        shape.setRadius(transformComponent.width / 2 * Game.WORLD_TO_BOX);
        this.add(new PhysicsComponent(world, this, BodyDef.BodyType.StaticBody, shape));
    }
}
