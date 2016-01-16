package com.agmcleod.lastresort.components;

import com.badlogic.ashley.core.Component;

/**
 * Created by aaronmcleod on 2016-01-16.
 */
public class VisibilityComponent implements Component {
    public boolean visible = true;

    public VisibilityComponent() {}
    public VisibilityComponent(boolean visible) {
        this.visible = visible;
    }
}
