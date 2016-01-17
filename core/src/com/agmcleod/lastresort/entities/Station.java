package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.InstructionState;
import com.agmcleod.lastresort.RecipeManager;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.screens.PlayScreen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by aaronmcleod on 2016-01-10.
 */
public class Station extends GameEntity {
    private RecipeManager recipeManager;
    private PlayScreen playScreen;
    public Station(PlayScreen playScreen, TextureRegion textureRegion, World world, RecipeManager recipeManager) {
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.set(150, 150);
        transformComponent.width = textureRegion.getRegionWidth();
        transformComponent.height = textureRegion.getRegionHeight();

        this.add(transformComponent);

        PhysicsComponent physicsComponent = new PhysicsComponent(world, this, BodyDef.BodyType.StaticBody, Game.STATION_MASK, Game.OBJECT_MASK);
        this.add(physicsComponent);
        this.recipeManager = recipeManager;
        this.playScreen = playScreen;
    }

    @Override
    public void collisionCallback(GameEntity ge) {
        if (ge instanceof Material) {
            Material material = (Material) ge;
            if (recipeManager.getRecipes().contains(material.getRecipeType(), false)) {
                playScreen.nextInstruction(InstructionState.TAKE_TO_STATION);
                material.getCollectableComponent().isCollected = true;
            }
        }
    }
}
