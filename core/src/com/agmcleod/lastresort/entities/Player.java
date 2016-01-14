package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.ComponentMappers;
import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Aaron on 12/20/2015.
 */
public class Player extends GameEntity {
    private boolean dead;
    private Harpoon harpoon;
    private boolean rotateCounterClockwise;
    private boolean rotateClockwise;
    private boolean thrustForward;
    private boolean thrustBackward;
    public Player(TextureRegion region, World world) {
        this.name = "Player";
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.width = region.getRegionWidth();
        transformComponent.height = region.getRegionHeight();
        this.add(transformComponent);
        this.add(new PhysicsComponent(world, this, BodyDef.BodyType.DynamicBody, Game.PLAYER_MASK, Game.OBJECT_MASK, 1));
        this.add(new HarpoonComponent());
        dead = false;
    }

    public Body getBody() {
        return ComponentMappers.physics.get(this).body;
    }

    public HarpoonComponent getHarpoonComponent() {
        return ComponentMappers.harpoon.get(this);
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

    public Harpoon getHarpoon() {
        return harpoon;
    }

    public void setHarpoon(Harpoon harpoon) {
        this.harpoon = harpoon;
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

    public void setRopeBody(Body body) {
        getHarpoonComponent().ropeBody = body;
    }
}
