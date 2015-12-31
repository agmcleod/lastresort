package com.agmcleod.lastresort;

import com.agmcleod.lastresort.entities.GameEntity;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaronmcleod on 2015-12-29.
 */
public class CollisionListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();
        Object objectA = fixtureA.getBody().getUserData();
        Object objectB = fixtureB.getBody().getUserData();
        if (objectA != null && objectB != null) {
            GameEntity entityA = (GameEntity) fixtureA.getBody().getUserData();
            GameEntity entityB = (GameEntity) fixtureB.getBody().getUserData();
            entityA.collisionCallback(entityB);
            entityB.collisionCallback(entityA);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
