package com.vt.gameobjects.actionqueue;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.vt.gameobjects.TouchHandler;
import com.vt.gameobjects.pointers.PointerSwitcher;

/**
 * Created by fckrsns on 16.01.2016.
 */
public class ActionQueueController implements TouchHandler {
    private ActionQueue m_queue;
    private PointerSwitcher m_pointerSwitcher = new PointerSwitcher();

    public ActionQueueController(ActionQueue queue) {
        m_queue = queue;
    }

    public void setCurrentPointerToMovement() {
        m_pointerSwitcher.setCurrentPointerToMovement();
    }

    public void setCurrentPointerToView() {
        m_pointerSwitcher.setCurrentPointerToView();
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
