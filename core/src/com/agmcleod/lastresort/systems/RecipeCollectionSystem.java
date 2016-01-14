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
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class RecipeCollectionSystem extends EntitySystem {
    private Array<Body> bodyCleanup;
    private Engine engine;
    private ImmutableArray<Entity> entities;
    private Player player;
    private RecipeManager recipeManager;
    private World world;

    public RecipeCollectionSystem(Engine engine, Player player, RecipeManager recipeManager, World world, Array<Body> bodyCleanup) {
        this.player = player;
        this.engine = engine;
        this.recipeManager = recipeManager;
        this.world = world;
        this.bodyCleanup = bodyCleanup;
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
                bodyCleanup.add(material.getBody());
                bodyCleanup.add(player.getHarpoonComponent().ropeBody);
                player.getHarpoonComponent().cleanup();
                recipeManager.consumeItem();
                if (recipeManager.recipeFinished()) {
                    recipeManager.makeNewRecipe();
                    recipeManager.populateUi();
                }
            }
        }
    }
}
