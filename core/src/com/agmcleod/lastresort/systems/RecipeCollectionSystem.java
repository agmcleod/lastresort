package com.agmcleod.lastresort.systems;

import com.agmcleod.lastresort.RecipeManager;
import com.agmcleod.lastresort.components.CollectableComponent;
import com.agmcleod.lastresort.entities.Material;
import com.agmcleod.lastresort.entities.Player;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;

/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class RecipeCollectionSystem extends EntitySystem {
    private Engine engine;
    private ImmutableArray<Entity> entities;
    private Player player;
    private RecipeManager recipeManager;

    public RecipeCollectionSystem(Engine engine, Player player, RecipeManager recipeManager) {
        this.player = player;
        this.engine = engine;
        this.recipeManager = recipeManager;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.all(CollectableComponent.class).get());
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < entities.size(); i++) {
            Material material = (Material) entities.get(i);
            if (material.getCollectableComponent().isCollected) {
                engine.removeEntity(material);
                recipeManager.consumeItem();
                if (recipeManager.recipeFinished()) {

                }
            }
        }
    }
}
