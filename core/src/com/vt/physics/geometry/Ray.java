package com.vt.physics.geometry;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by fckrsns on 21.02.2016.
 */
public class Ray {
    public Point origin;
    public Vector2 direction;

    public Ray(Point origin, Vector2 direction) {
        this.origin = origin;
        this.direction = direction;
    }
}
