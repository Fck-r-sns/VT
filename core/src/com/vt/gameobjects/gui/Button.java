package com.vt.gameobjects.gui;

import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 19.06.2015.
 */

public class Button extends GameObject {
    public Button() {
        setSize(Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        setOrigin(Align.topLeft);
        setTexture(Assets.getInstance().gui.buttonUp);
        this.setName(Constants.GUI_BUTTON_ACTOR_NAME);
        this.setActive(true);
    }
}
