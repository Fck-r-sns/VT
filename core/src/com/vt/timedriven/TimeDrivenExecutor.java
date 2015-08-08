package com.vt.timedriven;

import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * Created by Fck.r.sns on 08.08.2015.
 */
public class TimeDrivenExecutor implements TimeDrivenExecutable // can be nested
{
    Array<TimeDrivenExecutable> m_actions;

    public TimeDrivenExecutor() {
        m_actions = new Array<TimeDrivenExecutable>(16);
    }

    @Override
    public boolean execute() {
        Iterator<TimeDrivenExecutable> i = m_actions.iterator();
        while (i.hasNext()) {
            if (i.next().execute())
                i.remove();
        }
        return false; // always returns false -> can't be removed when nested
    }

    public void addAction(TimeDrivenExecutable action) {
        m_actions.add(action);
    }
}
