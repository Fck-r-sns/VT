package com.vt.gameobjects.actionqueue.actions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.vt.gameobjects.actionqueue.AbstractQueueableAction;
import com.vt.gameobjects.actionqueue.Context;
import com.vt.gameobjects.actionqueue.PlayerVirtualState;
import com.vt.gameobjects.pointers.DrawableVector;

/**
 * Created by Fck.r.sns on 15.12.2015.
 */
public class PlaceMovePointer extends AbstractQueueableAction {
    DrawableVector m_drawable;
    Vector2 m_targetPosition;

    public PlaceMovePointer(float x, float y) {
        m_targetPosition = new Vector2(x, y);
    }

    @Override
    public void draw(ShapeRenderer renderer) {
        if (m_drawable != null)
            m_drawable.draw(renderer);
    }

    @Override
    public boolean onStart(Context ctx) {
        return false;
    }

    @Override
    public boolean onStop(Context ctx) {
        return false;
    }

    @Override
    public boolean onExecute(Context ctx) {
        return false;
    }

    @Override
    public void onAdd(Context ctx, PlayerVirtualState state) {
        Vector2 prevPos = state.getMovePtrPos();
        m_drawable = new DrawableVector(prevPos.x, prevPos.y, m_targetPosition.x, m_targetPosition.y, Color.WHITE, 0.05f);
        state.changeMovePtrPos(m_targetPosition.x, m_targetPosition.y);
    }
}
