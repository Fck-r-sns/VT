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
        if (obj.getClass() != Point.class)
            return false;
        Point other = (Point)obj;
        return (x == other.x && y == other.y);
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

    public static Point sum(final Point p1, final Point p2) {
        return new Point(p1.x + p2.x, p1.y + p2.y);
    }

    public static Point substraction(final Point p1, final Point p2) {
        return new Point(p1.x - p2.x, p1.y - p2.y);
    }
}
