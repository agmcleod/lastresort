package com.agmcleod.lastresort.systems;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.ComponentMappers;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Player;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Aaron on 12/20/2015.
 */
public class MovementSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private Vector2 forceVector;

    public MovementSystem() {
        forceVector = new Vector2();
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(TransformComponent.class, PhysicsComponent.class).get());
    }

    public void update(float dt) {
        for (int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            TransformComponent transformComponent = ComponentMappers.transform.get(entity);
            PhysicsComponent physicsComponent = ComponentMappers.physics.get(entity);

            if (entity instanceof Player) {
                Player player = (Player) entity;
                Body body = physicsComponent.body;

                if (player.isRotateClockwise()) {
                    body.setAngularVelocity(-5);
                } else if (player.isRotateCounterClockwise()) {
                    body.setAngularVelocity(5);
                } else {
                    body.setAngularVelocity(0);
                }

                if (player.isThrustingForward() || player.isThrustingBackward()) {
                    if (player.isThrustingBackward()) {
                        forceVector.set(0, -0.3f);
                    } else if (player.isThrustingForward()) {
                        forceVector.set(0, 0.5f);
                    }
                    forceVector.rotate(body.getAngle() * MathUtils.radiansToDegrees);
                    body.applyForceToCenter(forceVector, true);
                }
            }

            Body body = physicsComponent.body;
            transformComponent.position.x = body.getPosition().x * Game.BOX_TO_WORLD;
            transformComponent.position.y = body.getPosition().y * Game.BOX_TO_WORLD;
        }
    }
}
