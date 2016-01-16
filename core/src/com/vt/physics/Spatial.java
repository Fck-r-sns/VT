package com.vt.physics;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */

/**
 * For objects which have position and size
 */
public interface Spatial extends Positionable {
    float getX(int alignment);
    float getY(int alignment);
    float getWidth();
    float getHeight();
    float getOriginX();
    float getOriginY();
}
