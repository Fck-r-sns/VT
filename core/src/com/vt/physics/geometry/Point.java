package com.vt.physics.geometry;

/**
 * Created by fckrsns on 21.02.2016.
 */
public class Point {
    public float x;
    public float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        return (int)(x + 31 * y);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != Point.class) {
            return false;
        }
        Point other = (Point)obj;
        return x == other.x && y == other.y;
    }

    public Point add(final Point other) {
        x += other.x;
        y += other.y;
        return this;
    }

    public Point substract(final Point other) {
        x -= other.x;
        y -= other.y;
        return this;
    }

    public Point multiply(float num) {
        x *= num;
        y *= num;
        return this;
    }

    public float distanceTo(final Point other) {
        return (float)Math.sqrt(distance2To(other));
    }

    public float distance2To(final Point other) {
        float dx = other.x - x;
        float dy = other.y - y;
        return (dx * dx + dy * dy);
    }

    public static Point sum(final Point p1, final Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    public static Point substraction(final Point p1, final Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
