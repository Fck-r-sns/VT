package com.vt.gameobjects.actionqueue;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by Fck.r.sns on 22.08.2015.
 */

public interface QueueableAction {
    class Flags {
        public boolean CHANGE_MOVE_PTR = false;
        public boolean CHANGE_VIEW_PTR = false;
    }

    public enum State {
        Ready,
        Running,
        Finished
    }

    public State getState();
    public Flags getFlags();
    public void draw(ShapeRenderer renderer);
    public boolean start(Context ctx);
    public boolean stop(Context ctx);
    public boolean execute(Context ctx); // called every frame between start and stop
    public void onAdd(Context ctx, PlayerVirtualState state);
}
