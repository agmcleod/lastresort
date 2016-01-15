package com.agmcleod.lastresort;

import com.agmcleod.lastresort.actors.RecipeGroup;
import com.agmcleod.lastresort.actors.RecipeItemActor;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;

import java.util.Iterator;

/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class RecipeManager {
    private TextureAtlas atlas;
    private Array<RecipeType> recipes;
    private Array<RecipeType> availableRecipies;
    private RecipeGroup recipeGroup;

    public RecipeManager(TextureAtlas atlas) {
        recipes = new Array<RecipeType>();
        availableRecipies = new Array<RecipeType>();
        this.atlas = atlas;
    }

    public void addAvailableRecipeType(RecipeType rt) {
        availableRecipies.add(rt);
    }

    public boolean availableRecipiesFinished() {
        return availableRecipies.size == 0;
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

        for (int i = availableRecipies.size - 1; i >= 0; i--) {
            RecipeType availableRecipeType = availableRecipies.get(i);
            if (availableRecipeType == type) {
                availableRecipies.removeIndex(i);
                break;
            }
        }
    }

    public void makeNewRecipe() {
        int len = Math.min(3, availableRecipies.size);
        for (int i = 0; i < len; i++) {
            recipes.add(availableRecipies.get(i));
        }
    }

    public void populateUi() {
        Iterator<RecipeType> it = recipes.iterator();
        int index = 0;
        while (it.hasNext()) {
            RecipeType type = it.next();
            TextureAtlas.AtlasRegion region = null;
            switch (type) {
                case ORB:
                    region = atlas.findRegion("orb");
                    break;
                case PART:
                    region = atlas.findRegion("part");
                    break;
                case ROCK:
                    region = atlas.findRegion("rock");
                    break;
                default:
                    break;
            }
            if (region != null) {
                RecipeItemActor ria = new RecipeItemActor(index * 60 + 35, 50, region, type);
                recipeGroup.addActor(ria);
            }

            index++;
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
