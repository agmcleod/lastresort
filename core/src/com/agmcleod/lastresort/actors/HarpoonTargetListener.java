package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.entities.Player;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by aaronmcleod on 2016-01-04.
 */
public class HarpoonTargetListener extends InputListener {
    private StillObjectActor actor;
    private Player player;

    public HarpoonTargetListener(StillObjectActor actor, Player player) {
        this.actor = actor;
        this.player = player;
    }

    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        HarpoonComponent harpoonComponent = player.getHarpoonComponent();
        if (!harpoonComponent.fireTriggered && !harpoonComponent.firing) {
            harpoonComponent.targetEntity = actor.getGameEntity();
            harpoonComponent.fireTriggered = true;
            harpoonComponent.target.set(x, y);
        }

        return true;
    }
}
