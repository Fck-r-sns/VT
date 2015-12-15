package com.vt.actionqueue;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */
public class ActionQueue {
    private PlayerVirtualState m_virtualState;
    private Array<QueueableAction> m_actions = new Array<QueueableAction>(16);
    private Context m_context = new Context();

    public ActionQueue(PlayerVirtualState virtualState) {
        m_virtualState = virtualState;
    }

    public void act(float delta) {
    }

    public void draw(ShapeRenderer renderer) {
        for (QueueableAction action : m_actions)
            action.draw(renderer);
    }

    public void addAction(QueueableAction action) {
        m_actions.add(action);
        action.onAdd(m_context, m_virtualState);
    }

}
