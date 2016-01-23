package com.vt.gameobjects.actionqueue.actions;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.vt.game.Constants;
import com.vt.gameobjects.actionqueue.AbstractQueueableAction;
import com.vt.gameobjects.actionqueue.Context;
import com.vt.gameobjects.pointers.DrawableVector;

/**
 * Created by Fck.r.sns on 15.12.2015.
 */
public class PlaceMovementPointer extends AbstractQueueableAction {
    DrawableVector m_drawable;
    Vector2 m_targetPosition;

    public PlaceMovementPointer(float x, float y, Context ctx) {
        m_targetPosition = new Vector2(x, y);
        Vector2 prevPos = ctx.virtualState.getMovementPtrPos();
        m_drawable = new DrawableVector(
                prevPos.x, prevPos.y,
                m_targetPosition.x, m_targetPosition.y,
                Constants.MOVEMENT_POINTER_VECTOR_COLOR,
                Constants.MOVEMENT_POINTER_VECTOR_WIDTH);
        m_flags.CHANGE_MOVE_PTR = true;
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
    public void onAdd(Context ctx) {
        ++ctx.placeMovementPtrCount;
        ctx.virtualState.changeMovementPtrPos(m_targetPosition.x, m_targetPosition.y);
    }

    @Override
    public void onRemove(Context ctx) {
        --ctx.placeMovementPtrCount;
    }

    @Override
    public Vector2 getPosition() {
        return m_targetPosition;
    }

    @Override
    public void setPosition(float x, float y) {
        m_targetPosition.set(x, y);
        m_drawable.setVector(x, y);
    }

    @Override
    public float getX() {
        return m_targetPosition.x;
    }

    @Override
    public float getY() {
        return m_targetPosition.y;
    }
}
