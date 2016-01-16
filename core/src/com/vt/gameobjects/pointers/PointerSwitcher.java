package com.vt.gameobjects.pointers;

/**
 * Created by fckrsns on 16.01.2016.
 */
public class PointerSwitcher {
    private enum CurrentPointer {
        Movement,
        View
    }

    private CurrentPointer m_currentPointer = CurrentPointer.Movement;

    public void setCurrentPointerToMovement() {
        m_currentPointer = CurrentPointer.Movement;
    }

    public void setCurrentPointerToView() {
        m_currentPointer = CurrentPointer.View;
    }

    public boolean isCurrentPointerMovement() {
        return m_currentPointer == CurrentPointer.Movement;
    }

    public boolean isCurrentPointerView() {
        return m_currentPointer == CurrentPointer.View;
    }
}
