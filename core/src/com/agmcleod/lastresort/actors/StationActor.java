package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Station;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by aaronmcleod on 2016-01-10.
 */
public class StationActor extends Actor {
    private Station station;
    private TextureRegion textureRegion;
    public StationActor(Station station, TextureRegion textureRegion) {
        this.textureRegion = textureRegion;
        TransformComponent transformComponent = station.getTransform();
        this.setBounds(transformComponent.position.x, transformComponent.position.y, transformComponent.width, transformComponent.height);
        this.station = station;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        Vector2 position = EntityToScreenBridge.transform(station);
        this.setPosition(position.x, position.y);

        batch.draw(textureRegion, getX(), getY(), getWidth(), getHeight());
    }
}
