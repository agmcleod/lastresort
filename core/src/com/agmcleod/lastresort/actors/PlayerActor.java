package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Aaron on 12/20/2015.
 */
public class PlayerActor extends Group {
    private Animation backwardAnimation;
    private Animation defaultAnimation;
    private float elapsedTime;
    private Animation forwardAnimation;
    private Player player;
    public PlayerActor(TextureAtlas atlas, final Player player) {
        this.player = player;
        elapsedTime = 0;

        Array<AtlasRegion> defaultAnimationFrames = atlas.findRegions("ship");
        defaultAnimation = new Animation(0.1f, defaultAnimationFrames);

        Array<AtlasRegion> forwardAnimationFrames = new Array<AtlasRegion>();
        forwardAnimationFrames.add(atlas.findRegion("shipbackjet1"));
        forwardAnimationFrames.add(atlas.findRegion("shipbackjet2"));
        forwardAnimation = new Animation(0.1f, forwardAnimationFrames);

        Array<AtlasRegion> backwardAnimationFrames = new Array<AtlasRegion>();
        backwardAnimationFrames.add(atlas.findRegion("shipfrontjet1"));
        backwardAnimationFrames.add(atlas.findRegion("shipfrontjet2"));
        backwardAnimation = new Animation(0.1f, backwardAnimationFrames);

        TransformComponent transformComponent = player.getTransform();
        Vector2 position = EntityToScreenBridge.transform(player);
        this.setBounds(position.x, position.y, transformComponent.width, transformComponent.height);
        this.setOrigin(getWidth() / 2, getHeight() / 2);

        this.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
               return player.setInputKeyState(keycode, true);
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return player.setInputKeyState(keycode, false);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Animation animation = null;
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (player.isThrustingForward()) {
            animation = forwardAnimation;
        } else if (player.isThrustingBackward()) {
            animation = backwardAnimation;
        } else {
            animation = defaultAnimation;
        }
        Vector2 position = EntityToScreenBridge.transform(player);
        this.setPosition(position.x, position.y);
        this.setRotation(MathUtils.radiansToDegrees * EntityToScreenBridge.getRotation(player));
        TextureRegion textureRegion = animation.getKeyFrame(elapsedTime, true);
        float width = textureRegion.getRegionWidth();
        float height = textureRegion.getRegionHeight();
        // the +90 rotation feels wrong.
        batch.draw(textureRegion, getX(), getY(), width / 2, height / 2, width, height, 1, 1, getRotation() + 90, true);
        super.draw(batch, parentAlpha);
    }
}
