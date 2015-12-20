package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.ashley.core.Entity;
import com.agmcleod.lastresort.components.ComponentMappers;

/**
 * Created by Aaron on 12/20/2015.
 */
public abstract class GameEntity extends Entity {
    private boolean dirty = false;
    public TransformComponent getTransform() {
        return ComponentMappers.transform.get(this);
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}