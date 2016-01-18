package com.vt.gameobjects.pointers;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 10.05.2015.
 */
public class MovementPointer extends GameObject implements Steerable<Vector2> {
    public MovementPointer() {
        setSize(Constants.MOVEMENT_POINTER_WIDTH, Constants.MOVEMENT_POINTER_HEIGHT);
        setOrigin(Align.center);
        setTexture(Assets.getInstance().gameEntities.movementPointer);
        this.setName(Constants.MOVEMENT_POINTER_ACTOR_NAME);
        this.setActive(false);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(getX(Align.center), getY(Align.center));
    }

    // stubs
    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return null;
    }

    @Override
    public float getAngularVelocity() {
        return 0;
    }

    @Override
    public float getBoundingRadius() {
        return 0;
    }

    @Override
    public boolean isTagged() {
        return false;
    }

    @Override
    public void setTagged(boolean tagged) {
    }

    @Override
    public Vector2 newVector() {
        return null;
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return 0;
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return null;
    }

    @Override
    public float getMaxLinearSpeed() {
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
    }

    @Override
    public float getMaxLinearAcceleration() {
        return 0;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
    }

    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
    }
}
