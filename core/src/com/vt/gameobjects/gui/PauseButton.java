package com.vt.gameobjects.gui;

import com.vt.game.Constants;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 30.06.2015.
 */
public class PauseButton extends Button {
    public PauseButton() {
        setSize(Constants.PAUSE_BUTTON_WIDTH, Constants.PAUSE_BUTTON_HEIGHT);
        m_textureDown = Assets.getInstance().gui.pauseButtonDown;
        m_textureUp = Assets.getInstance().gui.pauseButtonUp;
        this.setName(Constants.PAUSE_BUTTON_ACTOR_NAME);
        release();
    }
}
