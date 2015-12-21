package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.helpers.EntityToScreenCoordinates;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Aaron on 12/20/2015.
 */
public class PlayerActor extends Actor {
    private Player player;
    private Sprite sprite;
    public PlayerActor(TextureAtlas atlas, final Player player) {
        sprite = atlas.createSprite("ship");
        this.player = player;

        TransformComponent transformComponent = player.getTransform();
        Vector2 position = EntityToScreenCoordinates.transform(player);
        this.setBounds(position.x, position.y, transformComponent.width, transformComponent.height);
        sprite.setPosition(getX(), getY());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (player.isDirty()) {
            Vector2 position = EntityToScreenCoordinates.transform(player);
            this.setPosition(position.x, position.y);
            sprite.setPosition(getX(), getY());
            player.setDirty(false);
        }
        sprite.draw(batch);
    }
}
