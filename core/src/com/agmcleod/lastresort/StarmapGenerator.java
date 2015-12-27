package com.agmcleod.lastresort;

import com.agmcleod.lastresort.actors.StillObjectActor;
import com.agmcleod.lastresort.entities.EnemyOrb;
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
    private static final int SPACE_SIZE = 1200;

    public StarmapGenerator() {
    }

    public static void buildMap(World world, Engine engine, Stage stage, TextureAtlas atlas) {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                String spriteName = "planet";

                if (MathUtils.random() > 0.5f) {
                    spriteName = "planet2";
                }

                int x = c * SPACE_SIZE - SPACE_SIZE / 2;
                int y = r * SPACE_SIZE - SPACE_SIZE / 2;

                x += MathUtils.random(-250, 250);
                y += MathUtils.random(-250, 250);

                Sprite sprite = atlas.createSprite(spriteName);
                Planet planet = new Planet(x, y, sprite, world);
                engine.addEntity(planet);
                StillObjectActor stillObjectActor = new StillObjectActor(sprite, planet);
                stage.addActor(stillObjectActor);
            }
        }
    }

    public static void placeMines(World world, Engine engine, Stage stage, TextureAtlas atlas) {
        for (int r = 0; r < 10; r++) {
            for (int c = 0; c < 10; c++) {
                Sprite sprite = atlas.createSprite("mine");

                int x = c * SPACE_SIZE;
                int y = r * SPACE_SIZE;

                x += MathUtils.random(-20, 20);
                y += MathUtils.random(-20, 20);

                EnemyOrb mine = new EnemyOrb(x, y, sprite, world);
                engine.addEntity(mine);
                StillObjectActor stillObjectActor = new StillObjectActor(sprite, mine);
                stage.addActor(stillObjectActor);
            }
        }
    }
}
