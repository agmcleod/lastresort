package com.agmcleod.lastresort.systems;

import com.agmcleod.lastresort.InstructionState;
import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.screens.PlayScreen;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Logger;

/**
 * Created by Aaron on 1/3/2016.
 */
public class HarpoonSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    private World world;
    public HarpoonSystem(World world) {
        this.world = world;
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
                }

            }
        }
    }
}
