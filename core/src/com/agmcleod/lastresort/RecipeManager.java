package com.agmcleod.lastresort;

import com.badlogic.gdx.utils.Array;
/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class RecipeManager {
    private Array<RecipeType> recipes;

    public RecipeManager() {
        recipes = new Array<RecipeType>();
        recipes.add(RecipeType.ORB);
        recipes.add(RecipeType.ORB);
    }

    public void consumeItem() {
        recipes.removeIndex(0);
    }

    public boolean recipeFinished() {
        return recipes.size == 0;
    }

    public Array<RecipeType> getRecipes() {
        return recipes;
    }
}
