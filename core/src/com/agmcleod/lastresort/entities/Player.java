package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.ComponentMappers;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Aaron on 12/20/2015.
 */
public class Player extends GameEntity {
    private boolean rotateCounterClockwise;
    private boolean rotateClockwise;
    private boolean thrustForward;
    private boolean thrustBackward;
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
