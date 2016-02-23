package com.vt.gameobjects.characters;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.gameobjects.ActingObject;
import com.vt.gameobjects.pointers.MovementPointer;
import com.vt.gameobjects.pointers.ViewPointer;
import com.vt.gameobjects.weapons.AbstractWeapon;
import com.vt.gameobjects.weapons.Pistol;
import com.vt.physics.CollisionManager;
import com.vt.physics.geometry.Point;
import com.vt.physics.raycasting.VisibilityChecker;
import com.vt.resources.Assets;
import com.vt.serialization.RestorableValue;
import com.vt.timedriven.DelayedAction;
import com.vt.timedriven.TimeDrivenExecutor;

/**
 * Created by Fck.r.sns on 16.05.2015.
 */
public class CharacterObject extends ActingObject implements ControllableCharacter {
    private static final String TAG = CharacterObject.class.getName();
    private TimeDrivenExecutor m_actionsManager;
    private VisibilityChecker m_visibilityChecker;
    private MovementPointer m_movementPointer;
    private ViewPointer m_viewPointer;
    private Vector2 m_lastPosition;
    private boolean m_keepOrientation = true;
    private AbstractWeapon m_weapon;
    private DelayedAction m_shootingAction;
    private float m_visibilityRange_deg = Constants.CHARACTER_VISIBILITY_RANGE_ANGLE_DEG;

    protected enum State {
        Stand,
        Move,
        Shoot
    }

    private State m_state = State.Stand;

    public CharacterObject() {
        m_actionsManager = new TimeDrivenExecutor();
        m_actionsManager.setValuesHistory(getValuesHistory());
        m_lastPosition = new Vector2(0, 0);

        m_visibilityChecker = new VisibilityChecker();

        m_movementPointer = new MovementPointer();
        m_viewPointer = new ViewPointer();

        Environment.getInstance().currentStage.addActor(m_movementPointer);
        Environment.getInstance().currentStage.addActor(m_viewPointer);
        Environment.getInstance().currentStage.addActor(this);

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
                new Arrive<Vector2>(this, m_movementPointer)
                        .setDecelerationRadius(Constants.PLAYER_ARRIVAL_DECELERATION_RADIUS)
        );

        setBoundingRadius(Constants.PLAYER_BOUNDING_RADIUS);
        CollisionManager.getInstance().registerDynamicCollidableObject(getId(), this);

