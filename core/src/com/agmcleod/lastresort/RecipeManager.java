package com.agmcleod.lastresort;

import com.agmcleod.lastresort.actors.RecipeGroup;
import com.agmcleod.lastresort.actors.RecipeItemActor;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class RecipeManager {
    private Array<RecipeType> recipes;
    private RecipeGroup recipeGroup;

    public RecipeManager() {
        recipes = new Array<RecipeType>();
        recipes.add(RecipeType.ORB);
        recipes.add(RecipeType.ORB);
    }

    public void consumeItem() {
        RecipeType type = recipes.removeIndex(0);
        SnapshotArray<Actor> actors = recipeGroup.getChildren();
        for (int i = actors.size - 1; i >= 0; i--) {
            Actor actor = actors.get(i);
            if (actor instanceof RecipeItemActor && ((RecipeItemActor) actor).getRecipeType().equals(type)) {
                actor.remove();
                break;
            }
        }
    }

    public boolean recipeFinished() {
        return recipes.size == 0;
    }

    public void setRecipeGroup(RecipeGroup recipeGroup) {
        this.recipeGroup = recipeGroup;
    }

    public Array<RecipeType> getRecipes() {
        return recipes;
    }
}
