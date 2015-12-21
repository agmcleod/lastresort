package com.agmcleod.lastresort;

import com.agmcleod.lastresort.actors.PlayerActor;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.systems.MovementSystem;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.Iterator;

public class Game extends ApplicationAdapter {
    public static final float WORLD_TO_BOX = 0.01f;
    public static final float BOX_TO_WORLD = 100f;

    private TextureAtlas atlas;
    private Matrix4 cameraCpy;
    private Box2DDebugRenderer debugRenderer;
    private Engine engine;
    private Array<Texture> textures;
    private Stage stage;
    private World world;

    @Override
    public void create () {
        engine = new Engine();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        stage = new Stage(new ScalingViewport(Scaling.stretch, width, height));
        cameraCpy = new Matrix4();

        Gdx.input.setInputProcessor(stage);

        world = new World(new Vector2(0, 0), true);
        debugRenderer = new Box2DDebugRenderer();
        textures = new Array<Texture>();

        Player player = new Player(world);
        engine.addEntity(player);

        engine.addSystem(new MovementSystem());

        atlas = new TextureAtlas(Gdx.files.internal("sprites.txt"));
        PlayerActor playerActor = new PlayerActor(atlas, player);

        stage.addActor(playerActor);
        stage.setKeyboardFocus(playerActor);
    }

    @Override
    public void dispose() {
        stage.dispose();
        debugRenderer.dispose();
        Iterator<Texture> it = textures.iterator();
        while (it.hasNext()) {
            Texture t = it.next();
            t.dispose();
        }
        atlas.dispose();
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();
        engine.update(dt);

        stage.act(dt);
        stage.draw();
        cameraCpy.set(stage.getCamera().combined);
        debugRenderer.render(world, cameraCpy.scl(BOX_TO_WORLD));

        world.step(1/60f, 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