        m_weapon = new Pistol();
    }

    public void setInitialPosition(float x, float y) {
        setPosition(x, y, Constants.ALIGN_ORIGIN);
        m_movementPointer.setPosition(getX(Constants.ALIGN_ORIGIN), getY(Constants.ALIGN_ORIGIN), Align.center);
        m_viewPointer.setPosition(getX(Constants.ALIGN_ORIGIN) + 1.0f, getY(Constants.ALIGN_ORIGIN), Align.center);
        updateLastPosition();
    }

    @Override
    public void setLinearVelocityX(float x) {
        if (x == 0.0f || m_movementPointer.getPosition().dst2(getPosition()) >= Constants.PLAYER_ARRIVAL_TOLERANCE_POW_2)
            super.setLinearVelocityX(x);
    }

    @Override
    public void setLinearVelocityY(float y) {
        if (y == 0.0f || m_movementPointer.getPosition().dst2(getPosition()) >= Constants.PLAYER_ARRIVAL_TOLERANCE_POW_2)
            super.setLinearVelocityY(y);
    }

    public MovementPointer getMovementPointer() {
        return m_movementPointer;
    }

    public ViewPointer getViewPointer() {
        return m_viewPointer;
    }

    public void setState(State state) {
        getValuesHistory().addValue(new RestorableValue() {
            private State m_previousState = m_state;

            @Override
            public void restore() {
                setStateImpl(m_previousState);
            }
        });

        setStateImpl(state);
    }

    private void setStateImpl(State state) {
        m_state = state;
        manageAnimation();
    }

    @Override
    public void setMovementPointerPosition(float x, float y) {
        m_movementPointer.setActive(true);
        m_movementPointer.setPosition(x, y, Align.center);
    }

    @Override
    public void setViewPointerPosition(float x, float y) {
        m_viewPointer.setActive(true);
        m_viewPointer.setPosition(x, y, Align.center);
    }

    @Override
    public void shoot() {
        if (m_weapon != null) {
            setState(State.Shoot);
            if (m_shootingAction == null) {
                m_shootingAction = new DelayedAction(
                        0.2f, // TODO: change to constant
                        new Runnable() {
                            @Override
                            public void run() {
                                setState(State.Stand);
                                m_shootingAction = null;
                            }
                        }
                );
                m_actionsManager.addAction(m_shootingAction);
            } else {
                m_shootingAction.restart();
            }
            m_weapon.shoot();
        }
    }

    public void reload() {
    }

    @Override
    public void activateAbility() {
        // TODO: implement activating abilities
    }

    @Override
    public void update(float delta) {
        if (!Environment.getInstance().isRewinding()) {
            m_actionsManager.execute();

            if (m_movementPointer.getPosition().dst2(getPosition()) < Constants.PLAYER_ARRIVAL_TOLERANCE_POW_2) {
                m_movementPointer.setActive(false);
                setLinearVelocityX(0);
                setLinearVelocityY(0);
            }

            if (m_viewPointer.isActive()) {
                setRotationDeltaRelativeToCurrent(m_viewPointer.getPosition().sub(getPosition()).angle());
                if (m_keepOrientation) {
                    float newViewX = m_viewPointer.getX(Align.center) + getX(Align.center) - getLastPosition().x;
                    float newViewY = m_viewPointer.getY(Align.center) + getY(Align.center) - getLastPosition().y;
                    m_viewPointer.setPosition(newViewX, newViewY, Align.center);
                }
            } else {
                if (getLinearVelocity().len2() != 0.0f)
                    setRotationDeltaRelativeToCurrent(getLinearVelocity().angle());
            }

            m_visibilityChecker.updateVisibilityZone(
                    new Point(getX(Constants.ALIGN_ORIGIN), getY(Constants.ALIGN_ORIGIN)),
                    getRotation(),
                    m_visibilityRange_deg
            );


            updateLastPosition();
        }
        manageAnimation();
        if (m_state != State.Shoot) {
            if (getLinearVelocity().len2() > m_maxLinearSpeed * m_maxLinearSpeed * 0.25f) {
                if (m_state != State.Move)
                    setState(State.Move);
            } else {
                if (m_state != State.Stand)
                    setState(State.Stand);
            }
        }
        super.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        m_visibilityChecker.draw(batch);
    }

    private void updateLastPosition() {
        final float currentX = getX(Align.center);
        if (m_lastPosition.x != currentX) {
            getValuesHistory().addValue(new RestorableValue() {
                private float m_previousX = m_lastPosition.x;

                @Override
                public void restore() {
                    m_lastPosition.x = m_previousX;
                }
            });
            m_lastPosition.x = currentX;
        }

        final float currentY = getY(Align.center);
        if (m_lastPosition.y != currentY) {
            getValuesHistory().addValue(new RestorableValue() {
                private float m_previousY = m_lastPosition.y;

                @Override
                public void restore() {
                    m_lastPosition.y = m_previousY;
                }
            });
            m_lastPosition.y = currentY;
        }
    }

    private Vector2 getLastPosition() {
        return m_lastPosition;
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        updateWeaponLocation();
    }

    @Override
    protected void rotationChanged() {
        super.rotationChanged();
        updateWeaponLocation();
    }

    private void updateWeaponLocation() {
        if (m_weapon != null) {
            m_weapon.setPosition(
                    getX(Constants.ALIGN_ORIGIN),
                    getY(Constants.ALIGN_ORIGIN),
                    Constants.ALIGN_ORIGIN
            );
            m_weapon.rotate(getRotation());
        }
    }

    private void manageAnimation() {
        switch (m_state) {
            case Stand:
                break;
            case Move:
                break;
            case Shoot:
                break;
            default:
                break;
        }
    }
}
