package com.vt.game;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * Created by Fck.r.sns on 27.06.2015.
 */
public class CameraHelper {
    static final float DEFAULT_ZOOM = 1.0f;
    static final float MIN_ZOOM = 0.1f;
    static final float MAX_ZOOM = 10f;

    private OrthographicCamera m_camera;
    private Actor m_target;

    public CameraHelper(OrthographicCamera camera) {
        m_camera = camera;
    }

    public void setPosition(float x, float y) {
        m_camera.position.x = x;
        m_camera.position.y = y;
    }

    public Vector2 getPosition() {
        return new Vector2(m_camera.position.x, m_camera.position.y);
    }

    public void setTarget(Actor target) {
        m_target = target;
    }

    public void setZoom(float zoom) {
        if (zoom < MIN_ZOOM)
            zoom = MIN_ZOOM;
        else if (zoom > MAX_ZOOM)
            zoom = MAX_ZOOM;
        m_camera.zoom = zoom;
    }

    public float getZoom() {
        return m_camera.zoom;
    }

    public void update(float delta) {
        if (m_target != null)
            setPosition(m_target.getX(Align.center), m_target.getY(Align.center));
    }
}
