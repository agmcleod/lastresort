package com.agmcleod.lastresort.components;

import com.agmcleod.lastresort.Game;
import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Aaron on 12/20/2015.
 */
public class PhysicsComponent implements Component {
    public Body body;

    public PhysicsComponent(World world, Entity entity, BodyDef.BodyType bodyType) {
        TransformComponent transformComponent = ComponentMappers.transform.get(entity);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(transformComponent.width / 2 * Game.WORLD_TO_BOX, transformComponent.height / 2 * Game.WORLD_TO_BOX);

        setupBody(world, shape, bodyType, transformComponent);
    }

    public PhysicsComponent(World world, Entity entity, BodyDef.BodyType bodyType, Shape shape) {
        TransformComponent transformComponent = ComponentMappers.transform.get(entity);
        setupBody(world, shape, bodyType, transformComponent);
    }

    private void setupBody(World world, Shape shape, BodyDef.BodyType bodyType, TransformComponent transformComponent) {
        BodyDef def = new BodyDef();

        def.type = bodyType;
        def.position.set(transformComponent.position.x * Game.WORLD_TO_BOX, transformComponent.position.y * Game.WORLD_TO_BOX);

        body = world.createBody(def);
        body.setFixedRotation(true);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0f;
        fixtureDef.restitution = 0f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }
}
