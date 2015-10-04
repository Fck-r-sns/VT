package com.vt.timedriven;

import com.vt.game.Environment;
import com.vt.serialization.RestorableValue;
import com.vt.serialization.ValuesChangeHistory;

/**
 * Created by Fck.r.sns on 08.08.2015.
 */
public class DelayedAction implements TimeDrivenExecutable {
    private ValuesChangeHistory m_valuesHistory;
    private float m_time = 0;
    private float m_delay = 0;
    private Runnable m_action;

    public DelayedAction(float delay, Runnable action) {
        m_delay = delay;
        m_time = Environment.getInstance().gameTime + m_delay;
        m_action = action;
    }

    public void restart() {
        m_valuesHistory.addValue(new RestorableValue() {
            private float m_previousTime = m_time;

            @Override
            public void restore() {
                m_time = m_previousTime;
            }
        });
        m_time = Environment.getInstance().gameTime + m_delay;
    }

    @Override
    public boolean execute() {
        if (Environment.getInstance().gameTime > m_time) {
            m_action.run();
            return true;
        }
        return false;
    }

    @Override
    public void setValuesHistory(ValuesChangeHistory history) {
        m_valuesHistory = history;
    }
}
