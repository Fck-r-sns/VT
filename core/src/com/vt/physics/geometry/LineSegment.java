package com.vt.physics.geometry;

/**
 * Created by fckrsns on 21.02.2016.
 */
public class LineSegment {
    public Point p1;
    public Point p2;

    public LineSegment(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public int hashCode() {
        return p1.hashCode() + 31 * p2.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (obj.getClass() != LineSegment.class)
            return false;
        LineSegment other = (LineSegment)obj;
        return p1 == other.p1 && p2 == other.p2;
    }
}
