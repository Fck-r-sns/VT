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

    void onAdd(Context ctx);
    void onRemove(Context ctx);

    // this methods will return true when action finish it's task and queue can fetch next one
    boolean start(Context ctx);
    boolean stop(Context ctx);
    boolean execute(Context ctx); // called every frame between start and stop
}
