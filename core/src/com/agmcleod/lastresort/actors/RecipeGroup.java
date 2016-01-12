package com.agmcleod.lastresort.actors;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * Created by aaronmcleod on 2016-01-11.
 */
public class RecipeGroup extends Group {
    private ShapeRenderer renderer;

    public RecipeGroup(ShapeRenderer renderer) {
        this.setBounds(0, 0, 200, 100);
        this.renderer = renderer;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.end();

        renderer.begin(ShapeRenderer.ShapeType.Line);
        renderer.setColor(Color.WHITE);
        renderer.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        renderer.end();
        batch.begin();
        super.draw(batch, alpha);
    }
}
