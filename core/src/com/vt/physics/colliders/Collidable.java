package com.vt.physics.colliders;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Shape2D;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public interface Collidable {
    enum Type {
        Unknown,
        Wall,
        ActingObject,
        Projectile,
        Beam
    }
    public Type getColliderType();
    public Shape2D getBoundingShape();
    public void onCollision(Collidable other);

    public boolean checkCollision(Collidable other);
    public boolean checkShapeCollision(Circle circle);
    public boolean checkShapeCollision(Rectangle rectangle);

    public float getCenterX();
    public float getCenterY();
    public float getWidth();
    public float getHeight();
}
