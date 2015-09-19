package com.vt.serialization;

import com.vt.game.Environment;

/**
 * Created by Fck.r.sns on 27.08.2015.
 */
public abstract class RestorableValue {
    private float m_time = 0.0f;

    public RestorableValue() {
        m_time = Environment.getInstance().gameTime;
    }

    public final float getTime() {
        return m_time;
    }

    public abstract void restore();
}
