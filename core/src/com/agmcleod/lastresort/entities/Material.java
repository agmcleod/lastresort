package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.Game;
import com.agmcleod.lastresort.RecipeType;
import com.agmcleod.lastresort.components.CollectableComponent;
import com.agmcleod.lastresort.components.ComponentMappers;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by aaronmcleod on 2015-12-29.
 */
public class Material extends GameEntity {
    private RecipeType recipeType;
    public Material(float x, float y, RecipeType recipeType, Sprite sprite, World world) {
        this.recipeType = recipeType;
        this.name = "Material";
        TransformComponent transformComponent = new TransformComponent();
        transformComponent.position.x = x;
        transformComponent.position.y = y;
        transformComponent.width = sprite.getWidth();
        transformComponent.height = sprite.getHeight();

        this.add(transformComponent);
        CircleShape shape = new CircleShape();
        shape.setRadius(transformComponent.width / 2 * Game.WORLD_TO_BOX);
        PhysicsComponent physicsComponent = new PhysicsComponent(world, this, BodyDef.BodyType.DynamicBody, shape);
        this.add(physicsComponent);
        this.add(new CollectableComponent());
    }

    public CollectableComponent getCollectableComponent() {
        return ComponentMappers.collectable.get(this);
    }

    public RecipeType getRecipeType() {
        return recipeType;
    }
}
