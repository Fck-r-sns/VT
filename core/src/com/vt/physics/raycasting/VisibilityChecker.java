package com.vt.physics.raycasting;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedMap;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.physics.CollisionManager;
import com.vt.physics.geometry.LineSegment;
import com.vt.physics.geometry.Point;
import com.vt.physics.geometry.Ray;
import com.vt.resources.Assets;

/**
 * Created by fckrsns on 22.02.2016.
 */
public class VisibilityChecker {
    private static final int RAY_COUNT = 50;
    private static final float ROTATION_STEP = 360.0f / 50;
    private Point m_source;
    private ObjectMap<Integer, Ray> m_rays = new ObjectMap<Integer, Ray>(64);
    private ObjectMap<Integer, DrawableVector> m_vectors = new ObjectMap<Integer, DrawableVector>(RAY_COUNT);

    public VisibilityChecker() {
    }

    public VisibilityChecker(Point source) {
        m_source = source;
    }

    public void setSource(Point source) {
        m_source = source;
    }

//    public OrderedMap<Integer, Point> updateVisibilityZone() {
//        ObjectSet<LineSegment> segments = CollisionManager.getInstance().getStaticLineSegments();
//        if (m_rays.size == 0) {
//            int counter = 0;
//            for (LineSegment s : segments) {
//                m_rays.put(counter++, new Ray(m_source, new Vector2(s.p1.x - m_source.x, s.p1.y - m_source.y)));
//                m_rays.put(counter++, new Ray(m_source, new Vector2(s.p2.x - m_source.x, s.p2.y - m_source.y)));
//            }
//        }
//        OrderedMap<Integer, Point> result = new OrderedMap<Integer, Point>(m_rays.size);
//        RayCaster rc = new RayCaster();
//        for (ObjectMap.Entry<Integer, Ray> entry : m_rays.entries()) {
//            int id = entry.key;
//            rc.setRay(entry.value);
//            Point nearest = null;
//            float minRayParameter = Float.MAX_VALUE;
//            for (LineSegment s : segments) {
//                Point newIntersection = rc.findIntersection(s);
//                float newRayParameter = rc.getRayParameter();
//                if (newIntersection != null && newRayParameter < minRayParameter) {
//                    nearest = newIntersection;
//                    minRayParameter = newRayParameter;
//                }
//            }
//            if (nearest != null) {
//                result.put(id, nearest);
//                DrawableVector v = m_vectors.get(id, null);
//                if (v == null) {
//                    v = new DrawableVector(m_source.x, m_source.y, nearest.x, nearest.y, Color.RED, 0.03f, false);
//                    m_vectors.put(id, v);
//                } else {
//                    v.setOrigin(m_source.x, m_source.y);
//                    v.setVector(nearest.x, nearest.y);
//                }
//            } else {
//                m_vectors.remove(id);
//            }
//        }
//        return result;
//    }

    public OrderedMap<Integer, Point> updateVisibilityZone() {
        ObjectSet<LineSegment> segments = CollisionManager.getInstance().getStaticLineSegments();
        OrderedMap<Integer, Point> result = new OrderedMap<Integer, Point>(RAY_COUNT);
        Vector2 dir = new Vector2(0, 1);
        Ray ray = new Ray(m_source, dir);
        RayCaster rc = new RayCaster(ray);
        for (int i = 0; i < RAY_COUNT; ++i) {
            dir.rotate(ROTATION_STEP);
            Point nearest = null;
            float minRayParameter = Float.MAX_VALUE;
            for (LineSegment s : segments) {
                Point newIntersection = rc.findIntersection(s);
                float newRayParameter = rc.getRayParameter();
                if (newIntersection != null && newRayParameter < minRayParameter) {
                    nearest = newIntersection;
                    minRayParameter = newRayParameter;
                }
            }
            if (nearest != null) {
                result.put(i, nearest);
                DrawableVector v = m_vectors.get(i, null);
                if (v == null) {
                    v = new DrawableVector(m_source.x, m_source.y, nearest.x, nearest.y, Assets.getInstance().gui.redVector, 0.03f, false);
                    m_vectors.put(i, v);
                } else {
                    v.setOrigin(m_source.x, m_source.y);
                    v.setVector(nearest.x, nearest.y);
                }
            } else {
                m_vectors.remove(i);
            }
        }
        return result;
    }

    public void draw(SpriteBatch batch) {
        for (DrawableVector v : m_vectors.values())
            v.draw(batch);
    }
}
