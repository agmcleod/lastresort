package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.ComponentMappers;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Aaron on 12/20/2015.
 */
public class Player extends GameEntity {
    private boolean dead;
    private boolean rotateCounterClockwise;
    private boolean rotateClockwise;
    private boolean thrustForward;
    private boolean thrustBackward;
    public Player(Sprite sprite, World world) {
        this.name = "Player";
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.width = sprite.getWidth();
        transformComponent.height = sprite.getHeight();
        this.add(transformComponent);
        this.add(new PhysicsComponent(world, this, BodyDef.BodyType.DynamicBody));
        dead = false;
    }

    public TransformComponent getTransform() {
        return ComponentMappers.transform.get(this);
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public boolean isDirty() {
        return rotateClockwise || rotateCounterClockwise || thrustForward;
    }

    public boolean isRotateCounterClockwise() {
        return rotateCounterClockwise;
    }

    public boolean isRotateClockwise() {
        return rotateClockwise;
    }

    public boolean isThrustingBackward() {
        return thrustBackward;
    }

    public boolean isThrustingForward() {
        return thrustForward;
    }

    @Override
    public void collisionCallback(GameEntity gameEntity) {
        if (gameEntity instanceof EnemyOrb) {
            dead = true;
        }
    }

    public boolean setInputKeyState(int keycode, boolean state) {
        boolean updated = false;
        switch (keycode) {
            case Input.Keys.A:
                rotateCounterClockwise = state;
                updated = true;
                break;
            case Input.Keys.D:
                rotateClockwise = state;
                updated = true;
                break;
            case Input.Keys.SPACE:
                thrustForward = state;
                updated = true;
                break;
            case Input.Keys.CONTROL_LEFT:
                thrustBackward = state;
                updated = true;
                break;
        }

        return updated;
    }
}
