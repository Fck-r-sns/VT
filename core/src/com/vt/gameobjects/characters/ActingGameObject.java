package com.vt.gameobjects.characters;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.physics.colliders.Collidable;

/**
 * Created by Fck.r.sns on 10.05.2015.
 */
public class ActingGameObject extends GameObject implements Steerable<Vector2>, Collidable {
    protected Vector2 m_linearVelocity = new Vector2(); // pixels per second
    protected float m_angularVelocity = 0.0f;
    protected float m_boundingRadius;
    protected boolean m_tagged;
    protected float m_rotationDelta = 0.0f;

    protected float m_maxLinearSpeed = Constants.MAX_LINEAR_SPEED_DEFAULT;
    protected float m_maxLinearAcceleration = Constants.MAX_LINEAR_ACCELERATION_DEFAULT;
    protected float m_maxAngularSpeed = Constants.MAX_ANGULAR_SPEED_DEFAULT;
    protected float m_maxAngularAcceleration = Constants.MAX_ANGULAR_ACCELERATION_DEFAULT;

    protected SteeringAcceleration<Vector2> m_acceleration = new SteeringAcceleration<Vector2>(new Vector2(0, 0));
    protected SteeringBehavior<Vector2> m_behavior;

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        m_behavior = behavior;
    }

    @Override
    protected void update(float delta) {
        super.update(delta);
        if (m_behavior != null) {
            m_behavior.calculateSteering(m_acceleration);

            Vector2 pos = getPosition();
            pos.mulAdd(m_linearVelocity, delta);
            setPosition(pos.x, pos.y, Constants.ALIGN_ORIGIN);
            m_linearVelocity.mulAdd(m_acceleration.linear, delta).limit(getMaxLinearSpeed());

//            setRotation(m_linearVelocity.angle());
        }

        if (Math.abs(m_rotationDelta) > 1) {
            float signum = Math.signum(m_rotationDelta);
            m_angularVelocity += getMaxAngularAcceleration() * delta * signum;
            if (Math.abs(m_angularVelocity) > getMaxAngularSpeed())
                m_angularVelocity = getMaxAngularSpeed() * signum;
            float increment = Math.min(Math.abs(m_angularVelocity), Math.abs(m_rotationDelta)) * signum;
            float rotation = getRotation();
            rotation += increment;
            setRotation(rotation);
            m_rotationDelta -= increment;
        } else {
            m_angularVelocity = 0.0f;
        }
    }

    public void setRotationDelta(float rotation) {
        m_rotationDelta = rotation;
    }

    public void setRotationDeltaRelativeToCurrent(float rotation) {
        float newRotationDelta = rotation - getRotation();
        if (Math.abs(newRotationDelta) > 180)
            newRotationDelta = Math.signum(newRotationDelta) * (Math.abs(newRotationDelta) - 360);
        setRotationDelta(newRotationDelta);
    }

    public float getRotationDelta() {
        return m_rotationDelta;
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(getX() + getOriginX(), getY() + getOriginY());
    }

    @Override
    public void setPosition(float x, float y, int alignment) {
        if (alignment == Constants.ALIGN_ORIGIN) {
            x -= getOriginX();
            y -= getOriginY();
            super.setPosition(x, y);
        } else {
            super.setPosition(x, y, alignment);
        }
    }

    @Override
    public void setPosition(float x, float y) {
        setPosition(x, y, Constants.ALIGN_ORIGIN);
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

    @Override
    public Type getColliderType() {
        return Type.ActingObject;
    }

    @Override
    public Circle getBoundingShape() {
        return new Circle(getX() + getOriginX(), getY() + getOriginY(), getBoundingRadius());
    }

    @Override
    public void onCollision(Collidable other) {
        switch (other.getColliderType()) {
            case Wall: {
                Circle c = getBoundingShape();
                float x = c.x;
                float y = c.y;
                float r = c.radius;
                float otherLeft = other.getX(Align.left);
                float otherRight = other.getX(Align.right);
                float otherTop = other.getY(Align.top);
                float otherBottom = other.getY(Align.bottom);
                final float offset = other.getWidth() * 0.1f;
                final float offset2 = other.getWidth() * 0.3f;
                final float vel = 0.0f;

                if (Intersector.intersectSegmentCircle(
                        new Vector2(otherLeft - offset2, otherBottom + offset),
                        new Vector2(otherLeft - offset2, otherTop - offset),
                        new Vector2(x, y), r*r)
                        && m_linearVelocity.x > 0)
                    m_linearVelocity.x = -vel;

                else if (Intersector.intersectSegmentCircle(
                        new Vector2(otherRight + offset2, otherBottom + offset),
                        new Vector2(otherRight + offset2, otherTop - offset),
                        new Vector2(x, y), r*r)
                        && m_linearVelocity.x < 0)
                    m_linearVelocity.x = vel;

                if (Intersector.intersectSegmentCircle(
                        new Vector2(otherLeft + offset, otherBottom - offset2),
                        new Vector2(otherRight - offset, otherBottom - offset2),
                        new Vector2(x, y), r*r)
                        && m_linearVelocity.y > 0)
                    m_linearVelocity.y = -vel;
                else if (Intersector.intersectSegmentCircle(
                        new Vector2(otherLeft + offset, otherTop + offset2),
                        new Vector2(otherRight - offset, otherTop + offset2),
                        new Vector2(x, y), r*r)
                        && m_linearVelocity.y < 0)
                    m_linearVelocity.y = vel;
            }
            break;
            default:
                break;
        }
    }

    @Override
    public boolean checkCollision(Collidable other) {
        return other.checkShapeCollision(getBoundingShape());
    }

    @Override
    public boolean checkShapeCollision(Circle circle) {
        return Intersector.overlaps(getBoundingShape(), circle);
    }

    @Override
    public boolean checkShapeCollision(Rectangle rectangle) {
        return Intersector.overlaps(getBoundingShape(), rectangle);
    }
}
