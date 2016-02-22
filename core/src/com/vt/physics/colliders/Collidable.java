package com.vt.physics.colliders;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.vt.physics.Spatial;
import com.vt.physics.geometry.LineSegment;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public interface Collidable extends Spatial {
    enum Type {
        Unknown,
        Wall,
        ActingObject,
        Projectile,
        Ray,
        Beam
    }
    Type getColliderType();
    Shape2D getBoundingShape();
    void onCollision(Collidable other); // process effect on this by other object

    boolean checkCollision(Collidable other);
    boolean checkShapeCollision(Circle circle);
    boolean checkShapeCollision(Rectangle rectangle);
    LineSegment[] getLineSegments();
}
