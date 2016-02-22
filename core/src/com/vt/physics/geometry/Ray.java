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

    @Override
    public int hashCode() {
        return origin.hashCode() + 31 * direction.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != Ray.class)
            return false;
        Ray other = (Ray)obj;
        return origin == other.origin && direction == other.direction;
    }
}
