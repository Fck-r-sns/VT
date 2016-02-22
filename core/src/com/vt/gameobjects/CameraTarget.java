package com.vt.gameobjects;

import com.badlogic.gdx.ai.steer.SteerableAdapter;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.vt.game.Constants;
import com.vt.physics.geometry.LineSegment;

/**
 * Created by Fck.r.sns on 27.06.2015.
 */
public class CameraTarget extends ActingObject {
    private class PositionUpdater extends SteerableAdapter<Vector2> {
        private Array<GameObject> m_objects;

        private PositionUpdater(GameObject[] objects) {
            m_objects = new Array<GameObject>(objects.length);
            for (GameObject o : objects)
                m_objects.add(o);
        }

        @Override
        public Vector2 getPosition() {
            Vector2 res = new Vector2(0, 0);
            if (m_objects.size == 0)
                return res;
            for (GameObject o : m_objects) {
                res.x += o.getX(Align.center);
                res.y += o.getY(Align.center);
            }
            res.x /= m_objects.size;
            res.y /= m_objects.size;
            return res;
        }
    }

    private PositionUpdater m_posUpdater;

    public CameraTarget(GameObject[] objects) {
        m_maxLinearSpeed = Constants.MAX_CAMERA_LINEAR_SPEED_DEFAULT;
        m_maxLinearAcceleration = Constants.MAX_CAMERA_LINEAR_ACCELERATION_DEFAULT;
        m_posUpdater = new PositionUpdater(objects);
        setSize(0.0f, 0.0f);

        this.setName(Constants.CAMERA_TARGET_ACTOR_NAME);

        setBehavior(
                new Arrive<Vector2>(this, m_posUpdater)
                        .setDecelerationRadius(Constants.CAMERA_ARRIVAL_DECELERATION_RADIUS)
        );
    }

    @Override
    public void setLinearVelocityX(float x) {
        if (x == 0.0f || m_posUpdater.getPosition().dst2(getPosition()) >= Constants.CAMERA_ARRIVAL_TOLERANCE_POW_2)
            super.setLinearVelocityX(x);
    }

    @Override
    public void setLinearVelocityY(float y) {
        if (y == 0.0f || m_posUpdater.getPosition().dst2(getPosition()) >= Constants.CAMERA_ARRIVAL_TOLERANCE_POW_2)
            super.setLinearVelocityY(y);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (m_posUpdater.getPosition().dst2(getPosition()) < Constants.CAMERA_ARRIVAL_TOLERANCE_POW_2) {
            setLinearVelocityX(0);
            setLinearVelocityY(0);
        }
    }
}
