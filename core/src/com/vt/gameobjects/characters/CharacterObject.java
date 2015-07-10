package com.vt.gameobjects.characters;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.ActingGameObject;
import com.vt.gameobjects.pointers.MovementPointer;
import com.vt.gameobjects.pointers.ViewPointer;
import com.vt.physics.CollisionManager;
import com.vt.physics.behavior.CustomArrive;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 16.05.2015.
 */
public class CharacterObject extends ActingGameObject implements ControllableCharacter {
    private MovementPointer m_movementPointer;
    private ViewPointer m_viewPointer;
    private Vector2 m_lastPosition;
    private boolean m_keepOrientation = true;

    public CharacterObject() {
        m_lastPosition = new Vector2(0, 0);

        m_movementPointer = new MovementPointer();
        addActor(m_movementPointer);

        m_viewPointer = new ViewPointer();
        addActor(m_viewPointer);

        m_maxLinearSpeed = Constants.MAX_PLAYER_LINEAR_SPEED;
        m_maxLinearAcceleration = Constants.MAX_PLAYER_LINEAR_ACCELERATION;
        m_maxAngularSpeed = Constants.MAX_PLAYER_ANGULAR_SPEED;
        m_maxAngularAcceleration = Constants.MAX_PLAYER_ANGULAR_ACCELERATION;

        setSize(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        setOrigin(Constants.PLAYER_ORIGIN_RELATIVE_X * Constants.PLAYER_WIDTH,
                Constants.PLAYER_ORIGIN_RELATIVE_Y * Constants.PLAYER_HEIGHT);
        setTexture(Assets.getInstance().gameEntities.player);
        this.setName(Constants.PLAYER_ACTOR_NAME);

        setBehavior(
                new CustomArrive(this, m_movementPointer)
                        .setDecelerationRadius(Constants.PLAYER_ARRIVAL_DECELERATION_RADIUS)
        );

        m_boundingRadius = Constants.PLAYER_BOUNDING_RADIUS;
        CollisionManager.getInstance().registerDynamicCollidableObject(this);
    }

    public void setInitialPosition(float x, float y) {
        setPosition(x, y, Constants.ALIGN_ORIGIN);
        m_movementPointer.setPosition(getX(Constants.ALIGN_ORIGIN), getY(Constants.ALIGN_ORIGIN));
        m_viewPointer.setPosition(getX(Constants.ALIGN_ORIGIN), getY(Constants.ALIGN_ORIGIN));
        updateLastPosition();
    }

    public MovementPointer getMovementPointer() {
        return m_movementPointer;
    }

    public ViewPointer getViewPointer() {
        return m_viewPointer;
    }

    @Override
    public void setMovementPointerPosition(float x, float y) {
        m_movementPointer.setPosition(x, y, Align.center);
    }

    @Override
    public void setViewPointerPosition(float x, float y) {
        m_viewPointer.setPosition(x, y, Align.center);
    }

    @Override
    public void shot() {
        // TODO: implement shooting
    }

    @Override
    public void activateAbility() {
        // TODO: implement activating abilities
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

    @Override
    public void draw(Batch batch, float parentAlpha) {
        m_movementPointer.draw(batch, parentAlpha);
        m_viewPointer.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }

    private void updateLastPosition() {
        m_lastPosition.set(getX(Align.center), getY(Align.center));
    }

    private Vector2 getLastPosition() {
        return m_lastPosition;
    }
}
