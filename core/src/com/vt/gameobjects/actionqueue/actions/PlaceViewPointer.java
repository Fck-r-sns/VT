package com.vt.gameobjects.actionqueue.actions;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.vt.game.Constants;
import com.vt.gameobjects.actionqueue.AbstractQueueableAction;
import com.vt.gameobjects.actionqueue.Context;
import com.vt.gameobjects.primitives.DrawableVector;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 15.12.2015.
 */
public class PlaceViewPointer extends AbstractQueueableAction {
    DrawableVector m_drawable;
    Vector2 m_targetPosition;

    public PlaceViewPointer(float x, float y, Context ctx) {
        m_targetPosition = new Vector2(x, y);
        Vector2 prevPos = ctx.virtualState.getMovementPtrPos();
        m_drawable = new DrawableVector(
                prevPos.x, prevPos.y,
                m_targetPosition.x, m_targetPosition.y,
                Assets.getInstance().gui.viewVector,
                Constants.VIEW_POINTER_VECTOR_WIDTH);
        m_flags.CHANGE_VIEW_PTR = true;
    }

    public PlaceViewPointer(float xOrig, float yOrig, float x, float y, Context ctx) {
        m_targetPosition = new Vector2(x, y);
        m_drawable = new DrawableVector(
                xOrig, yOrig,
                m_targetPosition.x, m_targetPosition.y,
                Assets.getInstance().gui.viewVector,
                Constants.VIEW_POINTER_VECTOR_WIDTH);
        m_flags.CHANGE_VIEW_PTR = true;
    }

    @Override
    public boolean onStart(Context ctx) {
        ctx.character.setViewPointerPosition(m_targetPosition.x, m_targetPosition.y);
        return true;
    }

    @Override
    public boolean onExecute(Context ctx) {
//        m_drawable.setOrigin(ctx.character.getPosition());
//        return Math.abs(ctx.character.getRotation() - m_targetPosition.cpy().sub(ctx.character.getPosition()).angle()) < 10;
        return true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (m_drawable != null)
            m_drawable.draw(batch);
    }

    @Override
    public void onAdd(Context ctx) {
        ++ctx.placeViewPtrCount;
        ctx.virtualState.changeViewPtrPos(m_targetPosition.x, m_targetPosition.y);
    }

    @Override
    public void onRemove(Context ctx) {
        --ctx.placeViewPtrCount;
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
