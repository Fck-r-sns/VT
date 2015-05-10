package com.vt.gameobjects;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;

/**
 * Created by Fck.r.sns on 10.05.2015.
 */
public class ActingGameObject extends GameObject implements Steerable<Vector2> {
    protected Vector2 m_linearVelocity = new Vector2(); // pixels per second
    protected float m_angularVelocity;
    protected float m_boundingRadius;
    protected boolean m_tagged;

    protected float m_maxLinearSpeed = Constants.MAX_LINEAR_SPEED_DEFAULT;
    protected float m_maxLinearAcceleration = Constants.MAX_LINEAR_ACCELERATION_DEFAULT;
    protected float m_maxAngularSpeed = Constants.MAX_ANGULAR_SPEED_DEFAULT;
    protected float m_maxAngularAcceleration = Constants.MAX_ANGULAR_ACCELERATION_DEFAULT;

    SteeringAcceleration<Vector2> m_acceleration = new SteeringAcceleration<Vector2>(new Vector2(0, 0));
    SteeringBehavior<Vector2> m_behavior;

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        m_behavior = behavior;
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        if (m_behavior != null) {
            m_behavior.calculateSteering(m_acceleration);

            Vector2 pos = new Vector2(getX(Align.center), getY(Align.center));
            pos.mulAdd(m_acceleration.linear, delta);
            setPosition(pos.x, pos.y, Align.center);
            m_linearVelocity.mulAdd(m_acceleration.linear, delta).limit(getMaxLinearSpeed());
        }
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(getX(Align.center), getY(Align.center));
    }

    @Override
    public float getOrientation() {
        return getRotation() * MathUtils.degreesToRadians;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return m_linearVelocity;
    }

    @Override
    public float getAngularVelocity() {
        return m_angularVelocity;
    }

    @Override
    public float getBoundingRadius() {
        return m_boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return m_tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        m_tagged = tagged;
    }

    @Override
    public Vector2 newVector() {
        return new Vector2();
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return vector.angle() * MathUtils.degreesToRadians;
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return outVector.setAngle(angle * MathUtils.radiansToDegrees);
    }

    @Override
    public float getMaxLinearSpeed() {
        return m_maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        m_maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return m_maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        m_maxAngularAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return m_maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        m_maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return m_maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        m_maxAngularAcceleration = maxAngularAcceleration;
    }
}
