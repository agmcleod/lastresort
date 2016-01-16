package com.agmcleod.lastresort.systems;

import com.agmcleod.lastresort.components.CollectableComponent;
import com.agmcleod.lastresort.components.ScanMaterialsComponent;
import com.agmcleod.lastresort.components.ScanUiComponent;
import com.agmcleod.lastresort.components.VisibilityComponent;
import com.agmcleod.lastresort.entities.Material;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.entities.ScanUi;
import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by aaronmcleod on 2016-01-16.
 */
public class ScanSystem extends EntitySystem {
    private final float SCAN_TIMER = 3;
    private final float RECHARGE_TIMER = 8;

    private Camera camera;
    private ImmutableArray<Entity> entities;
    private float onCooldownTimer = 0;
    private float scanTimer = 0;
    private float rechargeTimer = 0;
    private Vector2 itemCoordinatesCache;
    private ImmutableArray<Entity> materialEntities;

    public ScanSystem(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void addedToEngine(Engine engine) {
        entities = engine.getEntitiesFor(Family.one(ScanMaterialsComponent.class, ScanUiComponent.class).get());
        materialEntities = engine.getEntitiesFor(Family.all(CollectableComponent.class).get());
        itemCoordinatesCache = new Vector2();
    }

    @Override
    public void update(float dt) {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e instanceof Player) {
                Player player = (Player) e;
                ScanMaterialsComponent smc = player.getScanMaterialsComponent();
                if (smc.scanTriggerd) {
                    if (smc.canTrigger) {
                        smc.canTrigger = false;
                        smc.scanTriggerd = false;
                        scanTimer = SCAN_TIMER;
                        Material material = (Material) materialEntities.random();
                        itemCoordinatesCache.set(material.getTransform().position);
                    } else {
                        smc.showCannotScanYet = true;
                        onCooldownTimer = rechargeTimer;
                    }
                } else if (rechargeTimer > 0 && !smc.canTrigger) {
                    rechargeTimer -= dt;
                    if (rechargeTimer < 0) {
                        smc.canTrigger = true;
                    }
                    onCooldownTimer -= dt;
                    if (onCooldownTimer < 0) {
                        smc.showCannotScanYet = false;
                    }
                }
            } else if (e instanceof ScanUi) {
                ScanUi scanUi = (ScanUi) e;
                VisibilityComponent vc = scanUi.getVisibilityComponent();
                scanUi.getTransform().position.set(camera.position.x, camera.position.y);
                if (scanTimer > 0) {
                    if (!vc.visible) {
                        vc.visible = true;
                        scanUi.getScanUiComponent().scannedLocation.set(itemCoordinatesCache);
                    }
                    scanTimer -= dt;
                    if (scanTimer <= 0) {
                        vc.visible = false;
                        rechargeTimer = RECHARGE_TIMER;
                    }
                } else if (onCooldownTimer > 0 && !vc.visible) {
                    vc.visible = true;
                } else if (onCooldownTimer <= 0 && vc.visible) {
                    vc.visible = false;
                }
            }
        }
    }
}
