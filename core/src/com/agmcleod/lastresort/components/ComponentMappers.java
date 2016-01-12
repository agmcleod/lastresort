package com.agmcleod.lastresort.components;

import com.badlogic.ashley.core.ComponentMapper;

/**
 * Created by Aaron on 12/20/2015.
 */
public class ComponentMappers {
    public static final ComponentMapper<CollectableComponent> collectable = ComponentMapper.getFor(CollectableComponent.class);
    public static final ComponentMapper<HarpoonComponent> harpoon = ComponentMapper.getFor(HarpoonComponent.class);
    public static final ComponentMapper<HarpoonRotateToTargetComponent> harpoonRotateToTarget = ComponentMapper.getFor(HarpoonRotateToTargetComponent.class);
    public static final ComponentMapper<TransformComponent> transform = ComponentMapper.getFor(TransformComponent.class);
    public static final ComponentMapper<PhysicsComponent> physics = ComponentMapper.getFor(PhysicsComponent.class);
}
