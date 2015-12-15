package com.vt.actionqueue;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Fck.r.sns on 15.12.2015.
 */
public class PlayerVirtualState {
    private Vector2 m_playerPos;
    private Vector2 m_movePtrPos;
    private Vector2 m_viewPtrPos;

    public PlayerVirtualState(final Vector2 playerPos, final Vector2 movePos, final Vector2 viewPos) {
        m_playerPos = playerPos.cpy();
        m_movePtrPos = movePos.cpy();
        m_viewPtrPos = viewPos.cpy();
    }

    public Vector2 getMovePtrPos() {
        return m_movePtrPos;
    }

    public void changeMovePtrPos(float x, float y) {
        m_movePtrPos.set(x, y);
    }

    public Vector2 getViewPtrPos() {
        return m_viewPtrPos;
    }

    public void changeViewPtrPos(float x, float y) {
        m_viewPtrPos.set(x, y);
    }
}
