package com.vt.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by fckrsns on 16.01.2016.
 */

/**
 * For objects which have position on 2D-surface
 */
public interface Positionable {
    Vector2 getPosition();
    void setPosition(float x, float y);
    float getX();
    float getY();
}
