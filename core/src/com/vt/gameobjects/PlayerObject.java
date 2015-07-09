package com.vt.gameobjects;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.pointers.MovementPointer;
import com.vt.gameobjects.pointers.ViewPointer;
import com.vt.physics.CollisionManager;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 16.05.2015.
 */
public class PlayerObject extends ActingGameObject {
    private MovementPointer m_movementPointer;
    private ViewPointer m_viewPointer;
    private Vector2 m_lastPosition;
    private boolean m_keepOrientation = true;

    public PlayerObject(MovementPointer mp, ViewPointer vp) {
        m_lastPosition = new Vector2(0, 0);
        m_movementPointer = mp;
        m_viewPointer = vp;

        m_maxLinearSpeed = Constants.MAX_PLAYER_LINEAR_SPEED;
        m_maxLinearAcceleration = Constants.MAX_PLAYER_LINEAR_ACCELERATION;
        m_maxAngularSpeed = Constants.MAX_PLAYER_ANGULAR_SPEED;
        m_maxAngularAcceleration = Constants.MAX_PLAYER_ANGULAR_ACCELERATION;

        setSize(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        setOrigin(Constants.PLAYER_ORIGIN_X * Constants.PLAYER_WIDTH,
                Constants.PLAYER_ORIGIN_Y * Constants.PLAYER_HEIGHT);
        setTexture(Assets.getInstance().gameEntities.player);
        this.setName(Constants.PLAYER_ACTOR_NAME);

        setBehavior(
                new Arrive<Vector2>(this, m_movementPointer)
                        .setDecelerationRadius(Constants.PLAYER_ARRIVAL_DECELERATION_RADIUS)
        );

        m_boundingRadius = Constants.PLAYER_BOUNDING_RADIUS;
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
}
