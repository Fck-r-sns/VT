package com.vt.gameobjects.gui;

import com.vt.game.Constants;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 10.07.2015.
 */
public class ButtonFactory {
    public enum ButtonType {
        View,
        Pause,
        Shoot
    }

    static public Button create(ButtonType type) {
        Button button = new Button();
        switch (type) {
            case View:
                button.setSize(Constants.VIEW_BUTTON_WIDTH, Constants.VIEW_BUTTON_HEIGHT);
                button.m_textureDown = Assets.getInstance().gui.viewButtonDown;
                button.m_textureUp = Assets.getInstance().gui.viewButtonUp;
                button.setName(Constants.VIEW_BUTTON_ACTOR_NAME);
                break;
            case Pause:
                button.setSize(Constants.PAUSE_BUTTON_WIDTH, Constants.PAUSE_BUTTON_HEIGHT);
                button.m_textureDown = Assets.getInstance().gui.pauseButtonDown;
                button.m_textureUp = Assets.getInstance().gui.pauseButtonUp;
                button.setName(Constants.PAUSE_BUTTON_ACTOR_NAME);
                break;
            case Shoot:
                button.setSize(Constants.SHOOT_BUTTON_WIDTH, Constants.SHOOT_BUTTON_HEIGHT);
                button.m_textureDown = Assets.getInstance().gui.shootButtonDown;
                button.m_textureUp = Assets.getInstance().gui.shootButtonUp;
                button.setName(Constants.SHOOT_BUTTON_ACTOR_NAME);
                break;
            default:
                return null;
        }
        button.release();
        return button;
    }
}
