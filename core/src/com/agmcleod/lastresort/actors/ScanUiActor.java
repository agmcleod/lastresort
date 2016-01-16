package com.agmcleod.lastresort.actors;

import com.agmcleod.lastresort.components.ScanUiComponent;
import com.agmcleod.lastresort.components.TransformComponent;
import com.agmcleod.lastresort.entities.Player;
import com.agmcleod.lastresort.entities.ScanUi;
import com.agmcleod.lastresort.helpers.EntityToScreenBridge;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Created by aaronmcleod on 2016-01-16.
 */
public class ScanUiActor extends Actor {
    private Color rechargeColor;
    private GlyphLayout glyphLayout;
    private Player player;
    private ScanUi scanUi;
    private BitmapFont uiFont;
    public ScanUiActor(Player player, ScanUi scanUi, BitmapFont uiFont) {
        this.player = player;
        this.scanUi = scanUi;
        this.uiFont = uiFont;
        glyphLayout = new GlyphLayout();
        Vector2 position = EntityToScreenBridge.transform(scanUi);
        TransformComponent transform = scanUi.getTransform();
        setBounds(position.x, position.y, transform.width, transform.height);
        rechargeColor = new Color(0.8f, 0, 0, 1);
    }

    public void draw(Batch batch, float alpha) {
        if (scanUi.getVisibilityComponent().visible) {
            ScanUiComponent suic = scanUi.getScanUiComponent();
            if (player.getScanMaterialsComponent().showCannotScanYet) {
                String text = "Recharging Scanner";
                updateTransform(text);
                uiFont.setColor(rechargeColor);
                uiFont.draw(batch, text, getX(), getY());
                uiFont.setColor(Color.WHITE);
            } else {
                String text = "[" + suic.scannedLocation.x + "," + suic.scannedLocation.y + "]";
                updateTransform(text);
                uiFont.draw(batch, text, getX(), getY());
            }
        }
    }

    public void updateTransform(String text) {
        TransformComponent transformComponent = scanUi.getTransform();
        Vector2 position = transformComponent.position;

        glyphLayout.setText(uiFont, text);
        setSize(glyphLayout.width, glyphLayout.height);
        transformComponent.width = glyphLayout.width;
        transformComponent.height = glyphLayout.height;

        setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
    }
}
