package com.vt.gameobjects.actionqueue;

import com.vt.physics.Positionable;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */
public abstract class AbstractQueueableAction implements QueueableAction, Positionable {
    State m_state = State.Ready;
    Flags m_flags = new Flags();

    @Override
    public final State getState() {
        return m_state;
    }

    @Override
    public Flags getFlags() {
        return m_flags;
    }

    @Override
    public final boolean start(Context ctx) {
        m_state = State.Running;
        return onStart(ctx);
    }

    @Override
    public final boolean stop(Context ctx) {
        m_state = State.Finished;
        return onStop(ctx);
    }

    @Override
    public final boolean execute(Context ctx) {
        return onExecute(ctx);
    }

    public boolean onStart(Context ctx) {
        return false;
    }

    public boolean onStop(Context ctx) {
        return false;
    }

    public boolean onExecute(Context ctx) {
        return false;
    }

    @Override
    public void onAdd(Context ctx) {
    }

    @Override
    public void onRemove(Context ctx) {
    }
}
