package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Stars;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by aaronmcleod on 2016-01-09.
 */
public class StarsActor extends Actor {
    private int[][] tiling;
    private Stars stars;
    private TextureRegion textureRegion;
    public StarsActor(Stars stars, TextureRegion textureRegion) {
        int lowestPointX = -6;
        int lowestPointY = -6;
        int highestPointX = 6;
        int highestPointY = 6;
        tiling = new int[(highestPointX - lowestPointX + 1) * (highestPointY - lowestPointY + 1)][2];
        int index = 0;
        for (int x = lowestPointX; x <= highestPointX; x++) {
            for (int y = lowestPointY; y <= highestPointY; y++) {
                tiling[index] = new int[] { x, y };
                index++;
            }
        }

        this.stars = stars;
        this.textureRegion = textureRegion;
        TransformComponent transformComponent = stars.getTransform();
        this.setBounds(transformComponent.position.x, transformComponent.position.y, transformComponent.width, transformComponent.height);
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Vector2 position = EntityToScreenBridge.transform(stars);
        this.setPosition(position.x, position.y);
        for (int i = 0; i < tiling.length; i++) {
            int[] tileOffsets = tiling[i];
            batch.draw(
                    textureRegion,
                    getX() + getWidth() * tileOffsets[0],
                    getY() + getHeight() * tileOffsets[1],
                    getWidth(),
                    getHeight()
            );
        }

    }
}
