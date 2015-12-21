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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

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
        Vector2 position = EntityToScreenBridge.transform(player);
        this.setBounds(position.x, position.y, transformComponent.width, transformComponent.height);
        sprite.setPosition(getX(), getY());

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
        // TODO: setting from body feels pointless, find a way to improve this
        this.setPosition(position.x, position.y);
        this.setRotation(MathUtils.radiansToDegrees * EntityToScreenBridge.getRotation(player));
        sprite.setPosition(getX(), getY());
        sprite.setRotation(getRotation());
        player.setDirty(false);
        sprite.draw(batch);
    }
}
