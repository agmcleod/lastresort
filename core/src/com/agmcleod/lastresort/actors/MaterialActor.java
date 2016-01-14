package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.RecipeType;
import com.agmcleod.lastresort.entities.GameEntity;
import com.agmcleod.lastresort.entities.Material;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class MaterialActor extends StillObjectActor {
    private Material material;

    public MaterialActor(Material material, Sprite sprite, GameEntity ge) {
        super(sprite, ge);
        this.material = material;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        super.draw(batch, alpha);
        if (material.getCollectableComponent().isCollected) {
            remove();
        }
    }

    public RecipeType getRecipeType() {
        return material.getRecipeType();
    }
}
