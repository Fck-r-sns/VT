package com.vt.logic.raycasting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.vt.physics.geometry.Point;
import com.vt.physics.geometry.LineSegment;
import com.vt.physics.geometry.Ray;

/**
 * Created by fckrsns on 21.02.2016.
 */
public class RayCaster {
    private Ray m_ray;

    public RayCaster(Ray ray) {
        m_ray = ray;
    }

    public Point findIntersection(final LineSegment segment) {
        // parametrized objects:
        // line : p1 + t1 * (p2 - p1)
        // ray : origin + t2 * direction
        float dx = segment.p2.x - segment.p1.x;
        float dy = segment.p2.y - segment.p1.y;
        float t2_numerator = m_ray.origin.y * dx - segment.p1.y * dx - m_ray.origin.x * dy + segment.p1.x * dy;
        float t2_denominator = m_ray.direction.x * dy - m_ray.direction.y * dx;
        if (t2_denominator == 0.0) {
            return null;
        }
        float t2 = t2_numerator / t2_denominator;
        if (t2 < 0) {
            return null;
        }
        float t1;
        if (dx != 0) {
            t1 = (m_ray.origin.x + t2 * m_ray.direction.x - segment.p1.x) / dx;
        } else if (dy != 0) {
            t1 = (m_ray.origin.y + t2 * m_ray.direction.y - segment.p1.y) / dy;
        } else {
            return null;
        }
        if (t1 < 0 || t1 > 1.0f) {
            return null;
        }
        float x = m_ray.origin.x + t2 * m_ray.direction.x;
        float y = m_ray.origin.y + t2 * m_ray.direction.y;
        return new Point(x, y);
    }

    public static void test() {
        String tag = "RayCastingTest";
        RayCaster rc;
        rc = new RayCaster(new Ray(new Point(2, 3), new Vector2(1, 1)));
        Gdx.app.log(tag, "" + rc.findIntersection(new LineSegment(new Point(8, 5), new Point(8, 9))).equals(new Point(8, 9)));
        Gdx.app.log(tag, "" + rc.findIntersection(new LineSegment(new Point(6, 7), new Point(10, 7))).equals(new Point(6, 7)));
        Gdx.app.log(tag, "" + (rc.findIntersection(new LineSegment(new Point(4, 5), new Point(6, 7))) == null));
        rc = new RayCaster(new Ray(new Point(3, 3), new Vector2(1, 0)));
        Gdx.app.log(tag, "" + (rc.findIntersection(new LineSegment(new Point(6, 3), new Point(8, 3))) == null));
        rc = new RayCaster(new Ray(new Point(3, 3), new Vector2(0, 1)));
        Gdx.app.log(tag, "" + (rc.findIntersection(new LineSegment(new Point(3, 6), new Point(3, 8))) == null));
    }
}
