package com.vt.gameobjects.actionqueue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.vt.gameobjects.TouchHandler;

/**
 * Created by fckrsns on 16.01.2016.
 */
public class ActionQueueController implements TouchHandler {
    private ActionQueue m_queue;

    public ActionQueueController(ActionQueue queue) {
        m_queue = queue;
    }

    @Override
    public boolean handleTouchDown(InputEvent event, float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public void handleTouchUp(InputEvent event, float x, float y, int pointer, int button) {

    }

    @Override
    public void handleTouchDragged(InputEvent event, float x, float y, int pointer) {

    }
}
