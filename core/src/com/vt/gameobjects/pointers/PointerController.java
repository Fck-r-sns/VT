package com.vt.gameobjects.pointers;

import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created by Fck.r.sns on 19.06.2015.
 */
public abstract class PointerController extends InputListener {
    private boolean m_active = false;

    public PointerController setActive(boolean active) {
        m_active = active;
        return this;
    }

    protected boolean isActive() {
        return m_active;
    }
}
