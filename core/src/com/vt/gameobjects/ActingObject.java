package com.vt.gameobjects;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.physics.colliders.Collidable;
import com.vt.physics.geometry.LineSegment;
import com.vt.serialization.RestorableValue;

/**
 * Created by Fck.r.sns on 10.05.2015.
 */
public class ActingObject extends GameObject implements Steerable<Vector2>, Collidable {
    private Vector2 m_linearVelocity = new Vector2(); // pixels per second
    private float m_angularVelocity = 0.0f;
    private float m_boundingRadius;
    private boolean m_tagged;
    private float m_rotationDelta = 0.0f;

    protected float m_maxLinearSpeed = Constants.MAX_LINEAR_SPEED_DEFAULT;
    protected float m_maxLinearAcceleration = Constants.MAX_LINEAR_ACCELERATION_DEFAULT;
    protected float m_maxAngularSpeed = Constants.MAX_ANGULAR_SPEED_DEFAULT;
    protected float m_maxAngularAcceleration = Constants.MAX_ANGULAR_ACCELERATION_DEFAULT;

    private SteeringAcceleration<Vector2> m_acceleration = new SteeringAcceleration<Vector2>(new Vector2(0, 0));
    private SteeringBehavior<Vector2> m_behavior;

    public void setBehavior(SteeringBehavior<Vector2> behavior) {
        m_behavior = behavior;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if (!Environment.getInstance().isRewinding()) {
            Vector2 pos = getPosition();
            pos.mulAdd(getLinearVelocity(), delta);
            setPosition(pos.x, pos.y, Constants.ALIGN_ORIGIN);

            if (m_behavior != null) {
                m_behavior.calculateSteering(m_acceleration);

                Vector2 vel = getLinearVelocity().cpy().mulAdd(m_acceleration.linear, delta).limit(getMaxLinearSpeed());
                setLinearVelocityX(vel.x);
                setLinearVelocityY(vel.y);
            }

            if (Math.abs(m_rotationDelta) > 1) {
                float rotationDelta = getRotationDelta();
                float sign = Math.signum(rotationDelta);
                setAngularVelocity(getAngularVelocity() + getMaxAngularAcceleration() * delta * sign);
                if (Math.abs(m_angularVelocity) > getMaxAngularSpeed())
                    setAngularVelocity(getMaxAngularSpeed() * sign);
                float increment = Math.min(Math.abs(m_angularVelocity), Math.abs(rotationDelta)) * sign;
                float rotation = getRotation();
                rotation += increment;
                setRotation(rotation);
                setRotationDelta(rotationDelta - increment);
            } else {
                setAngularVelocity(0.0f);
            }
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

    public void setLinearVelocityX(float x) {
        if (m_linearVelocity.x != x) {
            getValuesHistory().addValue(new RestorableValue() {
                private float m_previousX = m_linearVelocity.x;

                @Override
                public void restore() {
                    m_linearVelocity.x = m_previousX;
                }
            });

            m_linearVelocity.x = x;
        }
    }

    public void setLinearVelocityY(float y) {
        if (m_linearVelocity.y != y) {
            getValuesHistory().addValue(new RestorableValue() {
                private float m_previousY = m_linearVelocity.y;

                @Override
                public void restore() {
                    m_linearVelocity.y = m_previousY;
                }
            });

            m_linearVelocity.y = y;
        }
    }

    @Override
    public float getAngularVelocity() {
        return m_angularVelocity;
    }

    public void setAngularVelocity(float vel) {
        if (getAngularVelocity() != vel) {
            getValuesHistory().addValue(new RestorableValue() {
                float m_previousValue = getAngularVelocity();

                @Override
                public void restore() {
                    m_angularVelocity = m_previousValue;
                }
            });
            m_angularVelocity = vel;
        }
    }

    @Override
    public float getBoundingRadius() {
        return m_boundingRadius;
    }

    public void setBoundingRadius(float radius) {
        m_boundingRadius = radius;
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
            case Wall:
                processCollisionWithWall(other);
                break;
            case Projectile:
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

    @Override
    public LineSegment[] getLineSegments() {
        return new LineSegment[0];
    }

    protected void processCollisionWithWall(Collidable wall) {
        Circle c = getBoundingShape();
        float x = c.x;
        float y = c.y;
        float r = c.radius;
        float otherLeft = wall.getX(Align.left);
        float otherRight = wall.getX(Align.right);
        float otherTop = wall.getY(Align.top);
        float otherBottom = wall.getY(Align.bottom);
        final float offset = wall.getWidth() * 0.1f;
        final float offset2 = wall.getWidth() * 0.3f;
        final float vel = 0.0f;

        // check collision with left and right sides of wall tile
        if (Intersector.intersectSegmentCircle(
                new Vector2(otherLeft - offset2, otherBottom + offset),
                new Vector2(otherLeft - offset2, otherTop - offset),
                new Vector2(x, y), r * r)
                && getLinearVelocity().x > 0)
            setLinearVelocityX(-vel);
        else if (Intersector.intersectSegmentCircle(
                new Vector2(otherRight + offset2, otherBottom + offset),
                new Vector2(otherRight + offset2, otherTop - offset),
                new Vector2(x, y), r * r)
                && getLinearVelocity().x < 0)
            setLinearVelocityX(vel);

        // check collision with top and bottom sides of wall tile
        if (Intersector.intersectSegmentCircle(
                new Vector2(otherLeft + offset, otherBottom - offset2),
                new Vector2(otherRight - offset, otherBottom - offset2),
                new Vector2(x, y), r * r)
                && getLinearVelocity().y > 0)
            setLinearVelocityY(-vel);
        else if (Intersector.intersectSegmentCircle(
                new Vector2(otherLeft + offset, otherTop + offset2),
                new Vector2(otherRight - offset, otherTop + offset2),
                new Vector2(x, y), r * r)
                && getLinearVelocity().y < 0)
            setLinearVelocityY(vel);
    }
}
