package com.agmcleod.lastresort.components;

import com.badlogic.ashley.core.ComponentMapper;

/**
 * Created by Aaron on 12/20/2015.
 */
public class ComponentMappers {
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
}
