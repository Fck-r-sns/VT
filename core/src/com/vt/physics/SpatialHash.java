package com.vt.physics;

import com.vt.game.Constants;

/**
 * Created by fckrsns on 02.03.2016.
 */
public class SpatialHash implements Comparable<SpatialHash> {
    public final int x;
    public final int y;

    public SpatialHash(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static SpatialHash createFromPosition(float x, float y) {
        return new SpatialHash(
                (int)(x / Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH),
                (int)(y / Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT)
        );
    }

    @Override
    public int hashCode() {
        return x + 31 * y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (SpatialHash.class != obj.getClass()) {
            return false;
        }
        SpatialHash other = (SpatialHash) obj;
        return (x == other.x && y == other.y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int compareTo(SpatialHash o) {
        if (x != o.x) {
            return x - o.x;
        } else {
            return y - o.y;
        }
    }
}
