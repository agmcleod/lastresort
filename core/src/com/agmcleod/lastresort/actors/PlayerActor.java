package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Aaron on 12/20/2015.
 */
public class PlayerActor extends Group {
    private Player player;
    private Sprite sprite;
    public PlayerActor(Sprite sprite, final Player player) {
        this.sprite = sprite;
        this.player = player;

        TransformComponent transformComponent = player.getTransform();
        Vector2 position = EntityToScreenBridge.transform(player);
        this.setBounds(position.x, position.y, transformComponent.width, transformComponent.height);
        sprite.setPosition(getX(), getY());
        this.setOrigin(getWidth() / 2, getHeight() / 2);

        this.addListener(new InputListener() {
           @Override
            public boolean keyDown(InputEvent event, int keycode) {
               return player.setInputKeyState(keycode, true);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return player.setInputKeyState(keycode, false);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 position = EntityToScreenBridge.transform(player);
        this.setPosition(position.x, position.y);
        float rotation = MathUtils.radiansToDegrees * EntityToScreenBridge.getRotation(player);
        this.setRotation(rotation);
        sprite.setPosition(position.x, position.y);
        sprite.setRotation(rotation);
        sprite.draw(batch);
        super.draw(batch, parentAlpha);
    }
}
