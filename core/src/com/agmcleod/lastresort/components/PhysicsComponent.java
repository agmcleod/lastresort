package com.agmcleod.lastresort.components;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.entities.GameEntity;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by Aaron on 12/20/2015.
 */
public class PhysicsComponent implements Component {
    public Body body;

    public PhysicsComponent(World world, GameEntity entity, BodyDef.BodyType bodyType) {
        TransformComponent transformComponent = ComponentMappers.transform.get(entity);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(transformComponent.width / 2 * Game.WORLD_TO_BOX, transformComponent.height / 2 * Game.WORLD_TO_BOX);

        setupBody(world, entity, shape, bodyType, transformComponent, Game.OBJECT_MASK, Game.STATION_MASK | Game.PLAYER_MASK);
    }

    public PhysicsComponent(World world, GameEntity entity, BodyDef.BodyType bodyType, Shape shape) {
        TransformComponent transformComponent = ComponentMappers.transform.get(entity);
        setupBody(world, entity, shape, bodyType, transformComponent, Game.OBJECT_MASK, Game.STATION_MASK | Game.PLAYER_MASK);
    }

    public PhysicsComponent(World world, GameEntity entity, BodyDef.BodyType bodyType, int categoryBits, int maskBits) {
        TransformComponent transformComponent = ComponentMappers.transform.get(entity);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(transformComponent.width / 2 * Game.WORLD_TO_BOX, transformComponent.height / 2 * Game.WORLD_TO_BOX);
        setupBody(world, entity, shape, bodyType, transformComponent, categoryBits, maskBits);
    }

    private void setupBody(World world, GameEntity entity, Shape shape, BodyDef.BodyType bodyType, TransformComponent transformComponent, int categoryBits, int maskBits) {
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
        fixtureDef.filter.categoryBits = (short) categoryBits;
        fixtureDef.filter.maskBits = (short) maskBits;
        body.createFixture(fixtureDef);
        body.setUserData(entity);

        shape.dispose();
    }
}
