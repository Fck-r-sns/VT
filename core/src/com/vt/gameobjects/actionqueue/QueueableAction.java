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

    enum State {
        Ready,
        Running,
        Finished
    }

    State getState();
    Flags getFlags();
    void draw(ShapeRenderer renderer);
    boolean start(Context ctx);
    boolean stop(Context ctx);
    boolean execute(Context ctx); // called every frame between start and stop
    void onAdd(Context ctx, PlayerVirtualState state);
}
