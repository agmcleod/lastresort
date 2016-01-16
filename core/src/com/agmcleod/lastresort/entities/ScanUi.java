package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.ComponentMappers;
import com.agmcleod.lastresort.components.ScanUiComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.components.VisibilityComponent;
import com.badlogic.gdx.Gdx;

/**
 * Created by aaronmcleod on 2016-01-16.
 */

// An entity is built for UI so it can update the data through a system, as it's more complicated than positional tracking
// It has a corresponding actor as well, used in the main scene over the ui scene.
public class ScanUi extends GameEntity {
    public ScanUi() {
        this.name = "scanUi";
        this.add(new TransformComponent(0, Gdx.graphics.getHeight() / 2, 100, 40, 0, 1, 1));
        this.add(new VisibilityComponent(false));
        this.add(new ScanUiComponent());
    }

    public VisibilityComponent getVisibilityComponent() {
        return ComponentMappers.visible.get(this);
    }

    public ScanUiComponent getScanUiComponent() {
        return ComponentMappers.scanUi.get(this);
    }
}
