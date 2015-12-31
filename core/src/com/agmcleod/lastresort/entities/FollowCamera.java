package com.agmcleod.lastresort.entities;

import com.agmcleod.lastresort.components.TransformComponent;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by aaronmcleod on 2015-12-31.
 */
public class FollowCamera {
    private Camera camera;
    private TransformComponent target;
    private Rectangle totalViewBounds;

    public FollowCamera(Camera camera, TransformComponent bounds, Rectangle totalViewBounds) {
        this.target = bounds;
        this.camera = camera;
        this.totalViewBounds = totalViewBounds;
    }

    public void followH() {
        Camera cam = this.camera;
        if (totalViewBounds.getWidth() > camera.viewportWidth) {
            if (target.position.x - camera.viewportWidth / 2 < totalViewBounds.x) {
                cam.position.x = totalViewBounds.x + camera.viewportWidth / 2;
            }
            else if (target.position.x + camera.viewportWidth / 2 > totalViewBounds.getWidth() + totalViewBounds.x) {
                cam.position.x = (totalViewBounds.getWidth() + totalViewBounds.x) - camera.viewportWidth / 2;
            }
            else {
                cam.position.x = target.position.x;
            }
        }
    }

    public void followV() {
        Camera cam = this.camera;
        if (totalViewBounds.getHeight() > camera.viewportHeight) {
            if (target.position.y - camera.viewportHeight / 2 < totalViewBounds.y) {
                cam.position.y = totalViewBounds.y + camera.viewportHeight / 2;
            } else if (target.position.y + camera.viewportHeight / 2 > totalViewBounds.getHeight() + totalViewBounds.y) {
                cam.position.y = (totalViewBounds.getHeight() + totalViewBounds.y) - camera.viewportHeight / 2;
            } else {
                cam.position.y = target.position.y;
            }
        }
    }

    public void update() {
        followH();
        followV();
    }
}