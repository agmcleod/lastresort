package com.agmcleod.lastresort;

import com.agmcleod.lastresort.actors.PlanetActor;
import com.agmcleod.lastresort.entities.Planet;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Created by aaronmcleod on 2015-12-25.
 */
public class StarmapGenerator {
    public StarmapGenerator() {
    }

    public static void buildMap(World world, Engine engine, Stage stage, TextureAtlas atlas) {
        float startX = 300;
        float startY = 300;
        for (int i = 0; i < 10; i++) {
            float xMod = 1;
            float yMod = 1;

            if (i > 1) {
                xMod *= MathUtils.random(2f, 5f);
                yMod *= MathUtils.random(2f, 5f);
            }

            if (MathUtils.random() > 0.5f) {
                yMod *= -1;
            }

            String spriteName = "planet";

            if (MathUtils.random() > 0.5f) {
                spriteName = "planet2";
            }

            if (i >= 5) {
                xMod *= -1;
            }

            System.out.println(xMod + "," + yMod);

            Sprite sprite = atlas.createSprite(spriteName);
            Planet planet = new Planet(startX + startX * xMod, startY + startY * yMod, sprite, world);
            engine.addEntity(planet);
            PlanetActor planetActor = new PlanetActor(sprite, planet);
            stage.addActor(planetActor);
        }
    }
}
