package com.vt.serialization;

import com.badlogic.gdx.utils.PooledLinkedList;
import com.vt.game.Constants;
import com.vt.game.Environment;

/**
 * Created by Fck.r.sns on 27.08.2015.
 */
public class ValuesChangeHistory {
    private PooledLinkedList<RestorableValue> m_list = new PooledLinkedList<RestorableValue>(50);

    public void addValue(RestorableValue value) {
        m_list.add(value);
    }

    public void rewindBack(float rewindTime) {
        float time = Environment.getInstance().gameTime - rewindTime;
        m_list.iterReverse();
        while (true) {
            RestorableValue value = m_list.previous();
            if (value == null)
                break;
            if (value.getTime() >= time) {
                value.restore();
                m_list.remove();
            } else
                break;
        }
    }

    public void clearOldValues() {
        float time = Environment.getInstance().gameTime - Constants.MAX_HISTORY_TIME;
        m_list.iter();
        while (true) {
            RestorableValue value = m_list.next();
            if (value == null)
                break;
            if (value.getTime() < time)
                m_list.remove();
            else
                break;
        }
    }
}
