package com.vt.physics;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public interface Spatial {
    public Vector2 getPosition();
    public void setPosition(float x, float y);
    public float getX();
    public float getX(int align);
    public float getY();
    public float getY(int align);
    public float getWidth();
    public float getHeight();
}
