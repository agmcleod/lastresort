package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.RecipeType;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class RecipeItemActor extends Actor {
    private TextureRegion textureRegion;
    private RecipeType recipeType;

    public RecipeItemActor(float x, float y, TextureRegion textureRegion, RecipeType recipeType) {
        this.textureRegion = textureRegion;
        setBounds(x - textureRegion.getRegionWidth() / 5, y, textureRegion.getRegionWidth(), textureRegion.getRegionHeight());
        setScale(0.5f, 0.5f);
        this.recipeType = recipeType;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(textureRegion, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), this.getRotation());
    }

    public RecipeType getRecipeType() {
        return recipeType;
    }
}