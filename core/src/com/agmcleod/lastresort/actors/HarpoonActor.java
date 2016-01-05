package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.components.HarpoonRotateToTargetComponent;
import com.agmcleod.lastresort.entities.Harpoon;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
    public HarpoonActor(TextureRegion textureRegion, Harpoon harpoon) {
        this.harpoon = harpoon;
        this.textureRegion = textureRegion;
        this.setBounds(0, 0, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 position = EntityToScreenBridge.transform(harpoon);
        this.setPosition(position.x, position.y);
        HarpoonRotateToTargetComponent harpoonRotateToTargetComponent = harpoon.getHarpoonRotateToTargetComponent();
        if (harpoonRotateToTargetComponent.startRotate) {
            harpoonRotateToTargetComponent.startRotate = false;
            final Player player = (Player) harpoon.getParent();
            float angle = harpoonRotateToTargetComponent.angle - MathUtils.radiansToDegrees * EntityToScreenBridge.getRotation(player);
            if (angle < 0) {
                angle += 360f;
            }
            System.out.println(angle + " " + harpoonRotateToTargetComponent.angle);
            addAction((sequence(Actions.rotateTo(angle, 0.5f), new RunnableAction() {
                @Override
                public void run() {
                    HarpoonComponent harpoonComponent = player.getHarpoonComponent();
                    harpoonComponent.firing = false;
                }
            })
            ));
        }
        batch.draw(textureRegion, getX(), getY(), getWidth() / 2, getHeight() - 5, getWidth(), getHeight(), 1, 1, this.getRotation());
    }
}
