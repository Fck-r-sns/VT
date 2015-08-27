package com.vt.actionqueue;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */
public interface QueueableAction {
    public enum State {
        Ready,
        Running,
        Finished
    }
    public State getState();
    public boolean start(Context ctx);
    public boolean stop(Context ctx);
    public boolean execute(Context ctx); // called every frame between start and stop
}
