package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

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
        PolygonShape shape = new PolygonShape();
        float box2dWidth = transformComponent.width * Game.WORLD_TO_BOX;
        float box2dHeight = transformComponent.height * Game.WORLD_TO_BOX;
        shape.set(new float[]{
                0, box2dHeight/2,
                -box2dWidth/2, -box2dHeight/3f,
                box2dWidth/2, -box2dHeight/3f
        });
        this.add(new PhysicsComponent(world, this, BodyDef.BodyType.DynamicBody, shape, Game.PLAYER_MASK, Game.OBJECT_MASK));
        this.add(new HarpoonComponent());
        this.add(new ScanMaterialsComponent());
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

    public ScanMaterialsComponent getScanMaterialsComponent() {
        return ComponentMappers.scanMaterials.get(this);
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
            case Input.Keys.G:
                getScanMaterialsComponent().scanTriggerd = state;
                updated = true;
                break;
        }

        return updated;
    }

    public void setRopeBody(Body body) {
        getHarpoonComponent().ropeBody = body;
    }
}
