package com.vt.gameobjects.actionqueue;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */
public class ActionQueue {
    private com.vt.gameobjects.actionqueue.PlayerVirtualState m_virtualState;
    private Array<com.vt.gameobjects.actionqueue.QueueableAction> m_actions = new Array<com.vt.gameobjects.actionqueue.QueueableAction>(16);
    private Context m_context = new Context();

    public ActionQueue(com.vt.gameobjects.actionqueue.PlayerVirtualState virtualState) {
        m_virtualState = virtualState;
    }

    public void act(float delta) {
    }

    public void draw(ShapeRenderer renderer) {
        for (com.vt.gameobjects.actionqueue.QueueableAction action : m_actions)
            action.draw(renderer);
    }

    public void addAction(com.vt.gameobjects.actionqueue.QueueableAction action) {
        m_actions.add(action);
        action.onAdd(m_context, m_virtualState);
    }

}
