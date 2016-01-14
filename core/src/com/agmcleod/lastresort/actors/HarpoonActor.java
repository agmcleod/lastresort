package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Harpoon;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
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
    private TextureRegion textureRegion;
    private World world;
    public HarpoonActor(World world, TextureRegion textureRegion, Harpoon harpoon) {
        this.harpoon = harpoon;
        this.textureRegion = textureRegion;
        TransformComponent transformComponent = harpoon.getTransform();
        this.setBounds(transformComponent.position.x, transformComponent.position.y, transformComponent.width, transformComponent.height);
        this.world = world;
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
                HarpoonComponent harpoonComponent = player.getHarpoonComponent();
                harpoonComponent.firing = false;
                PhysicsComponent physicsComponent = harpoonComponent.targetEntity.getComponent(PhysicsComponent.class);
                Body targetBody = physicsComponent.body;
                PolygonShape shape = new PolygonShape();
                Vector2 playerPosition = player.getTransform().position;
                Vector2 harpoonPosition = harpoonComponent.targetEntity.getTransform().position;
                float distance = harpoonPosition.dst(playerPosition);
                harpoonComponent.ropeWidth = 5;
                harpoonComponent.ropeHeight = distance;
                shape.setAsBox((harpoonComponent.ropeWidth * Game.WORLD_TO_BOX) / 2, (harpoonComponent.ropeHeight * Game.WORLD_TO_BOX) / 2);

                BodyDef def = new BodyDef();

                def.type = BodyDef.BodyType.DynamicBody;
                def.position.set(((harpoonPosition.x - playerPosition.x) / 2 + playerPosition.x) * Game.WORLD_TO_BOX,
                        ((harpoonPosition.y - playerPosition.y) / 2 + playerPosition.y) * Game.WORLD_TO_BOX);
                Body ropeBody = world.createBody(def);

                FixtureDef fixtureDef = new FixtureDef();
                fixtureDef.shape = shape;
                fixtureDef.density = 0.5f;
                fixtureDef.friction = 0f;
                fixtureDef.restitution = 0f;
                fixtureDef.filter.categoryBits = Game.PLAYER_MASK;
                fixtureDef.filter.maskBits = Game.OBJECT_MASK;
                fixtureDef.restitution = 0;
                ropeBody.createFixture(fixtureDef);
                player.setRopeBody(ropeBody);

                RevoluteJointDef jointDef = new RevoluteJointDef();
                jointDef.bodyA = player.getBody();
                jointDef.bodyB = ropeBody;
                jointDef.type = JointDef.JointType.RevoluteJoint;
                jointDef.localAnchorA.set(0, -0.25f);
                jointDef.localAnchorB.set(0, 1f);
                jointDef.enableLimit = true;
                jointDef.lowerAngle = 0;
                jointDef.upperAngle = 0;

                world.createJoint(jointDef);

                jointDef = new RevoluteJointDef();
                jointDef.bodyA = ropeBody;
                jointDef.bodyB = targetBody;
                jointDef.type = JointDef.JointType.RevoluteJoint;
                jointDef.localAnchorA.set(0, -1);
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
