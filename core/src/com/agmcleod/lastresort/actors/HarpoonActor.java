package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.entities.Harpoon;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by Aaron on 1/2/2016.
 */
public class HarpoonActor extends Actor {
    private Harpoon harpoon;
    private Sprite sprite;
    public HarpoonActor(Sprite sprite, Harpoon harpoon) {
        this.harpoon = harpoon;
        this.sprite = sprite;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Vector2 position = EntityToScreenBridge.transform(harpoon);
        sprite.setPosition(position.x, position.y);
        sprite.draw(batch);
    }
}
