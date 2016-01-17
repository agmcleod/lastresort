package com.agmcleod.lastresort.screens;

import com.agmcleod.lastresort.*;
import com.agmcleod.lastresort.actors.*;
import com.agmcleod.lastresort.entities.*;
import com.agmcleod.lastresort.systems.*;
import com.badlogic.ashley.core.Engine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

import java.util.Iterator;

/**
 * Created by aaronmcleod on 2015-12-27.
 */
public class PlayScreen implements Screen {
    private TextureAtlas atlas;
    private Array<Body> bodyCleanup;
    private Matrix4 cameraCpy;
    private Box2DDebugRenderer debugRenderer;
    private Engine engine;
    private FollowCamera followCamera;
    private Game game;
    private Player player;
    private Array<Texture> textures;
    private RecipeManager recipeManager;
    private ShapeRenderer shapeRenderer;
    private Stage stage;
    private StarmapGenerator starmapGenerator;
    private Stage uiStage;
    private BitmapFont uiFont;
    private ObjectMap<String, Label> uiMap;
    private World world;

    public PlayScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        engine = new Engine();
        world = new World(new Vector2(0, 0), true);
        world.setContactListener(new CollisionListener());
        debugRenderer = new Box2DDebugRenderer();
        textures = new Array<Texture>();
        shapeRenderer = new ShapeRenderer();

        atlas = new TextureAtlas(Gdx.files.internal("sprites.txt"));
        bodyCleanup = new Array<Body>();
        uiFont = new BitmapFont(Gdx.files.internal("xolo24.fnt"), Gdx.files.internal("xolo24.png"), false);
        createScene();
        createUi();
        Rectangle viewBounds = new Rectangle(-StarmapGenerator.MAP_WIDTH / 2, -StarmapGenerator.MAP_HEIGHT / 2, StarmapGenerator.MAP_WIDTH, StarmapGenerator.MAP_HEIGHT);
        followCamera = new FollowCamera(stage.getCamera(), player.getTransform(), viewBounds);
        // ((OrthographicCamera) stage.getCamera()).zoom = 10f;
    }

    public void createScene() {
        recipeManager = new RecipeManager(atlas);
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        stage = new Stage(new ScalingViewport(Scaling.stretch, width, height));
        cameraCpy = new Matrix4();

        Gdx.input.setInputProcessor(stage);

        // populate the engine with entities
        TextureAtlas.AtlasRegion starsRegion = atlas.findRegion("stars");
        Stars stars = new Stars(starsRegion);
        engine.addEntity(stars);

        TextureAtlas.AtlasRegion stationRegion = atlas.findRegion("station");
        Station station = new Station(stationRegion, world, recipeManager);
        engine.addEntity(station);

        TextureAtlas.AtlasRegion playerSprite = atlas.findRegion("ship");
        player = new Player(playerSprite, world);
        engine.addEntity(player);
        TextureAtlas.AtlasRegion harpoonRegion = atlas.findRegion("harpoon");
        Harpoon harpoon = new Harpoon(harpoonRegion, player);
        engine.addEntity(harpoon);
        ScanUi scanUi = new ScanUi();
        engine.addEntity(scanUi);

        engine.addSystem(new MovementSystem());
        engine.addSystem(new HarpoonSystem(world));
        engine.addSystem(new StarsParallaxSystem(stage.getCamera()));
        engine.addSystem(new RecipeCollectionSystem(engine, player, recipeManager, world, bodyCleanup));
        engine.addSystem(new ScanSystem(stage.getCamera()));

        player.setHarpoon(harpoon);

        // populate the stage with actors
        PlayerActor playerActor = new PlayerActor(atlas, player);
        HarpoonActor harpoonActor = new HarpoonActor(world, harpoonRegion, harpoon);

        playerActor.addActor(harpoonActor);
        stage.addActor(new StarsActor(stars, starsRegion));
        stage.addActor(new StationActor(station, stationRegion));
        stage.addActor(playerActor);
        stage.addActor(new ScanUiActor(player, scanUi, uiFont));
        stage.setKeyboardFocus(playerActor);
        starmapGenerator = new StarmapGenerator();
        starmapGenerator.buildMap(world, engine, stage, atlas);
        starmapGenerator.placeMines(world, engine, stage, atlas);
        Array<MaterialActor> collectObjects = starmapGenerator.buildCollectObjects(world, engine, stage, atlas);
        starmapGenerator.buildBorders(engine, world);

        Iterator<MaterialActor> stillActorIterator = collectObjects.iterator();
        while (stillActorIterator.hasNext()) {
            MaterialActor actor = stillActorIterator.next();
            recipeManager.addAvailableRecipeType(actor.getRecipeType());
            actor.addListener(new HarpoonTargetListener(actor, player));
        }

        stage.addActor(new RopeActor(player, atlas.findRegion("harpoon")));
    }

    public void createUi() {
        uiMap = new ObjectMap<String, Label>();
        uiStage = new Stage(new ScalingViewport(Scaling.stretch, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Skin skin = new Skin();
        skin.add("default", new Label.LabelStyle(uiFont, Color.WHITE));
        Table table = new Table();
        table.setFillParent(true);
        Label coordinates = new Label("test", skin);
        table.add(coordinates).expandX().align(Align.topLeft);
        RecipeGroup recipeGroup = new RecipeGroup(shapeRenderer);
        recipeManager.setRecipeGroup(recipeGroup);
        recipeManager.makeNewRecipe();
        recipeManager.populateUi();

        table.add(recipeGroup).width(recipeGroup.getWidth());
        table.padLeft(10);
        table.padTop(10);

        Label controls = new Label("A / D - rotate\nSpace - Thrust\nCtrl - Reverse\nG - Scan Location", skin);
        controls.setAlignment(Align.right);
        table.row();
        table.add(controls).colspan(2).expand().align(Align.bottomRight);
        table.padRight(10);
        table.padTop(10);

        uiMap.put("coordinates", coordinates);

        uiStage.addActor(table);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float dt = Gdx.graphics.getDeltaTime();
        engine.update(dt);

        Label coordinates = uiMap.get("coordinates");
        Vector2 playerPosition = player.getTransform().position;
        String coordinatesText = MathUtils.floor(playerPosition.x) +
                "," + MathUtils.floor(playerPosition.y);
        coordinates.setText(coordinatesText);

        followCamera.update();

        stage.act(dt);
        stage.draw();

        uiStage.act(dt);
        uiStage.draw();

        cameraCpy.set(stage.getCamera().combined);
        debugRenderer.render(world, cameraCpy.scl(Game.BOX_TO_WORLD));

        world.step(1/60f, 6, 2);

        for (int i = bodyCleanup.size - 1; i >= 0; i--) {
            Body body = bodyCleanup.get(i);
            world.destroyBody(body);
            bodyCleanup.removeIndex(i);
        }

        if (player.isDead()) {
            game.setScreen(this);
        } else if (recipeManager.availableRecipesFinished()) {
            game.startEndScreen();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        debugRenderer.dispose();
        shapeRenderer.dispose();
        Iterator<Texture> it = textures.iterator();
        while (it.hasNext()) {
            Texture t = it.next();
            t.dispose();
        }
        atlas.dispose();
    }
}
