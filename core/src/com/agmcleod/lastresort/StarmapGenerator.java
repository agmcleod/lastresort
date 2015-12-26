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
        int spaceSize = 1200;
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                String spriteName = "planet";

                if (MathUtils.random() > 0.5f) {
                    spriteName = "planet2";
                }

                int x = c * spaceSize - spaceSize / 2;
                int y = r * spaceSize - spaceSize / 2;

                x += MathUtils.random(-250, 250);
                y += MathUtils.random(-250, 250);

                Sprite sprite = atlas.createSprite(spriteName);
                Planet planet = new Planet(x, y, sprite, world);
                engine.addEntity(planet);
                PlanetActor planetActor = new PlanetActor(sprite, planet);
                stage.addActor(planetActor);
            }
        }
    }
}
