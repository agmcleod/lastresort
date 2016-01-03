package com.agmcleod.lastresort.systems;

import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.entities.Player;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Aaron on 1/3/2016.
 */
public class HarpoonSystem extends EntitySystem {
    private ImmutableArray<Entity> entities;
    public HarpoonSystem() {}

    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(HarpoonComponent.class).get());
    }

    public void update(float dt) {
        for (int i = 0; i < entities.size(); i++) {
            Entity entity = entities.get(i);
            if (entity instanceof Player) {
                Player player = (Player) entity;
                HarpoonComponent harpoonComponent = player.getHarpoonComponent();
                if (harpoonComponent.fireTriggered) {
                    harpoonComponent.fireTriggered = false;
                    harpoonComponent.firing = true;

                    Vector2 targetPos = harpoonComponent.target;
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

                    HarpoonRotateToTargetComponent rotateToTarget = player.getHarpoon().getHarpoonRotateToTargetComponent();
                    rotateToTarget.angle = angle;
                    rotateToTarget.startRotate = true;
                }
            }
        }
    }
}
