package com.vt.gameobjects.characters;

/**
 * Created by Fck.r.sns on 10.07.2015.
 */
public class ManualController {
    private enum CurrentPointer {
        Movement,
        View
    }

    private ControllableCharacter m_controllable;
    private CurrentPointer m_currentPointer = CurrentPointer.Movement;

    public ManualController(ControllableCharacter target) {
        m_controllable = target;
    }

    public void setCurrentPointerToMovement() {
        m_currentPointer = CurrentPointer.Movement;
    }

    public void setCurrentPointerToView() {
        m_currentPointer = CurrentPointer.View;
    }

    public void setPointerPosition(float x, float y) {
        switch (m_currentPointer) {
            case Movement:
                m_controllable.setMovementPointerPosition(x, y);
                break;
            case View:
                m_controllable.setViewPointerPosition(x, y);
                break;
            default:
                break;
        }
    }

    public void shot() {
        m_controllable.shot();
    }

    public void activateAbility() {
        m_controllable.activateAbility();
    }
}
