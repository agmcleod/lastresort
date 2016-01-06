package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.entities.Harpoon;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
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
        this.setBounds(0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
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
            this.setRotation(harpoonRotateToTargetComponent.angle);
        }

        batch.draw(textureRegion, getX(), getY(), getWidth() / 2, getHeight() - 5, getWidth(), getHeight(), 1, 1, this.getRotation());
    }

    private void rotateHarpoonToTarget(HarpoonRotateToTargetComponent harpoonRotateToTargetComponent) {
        final Player player = (Player) harpoon.getParent();
        harpoonRotateToTargetComponent.startRotate = false;
        float angle = harpoonRotateToTargetComponent.angle - MathUtils.radiansToDegrees * EntityToScreenBridge.getRotation(player);
        if (angle < 0) {
            angle += 360f;
        }
        addAction((sequence(Actions.rotateTo(angle, 0.5f), new RunnableAction() {
            @Override
            public void run() {
                HarpoonComponent harpoonComponent = player.getHarpoonComponent();
                harpoonComponent.firing = false;
                PhysicsComponent physicsComponent = harpoonComponent.targetEntity.getComponent(PhysicsComponent.class);
                Body body = physicsComponent.body;
                DistanceJointDef jointDef = new DistanceJointDef();
                jointDef.bodyA = body;
                jointDef.bodyB = player.getBody();
                jointDef.type = JointDef.JointType.DistanceJoint;
                jointDef.localAnchorB.set(0, -0.25f);
                jointDef.length = harpoonComponent.target.dst(player.getTransform().position) * Game.WORLD_TO_BOX;

                player.setJoint(world.createJoint(jointDef));
            }
        })
        ));
    }
}
