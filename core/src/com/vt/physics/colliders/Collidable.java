package com.vt.physics.colliders;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;
import com.vt.physics.Spatial;

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
    public Type getColliderType();
    public Shape2D getBoundingShape();
    public void onCollision(Collidable other); // process effect on this by other object

    public boolean checkCollision(Collidable other);
    public boolean checkShapeCollision(Circle circle);
    public boolean checkShapeCollision(Rectangle rectangle);
}
