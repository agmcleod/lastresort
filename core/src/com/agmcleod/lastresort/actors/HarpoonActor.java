package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.InstructionState;
import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Harpoon;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.agmcleod.lastresort.screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Created by Aaron on 1/2/2016.
 */
public class HarpoonActor extends Actor {
    private Harpoon harpoon;
    private PlayScreen playScreen;
    private TextureRegion textureRegion;
    private World world;
    public HarpoonActor(PlayScreen playScreen, World world, TextureRegion textureRegion, Harpoon harpoon) {
        this.harpoon = harpoon;
        this.textureRegion = textureRegion;
        TransformComponent transformComponent = harpoon.getTransform();
        this.setBounds(transformComponent.position.x, transformComponent.position.y, transformComponent.width, transformComponent.height);
        this.world = world;
        this.playScreen = playScreen;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 position = EntityToScreenBridge.transform(harpoon);
        this.setPosition(position.x, position.y);
        HarpoonRotateToTargetComponent harpoonRotateToTargetComponent = harpoon.getHarpoonRotateToTargetComponent();
        if (harpoonRotateToTargetComponent.startRotate) {
            rotateHarpoonToTarget(harpoonRotateToTargetComponent);
        } else if (harpoonRotateToTargetComponent.rotateImmediately) {
            harpoonRotateToTargetComponent.rotateImmediately = false;
            this.setRotation(getAbsoluteAngle(harpoonRotateToTargetComponent));
        }
        batch.draw(textureRegion, getX(), getY(), getWidth() / 2, getHeight() - 5, getWidth(), getHeight(), 1, 1, this.getRotation());
    }

    public float getAbsoluteAngle(HarpoonRotateToTargetComponent harpoonRotateToTargetComponent) {
        Player player = (Player) harpoon.getParent();
        float angle = harpoonRotateToTargetComponent.angle - MathUtils.radiansToDegrees * EntityToScreenBridge.getRotation(player);
        if (angle < 0) {
            angle += 360f;
        }

        return angle;
    }

    private void rotateHarpoonToTarget(HarpoonRotateToTargetComponent harpoonRotateToTargetComponent) {
        final Player player = (Player) harpoon.getParent();
        harpoonRotateToTargetComponent.startRotate = false;

        addAction((sequence(Actions.rotateTo(getAbsoluteAngle(harpoonRotateToTargetComponent), 0.5f), new RunnableAction() {
            @Override
            public void run() {
                playScreen.nextInstruction(InstructionState.GRAB_OBJECT);

                HarpoonComponent harpoonComponent = player.getHarpoonComponent();
                harpoonComponent.firing = false;
                PhysicsComponent physicsComponent = harpoonComponent.targetEntity.getComponent(PhysicsComponent.class);
                Body targetBody = physicsComponent.body;
                targetBody.setType(BodyDef.BodyType.DynamicBody);
                PolygonShape shape = new PolygonShape();
                Vector2 playerPosition = player.getTransform().position;
                Vector2 targetPosition = harpoonComponent.targetEntity.getTransform().position;
                float length = targetPosition.dst(playerPosition);
                harpoonComponent.ropeWidth = 5;
                harpoonComponent.ropeHeight = length;

                final float halfWidth = 2.5f * Game.WORLD_TO_BOX;
                final float halfHeight = length / 2 * Game.WORLD_TO_BOX;

                float[] vertices = new float[]{
                        -halfWidth, -halfHeight, halfWidth, -halfHeight,
                        -halfWidth, halfHeight, halfWidth, halfHeight,
                };

                // shape.setAsBox((harpoonComponent.ropeWidth * Game.WORLD_TO_BOX) / 2, (harpoonComponent.ropeHeight * Game.WORLD_TO_BOX) / 2);
                shape.set(vertices);

                BodyDef def = new BodyDef();

                def.type = BodyDef.BodyType.DynamicBody;
                def.position.set(((targetPosition.x - playerPosition.x) / 2 + playerPosition.x) * Game.WORLD_TO_BOX,
                        ((targetPosition.y - playerPosition.y) / 2 + playerPosition.y) * Game.WORLD_TO_BOX);
                Body ropeBody = world.createBody(def);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 0f;
                fixtureDef.filter.categoryBits = Game.PLAYER_MASK;
                fixtureDef.filter.maskBits = 0;
                fixtureDef.restitution = 0;
                ropeBody.createFixture(fixtureDef);
                player.setRopeBody(ropeBody);

                RevoluteJointDef jointDef = new RevoluteJointDef();
                jointDef.bodyA = player.getBody();
                jointDef.bodyB = ropeBody;
                jointDef.type = JointDef.JointType.RevoluteJoint;
                jointDef.localAnchorA.set(0, -0.1f);
                jointDef.localAnchorB.set(0, halfHeight);
                jointDef.enableLimit = true;
                jointDef.lowerAngle = 0;
                jointDef.upperAngle = 0;

                world.createJoint(jointDef);

                jointDef = new RevoluteJointDef();
                jointDef.bodyA = ropeBody;
                jointDef.bodyB = targetBody;
                jointDef.type = JointDef.JointType.RevoluteJoint;
                jointDef.localAnchorA.set(0, -halfHeight);
                jointDef.enableLimit = true;
                jointDef.lowerAngle = 0;
                jointDef.upperAngle = 0;

                world.createJoint(jointDef);
                shape.dispose();
            }
        })
        ));
    }
}
