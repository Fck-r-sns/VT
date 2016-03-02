package com.vt.physics.geometry;

import com.vt.game.Constants;
import com.vt.physics.SpatialHash;

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

    public SpatialHash spatialHash1() {
        return new SpatialHash(
                (int)(p1.x / Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH),
                (int)(p1.y / Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT)
        );
    }

    public SpatialHash spatialHash2() {
        return new SpatialHash(
                (int)(p2.x / Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH),
                (int)(p2.y / Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT)
        );
    }

    @Override
    public int hashCode() {
        return p1.hashCode() + 31 * p2.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != LineSegment.class) {
            return false;
        }
        LineSegment other = (LineSegment)obj;
        return p1.equals(other.p1) && p2.equals(other.p2);
    }

    @Override
    public String toString() {
        return "LineSegment(" + p1 + ";" + p2 + ")";
    }
}
