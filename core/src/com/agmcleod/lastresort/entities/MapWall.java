package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by aaronmcleod on 2015-12-31.
 */
public class MapWall extends GameEntity {
    public MapWall(float x, float y, float w, float h, World world) {
        this.name = "wall";

        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.x = x;
        transformComponent.position.y = y;
        transformComponent.width = w;
        transformComponent.height = h;

        this.add(transformComponent);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w / 2 * Game.WORLD_TO_BOX, h / 2 * Game.WORLD_TO_BOX);

        PhysicsComponent physicsComponent = new PhysicsComponent(world, this, BodyDef.BodyType.StaticBody, shape);
        this.add(physicsComponent);
    }
}
