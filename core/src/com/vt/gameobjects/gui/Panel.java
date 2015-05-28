package com.vt.gameobjects.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 28.05.2015.
 */
public class Panel extends GameObject {
    public Panel() {
        float height = Constants.VIEWPORT_WIDTH / Gdx.graphics.getWidth() * Gdx.graphics.getHeight();
        float width = height / Constants.GUI_PANEL_HEIGHT * Constants.GUI_PANEL_WIDTH;
        setSize(width, height);
        setOrigin(Align.topLeft);
        setTexture(Assets.getInstance().gui.panel);
        this.setName(Constants.GUI_PANEL_ACTOR_NAME);
        this.setActive(true);
    }
}
