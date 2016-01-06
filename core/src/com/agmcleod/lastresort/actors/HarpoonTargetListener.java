package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.entities.GameEntity;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by aaronmcleod on 2016-01-04.
 */
public class HarpoonTargetListener extends InputListener {
    private StillObjectActor actor;
    private Player player;
    private World world;

    public HarpoonTargetListener(World world, StillObjectActor actor, Player player) {
        this.actor = actor;
        this.player = player;
        this.world = world;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        GameEntity entity = actor.getGameEntity();

        HarpoonComponent harpoonComponent = player.getHarpoonComponent();
        if (!harpoonComponent.fireTriggered && !harpoonComponent.firing) {
            harpoonComponent.fireTriggered = true;
            harpoonComponent.target.set(x, y);
        }

        PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
        Body body = physicsComponent.body;
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.bodyA = body;
        jointDef.bodyB = player.getBody();
        jointDef.type = JointDef.JointType.DistanceJoint;
        jointDef.length = harpoonComponent.target.dst(EntityToScreenBridge.transform(player)) * Game.WORLD_TO_BOX;

        player.setJoint(world.createJoint(jointDef));

        return true;
    }
}
