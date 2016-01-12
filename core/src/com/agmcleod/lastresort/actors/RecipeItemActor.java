package com.agmcleod.lastresort.actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class RecipeItemActor extends Actor {
    private TextureRegion textureRegion;

    public RecipeItemActor(float x, float y, TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        setBounds(x - textureRegion.getRegionWidth() / 5, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        setScale(0.5f, 0.5f);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), this.getRotation());
    }
}