package com.agmcleod.lastresort;

import com.agmcleod.lastresort.actors.StillObjectActor;
import com.agmcleod.lastresort.components.PhysicsComponent;
import com.agmcleod.lastresort.entities.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

/**
 * Created by aaronmcleod on 2015-12-25.
 */
public class StarmapGenerator {
    public static final int SPACE_SIZE = 1200;
    public static final int ROW_COUNT = 10;
    public static final int COL_COUNT = 10;
    public static final int MAP_HEIGHT = SPACE_SIZE * ROW_COUNT;
    public static final int MAP_WIDTH = SPACE_SIZE * COL_COUNT;
    private ObjectMap<Integer, Array<Integer>> consumedCoords;

    public StarmapGenerator() {
        consumedCoords = new ObjectMap<Integer, Array<Integer>>();
    }

    public void buildBorders(Engine engine, World world) {
        final int WALL_DEPTH = 20;
        MapWall leftWall = new MapWall(-WALL_DEPTH / 2 - (MAP_WIDTH / 2), 0, WALL_DEPTH, MAP_HEIGHT + WALL_DEPTH * 2, world);
        engine.addEntity(leftWall);

        MapWall bottomWall = new MapWall(0, -WALL_DEPTH / 2 - (MAP_HEIGHT / 2), MAP_WIDTH, WALL_DEPTH, world);
        engine.addEntity(bottomWall);

        MapWall rightWall = new MapWall(WALL_DEPTH / 2 + (MAP_WIDTH / 2), 0, WALL_DEPTH, MAP_HEIGHT + WALL_DEPTH * 2, world);
        engine.addEntity(rightWall);

        MapWall topWall = new MapWall(0, WALL_DEPTH / 2 + (MAP_HEIGHT / 2), MAP_WIDTH, WALL_DEPTH, world);
        engine.addEntity(topWall);
    }

    public void buildMap(World world, Engine engine, Stage stage, TextureAtlas atlas) {
        for (int r = 0; r < ROW_COUNT; r++) {
            for (int c = 0; c < COL_COUNT; c++) {
                // 20 percent chance there is no planet
                if (MathUtils.random() > 0.2f) {
                    String spriteName = "planet";

                    if (MathUtils.random() > 0.5f) {
                        spriteName = "planet2";
                    }

                    int x = (c * SPACE_SIZE + SPACE_SIZE / 2) - MAP_WIDTH / 2;
                    int y = (r * SPACE_SIZE + SPACE_SIZE / 2) - MAP_HEIGHT / 2;

                    addConsumedCoords(c, r);

                    x += MathUtils.random(-250, 250);
                    y += MathUtils.random(-250, 250);

                    Sprite sprite = atlas.createSprite(spriteName);
                    Planet planet = new Planet(x, y, sprite, world);
                    engine.addEntity(planet);
                    createStillActor(sprite, planet, stage);
                }
            }
        }
    }

    public void collectObjects(World world, Engine engine, Stage stage, TextureAtlas atlas) {
        for (int i = 0; i < 5; i++) {
            int x;
            int y;

            do {
                x = MathUtils.random(0, COL_COUNT - 1);
                y = MathUtils.random(0, ROW_COUNT - 1);
            } while (consumedCoords.containsKey(x) && consumedCoords.get(x).contains(y, false));

            addConsumedCoords(x, y);

            x = (x * SPACE_SIZE + SPACE_SIZE / 2) - MAP_WIDTH / 2;
            y = (y * SPACE_SIZE + SPACE_SIZE / 2) - MAP_HEIGHT / 2;

            Sprite sprite = atlas.createSprite("orb");
            Material material = new Material(x, y, sprite, world);
            engine.addEntity(material);
            final StillObjectActor actor = createStillActor(sprite, material, stage);
            actor.addListener(new InputListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    GameEntity entity = actor.getGameEntity();
                    PhysicsComponent physicsComponent = entity.getComponent(PhysicsComponent.class);
                    Body body = physicsComponent.body;
                    return true;
                }
            });
        }
    }

    public void placeMines(World world, Engine engine, Stage stage, TextureAtlas atlas) {
        for (int r = 1; r < ROW_COUNT; r++) {
            for (int c = 1; c < COL_COUNT; c++) {
                // dont place a mine in the start spot.
                if (r == 5 && c == 5) {
                    continue;
                }
                Sprite sprite = atlas.createSprite("mine");

                int x = c * SPACE_SIZE;
                int y = r * SPACE_SIZE;

                x += MathUtils.random(-20, 20);
                y += MathUtils.random(-20, 20);
                x -= MAP_WIDTH / 2;
                y -= MAP_HEIGHT / 2;

                EnemyOrb mine = new EnemyOrb(x, y, sprite, world);
                engine.addEntity(mine);
                createStillActor(sprite, mine, stage);
            }
        }
    }

    private void addConsumedCoords(int c, int r) {
        Array<Integer> rowValues;

        if (consumedCoords.containsKey(c)) {
            rowValues = consumedCoords.get(c);
        } else {
            rowValues = new Array<Integer>();
            consumedCoords.put(c, rowValues);
        }

        rowValues.add(r);
    }

    private StillObjectActor createStillActor(Sprite sprite, GameEntity entity, Stage stage) {
        StillObjectActor stillObjectActor = new StillObjectActor(sprite, entity);
        stage.addActor(stillObjectActor);

        return stillObjectActor;
    }
}
