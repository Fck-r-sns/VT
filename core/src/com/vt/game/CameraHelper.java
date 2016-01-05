package com.vt.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.messages.Context;
import com.vt.messages.MessageDispatcher;
import com.vt.messages.MessageHandler;
import com.vt.messages.RewindContext;
import com.vt.serialization.RestorableValue;
import com.vt.serialization.ValuesChangeHistory;

/**
 * Created by Fck.r.sns on 27.06.2015.
 */
public class CameraHelper {
    static final float DEFAULT_ZOOM = 1.0f;
    static final float MIN_ZOOM = 0.5f;
    static final float MAX_ZOOM = 2f;

    private ValuesChangeHistory m_valuesHistory;
    private OrthographicCamera m_camera;
    private Actor m_target;

    public CameraHelper(OrthographicCamera camera) {
        m_valuesHistory = new ValuesChangeHistory();
        m_camera = camera;
        MessageDispatcher.getInstance().subscribeToBroadcast(
                MessageDispatcher.BroadcastMessageType.Rewind,
                new MessageHandler() {
                    @Override
                    public void onMessageReceived(Context ctx) {
                        if (ctx != null && ctx instanceof RewindContext) {
                            m_valuesHistory.rewindBack(((RewindContext) ctx).getRewindTime());
                        }
                    }
                });
    }

    public void setPosition(float x, float y) {
        if (m_camera.position.x != x) {
            m_valuesHistory.addValue(new RestorableValue() {
                private float m_previousX = m_camera.position.x;

                @Override
                public void restore() {
                    m_camera.position.x = m_previousX;
                }
            });
            m_camera.position.x = x;
        }

        if (m_camera.position.y != y) {
            m_valuesHistory.addValue(new RestorableValue() {
                private float m_previousY = m_camera.position.y;

                @Override
                public void restore() {
                    m_camera.position.y = m_previousY;
                }
            });
            m_camera.position.y = y;
        }
    }

    public void moveRight(float value) {
        setPosition(m_camera.position.x + value, m_camera.position.y);
    }

    public void moveLeft(float value) {
        setPosition(m_camera.position.x - value, m_camera.position.y);
    }

    public void moveUp(float value) {
        setPosition(m_camera.position.x, m_camera.position.y + value);
    }

    public void moveDown(float value) {
        setPosition(m_camera.position.x, m_camera.position.y - value);
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

    public void zoomUp(float value) {
        setZoom(getZoom() + value);
    }

    public void zoomDown(float value) {
        setZoom(getZoom() - value);
    }

    public float getZoom() {
        return m_camera.zoom;
    }

    public void update(float delta) {
        if (m_target != null)
            setPosition(m_target.getX(Align.center), m_target.getY(Align.center));
    }
}
