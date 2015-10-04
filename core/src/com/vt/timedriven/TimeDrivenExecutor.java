package com.vt.timedriven;

import com.badlogic.gdx.utils.Array;
import com.vt.serialization.RestorableValue;
import com.vt.serialization.ValuesChangeHistory;

/**
 * Created by Fck.r.sns on 08.08.2015.
 */
public class TimeDrivenExecutor implements TimeDrivenExecutable // can be nested
{
    private Array<TimeDrivenExecutable> m_actions;
    private ValuesChangeHistory m_valuesHistory;
    Array<TimeDrivenExecutable> m_toBeRemoved;

    public TimeDrivenExecutor() {
        m_actions = new Array<TimeDrivenExecutable>(16);
        m_toBeRemoved = new Array<TimeDrivenExecutable>(16);
    }

    public void setValuesHistory(ValuesChangeHistory history) {
        m_valuesHistory = history;
    }

    @Override
    public boolean execute() {
        for (TimeDrivenExecutable e : m_actions) {
            if (e.execute())
                m_toBeRemoved.add(e);
        }
        for (TimeDrivenExecutable e : m_toBeRemoved) {
            removeAction(e);
        }
        m_toBeRemoved.clear();
        return false; // always returns false -> can't be removed when nested
    }

    public void addAction(final TimeDrivenExecutable action) {
        action.setValuesHistory(m_valuesHistory);
        m_valuesHistory.addValue(new RestorableValue() {
            TimeDrivenExecutable m_action = action;

            @Override
            public void restore() {
                removeActionImpl(m_action);
            }
        });
        addActionImpl(action);
    }

    private void addActionImpl(final TimeDrivenExecutable action) {
        m_actions.add(action);
    }

    public void removeAction(final TimeDrivenExecutable action) {
        m_valuesHistory.addValue(new RestorableValue() {
            TimeDrivenExecutable m_action = action;

            @Override
            public void restore() {
                addActionImpl(m_action);
            }
        });
        removeActionImpl(action);
    }

    private void removeActionImpl(final TimeDrivenExecutable action) {
        m_actions.removeValue(action, true);
    }
}
