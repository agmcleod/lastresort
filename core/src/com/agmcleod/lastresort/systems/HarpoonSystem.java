package com.agmcleod.lastresort.systems;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.InstructionState;
import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.screens.PlayScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Logger;

/**
 * Created by Aaron on 1/3/2016.
 */
public class HarpoonSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private PlayScreen playScreen;
    private World world;
    public HarpoonSystem(PlayScreen playScreen, World world) {
        this.world = world;
        this.playScreen = playScreen;
    }

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(HarpoonComponent.class).get());
    }

    public void removeExistingHarpoon(HarpoonComponent harpoonComponent) {
        world.destroyBody(harpoonComponent.ropeBody);
        harpoonComponent.cleanup();
    }

    public void update(float dt) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Player) {
                Player player = (Player) entity;
                HarpoonComponent harpoonComponent = player.getHarpoonComponent();

                if (harpoonComponent.fireTriggered && harpoonComponent.ropeBody != null) {
                    removeExistingHarpoon(harpoonComponent);
                }

                if (harpoonComponent.fireTriggered || harpoonComponent.ropeBody != null) {
                    HarpoonRotateToTargetComponent rotateToTarget = player.getHarpoon().getHarpoonRotateToTargetComponent();
                    Vector2 targetPos = harpoonComponent.targetEntity.getTransform().position;
                    Vector2 playerPos = player.getTransform().position;
                    Vector2 harpoonPos = player.getHarpoon().getTransform().position;

                    float angle = MathUtils.atan2(targetPos.y - (playerPos.y + harpoonPos.y), targetPos.x - (playerPos. x + harpoonPos.x)) * (180 / MathUtils.PI);
                    if (angle < 0) {
                        angle += 360;
                    }
                    angle += 90;
                    if (angle > 360) {
                        angle -= 360;
                    }

                    rotateToTarget.angle = angle;

                    if (harpoonComponent.fireTriggered) {
                        harpoonComponent.fireTriggered = false;
                        harpoonComponent.firing = true;
                        rotateToTarget.startRotate = true;
                    } else if (harpoonComponent.ropeBody != null) {
                        rotateToTarget.rotateImmediately = true;
                    }
                } else if (harpoonComponent.queueFireTrigger) {
                    harpoonComponent.queueFireTrigger = false;
                    playScreen.nextInstruction(InstructionState.GRAB_OBJECT);

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

            }
        }
    }
}
