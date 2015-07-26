package com.vt.gameobjects.characters;

import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.gameobjects.ActingObject;
import com.vt.gameobjects.pointers.MovementPointer;
import com.vt.gameobjects.pointers.ViewPointer;
import com.vt.gameobjects.weapons.AbstractWeapon;
import com.vt.gameobjects.weapons.Rifle;
import com.vt.physics.CollisionManager;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 16.05.2015.
 */
public class CharacterObject extends ActingObject implements ControllableCharacter {
    private MovementPointer m_movementPointer;
    private ViewPointer m_viewPointer;
    private Vector2 m_lastPosition;
    private boolean m_keepOrientation = true;
    private AbstractWeapon m_weapon;

    public CharacterObject() {
        m_lastPosition = new Vector2(0, 0);

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

        m_weapon = new Rifle();
    }

    public void setInitialPosition(float x, float y) {
        setPosition(x, y, Constants.ALIGN_ORIGIN);
        m_movementPointer.setPosition(getX(Constants.ALIGN_ORIGIN), getY(Constants.ALIGN_ORIGIN), Align.center);
        m_viewPointer.setPosition(getX(Constants.ALIGN_ORIGIN) + 1.0f, getY(Constants.ALIGN_ORIGIN), Align.center);
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
        if (m_weapon != null)
            m_weapon.shoot();
    }

    public void reload() {
    }

    @Override
    public void activateAbility() {
        // TODO: implement activating abilities
    }

    @Override
    public void update(float delta) {
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
        } else {
            if (m_linearVelocity.len2() != 0.0f)
                setRotationDeltaRelativeToCurrent(m_linearVelocity.angle());
        }

        updateLastPosition();
        super.update(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }

    private void updateLastPosition() {
        m_lastPosition.set(getX(Align.center), getY(Align.center));
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
}
