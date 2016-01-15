package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.components.HarpoonComponent;
import com.agmcleod.lastresort.entities.Player;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by aaronmcleod on 2016-01-14.
 */
public class RopeActor extends Actor {
    private Player player;
    private TextureRegion textureRegion;
    public RopeActor(Player player, TextureRegion textureRegion) {
        this.player = player;
        this.setBounds(0, 0, 100, 100);
        this.textureRegion = textureRegion;
    }


    public void draw(Batch batch, float parentAlpha) {
        HarpoonComponent harpoonComponent = player.getHarpoonComponent();
        if (harpoonComponent.ropeBody != null) {
            Vector2 position = harpoonComponent.ropeBody.getPosition();
            float width = harpoonComponent.ropeWidth;
            float height = harpoonComponent.ropeHeight;
            setBounds(position.x * Game.BOX_TO_WORLD - width / 2, position.y * Game.BOX_TO_WORLD - height / 2, width, height);
            setRotation(harpoonComponent.ropeBody.getAngle() * MathUtils.radiansToDegrees);
            batch.draw(textureRegion, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1, 1, getRotation());
        }
    }
}
