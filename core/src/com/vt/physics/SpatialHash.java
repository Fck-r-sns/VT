package com.vt.physics;

/**
 * Created by fckrsns on 02.03.2016.
 */
public class SpatialHash implements Comparable<SpatialHash> {
    public int x;
    public int y;

    public SpatialHash(int x, int y) {
        this.x = x;
        this.y = y;
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
