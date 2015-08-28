package com.vt.messages;

/**
 * Created by Fck.r.sns on 29.08.2015.
 */
public class RewindContext extends Context {
    private float m_rewindTime;

    public RewindContext(float rewindTime) {
        m_rewindTime = rewindTime;
    }

    public float getRewindTime() {
        return m_rewindTime;
    }
}
