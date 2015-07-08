package com.vt.gameobjects;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.physics.colliders.Collidable;
import com.vt.physics.CollisionManager;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 16.05.2015.
 */
public class PlayerObject extends ActingGameObject implements Collidable {
    private MovementPointer m_movementPointer;
    private ViewPointer m_viewPointer;
    private Vector2 m_lastPosition;
    private boolean m_keepOrientation = true;

    public PlayerObject(MovementPointer mp, ViewPointer vp) {
        m_lastPosition = new Vector2(0, 0);
        m_movementPointer = mp;
        m_viewPointer = vp;

        setSize(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        setOrigin(Align.center);
//        setOrigin(Constants.PLAYER_ORIGIN_X * Constants.PLAYER_WIDTH,
//                Constants.PLAYER_ORIGIN_Y * Constants.PLAYER_HEIGHT);
        setUsePositionCorrection(false);
        setTexture(Assets.getInstance().gameEntities.player);
        this.setName(Constants.PLAYER_ACTOR_NAME);

        setBehavior(
                new Arrive<Vector2>(this, m_movementPointer)
                        .setDecelerationRadius(Constants.PLAYER_ARRIVAL_DECELERATION_RADIUS)
        );

        m_boundingRadius = 0.375f;
        CollisionManager.getInstance().registerDynamicCollidableObject(this);
    }

    public void setInitialPosition(float x, float y) {
        setPosition(x, y, Align.center);
        updateLastPosition();
    }

    @Override
    protected void update(float delta) {
        if (m_movementPointer.getPosition().dst(getPosition()) < Constants.PLAYER_ARRIVAL_TOLERANCE) {
            m_movementPointer.setActive(false);
            m_linearVelocity.set(0, 0);
        }

        if (m_viewPointer.isActive()) {
            setRotationDeltaRelativeToCurrent(m_viewPointer.getPosition().sub(getPosition()).angle());
            if (m_keepOrientation) {
                float newViewX = m_viewPointer.getX(Align.center) + getX(Align.center) - getLastPosition().x;
                float newViewY = m_viewPointer.getY(Align.center) + getY(Align.center) - getLastPosition().y;
                m_viewPointer.setPosition(newViewX, newViewY, Align.center);
            }
        }

        updateLastPosition();
        super.update(delta);
    }

    private void updateLastPosition() {
        m_lastPosition.set(getX(Align.center), getY(Align.center));
    }

    private Vector2 getLastPosition() {
        return m_lastPosition;
    }

    @Override
    public Type getColliderType() {
        return Type.ActingObject;
    }

    @Override
    public Circle getBoundingShape() {
        return new Circle(getX() + getOriginX(), getY() + getOriginY(), getBoundingRadius());
//        return new Circle(getX(), getY(), getBoundingRadius());
    }

    @Override
    public void onCollision(Collidable other) {
        switch (other.getColliderType()) {
            case Wall: {
                float otherLeft = other.getCenterX() - other.getWidth() / 2.0f;
                float otherRight = other.getCenterX() + other.getWidth() / 2.0f;
                float otherTop = other.getCenterY() + other.getHeight() / 2.0f;
                float otherBottom = other.getCenterY() - other.getHeight() / 2.0f;
                final float offset = other.getWidth() * 0.05f;
                final float offset2 = other.getWidth() * 0.2f;
                final float vel = 0.0f;

                if (Intersector.intersectSegmentCircle(
                        new Vector2(otherLeft - offset2, otherBottom + offset),
                        new Vector2(otherLeft - offset2, otherTop - offset),
                        new Vector2(getCenterX(), getCenterY()),
                        getBoundingRadius() * getBoundingRadius()
                )
                        && m_linearVelocity.x > 0)
                    m_linearVelocity.x = -vel;

                else if (Intersector.intersectSegmentCircle(
                        new Vector2(otherRight + offset2, otherBottom + offset),
                        new Vector2(otherRight + offset2, otherTop - offset),
                        new Vector2(getCenterX(), getCenterY()),
                        getBoundingRadius() * getBoundingRadius()
                )
                        && m_linearVelocity.x < 0)
                    m_linearVelocity.x = vel;

                if (Intersector.intersectSegmentCircle(
                        new Vector2(otherLeft + offset, otherBottom - offset2),
                        new Vector2(otherRight - offset, otherBottom - offset2),
                        new Vector2(getCenterX(), getCenterY()),
                        getBoundingRadius() * getBoundingRadius()
                )
                        && m_linearVelocity.y > 0)
                    m_linearVelocity.y = -vel;
                else if (Intersector.intersectSegmentCircle(
                        new Vector2(otherLeft + offset, otherTop + offset2),
                        new Vector2(otherRight - offset, otherTop + offset2),
                        new Vector2(getCenterX(), getCenterY()),
                        getBoundingRadius() * getBoundingRadius()
                )
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

    @Override
    public float getCenterX() {
        return getX(Align.center);
    }

    @Override
    public float getCenterY() {
        return getY(Align.center);
    }
}
