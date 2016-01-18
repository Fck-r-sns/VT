package com.vt.gameobjects.characters;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.vt.gameobjects.TouchHandler;
import com.vt.gameobjects.pointers.PointerSwitcher;
import com.vt.messages.MessageDispatcher;

/**
 * Created by Fck.r.sns on 10.07.2015.
 */
public class ManualController implements TouchHandler {
    private ControllableCharacter m_controllable;
    private PointerSwitcher m_pointerSwitcher = new PointerSwitcher();
    private int m_firstTouchPointer = -1;

    public ManualController(ControllableCharacter target) {
        m_controllable = target;
    }

    public void setCurrentPointerToMovement() {
        m_pointerSwitcher.setCurrentPointerToMovement();
    }

    public void setCurrentPointerToView() {
        m_pointerSwitcher.setCurrentPointerToView();
    }

    public void setPointerPosition(float x, float y) {
        if (m_pointerSwitcher.isCurrentPointerMovement())
            m_controllable.setMovementPointerPosition(x, y);
        else
            m_controllable.setViewPointerPosition(x, y);
    }

    public void shoot() {
        m_controllable.shoot();
    }

    public void activateAbility() {
        m_controllable.activateAbility();
    }

    @Override
    public boolean handleTouchDown(InputEvent event, float x, float y, int pointer, int button) {
        // in case of touchUp skipping m_firstTouchPointer it can be locked on not actual value
        // for that case added check for pointer == 0
        if (m_firstTouchPointer == -1 || pointer == 0) // 0 is always first touch
            m_firstTouchPointer = pointer;
        if (pointer == m_firstTouchPointer)
            setPointerPosition(x, y);
        else
            shoot();
        MessageDispatcher.getInstance().sendBroadcast(MessageDispatcher.BroadcastMessageType.TouchDownWithRealTime, null);
        return true;
    }

    @Override
    public void handleTouchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (pointer == m_firstTouchPointer)
            m_firstTouchPointer = -1;
    }

    @Override
    public void handleTouchDragged(InputEvent event, float x, float y, int pointer) {
        if (pointer == m_firstTouchPointer)
            setPointerPosition(x, y);
    }
}
