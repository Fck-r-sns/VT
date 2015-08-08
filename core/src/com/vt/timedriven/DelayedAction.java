package com.vt.timedriven;

import com.vt.game.Environment;

/**
 * Created by Fck.r.sns on 08.08.2015.
 */
public class DelayedAction implements TimeDrivenExecutable {
    private float m_time = 0;
    private float m_delay = 0;
    private Runnable m_action;

    public DelayedAction(float delay, Runnable action) {
        m_delay = delay;
        m_time = Environment.getInstance().globalTime + m_delay;
        m_action = action;
    }

    public void restart() {
        m_time = Environment.getInstance().globalTime + m_delay;
    }

    @Override
    public boolean execute() {
        if (Environment.getInstance().globalTime > m_time) {
            m_action.run();
            return true;
        }
        return false;
    }
}
