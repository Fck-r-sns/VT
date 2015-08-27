package com.vt.actionqueue;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */
public abstract class AbstractQueueableAction implements QueueableAction {
    State m_state = State.Ready;

    @Override
    public final State getState() {
        return m_state;
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

    public abstract boolean onStart(Context ctx);
    public abstract boolean onStop(Context ctx);
    public abstract boolean onExecute(Context ctx);
}