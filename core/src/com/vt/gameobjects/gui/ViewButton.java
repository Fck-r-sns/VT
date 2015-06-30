package com.vt.gameobjects.gui;

import com.vt.game.Constants;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 30.06.2015.
 */
public class ViewButton extends Button {
    public ViewButton() {
        setSize(Constants.VIEW_BUTTON_WIDTH, Constants.VIEW_BUTTON_HEIGHT);
        setTexture(Assets.getInstance().gui.viewButtonUp);
        this.setName(Constants.VIEW_BUTTON_ACTOR_NAME);
    }
}
