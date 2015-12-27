package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.GameEntity;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Aaron on 12/21/2015.
 */
public class StillObjectActor extends Actor {
    private GameEntity gameEntity;
    private Sprite sprite;
    public StillObjectActor(Sprite sprite, GameEntity ge) {
        this.sprite = sprite;
        this.gameEntity = ge;
        TransformComponent transformComponent = ge.getTransform();
        Vector2 position = EntityToScreenBridge.transform(ge);

        this.setBounds(position.x, position.y, transformComponent.width, transformComponent.height);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Vector2 position = EntityToScreenBridge.transform(gameEntity);
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(MathUtils.radiansToDegrees * EntityToScreenBridge.getRotation(gameEntity));
        gameEntity.setDirty(false);
        sprite.draw(batch);
    }
}
