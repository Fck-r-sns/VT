package com.vt.physics.raycasting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedMap;
import com.badlogic.gdx.utils.Queue;
import com.vt.gameobjects.primitives.DrawableVector;
import com.vt.physics.CollisionManager;
import com.vt.physics.SpatialHash;
import com.vt.physics.SpatialHashTable;
import com.vt.physics.geometry.LineSegment;
import com.vt.physics.geometry.Point;
import com.vt.physics.geometry.Ray;
import com.vt.resources.Assets;

/**
 * Created by fckrsns on 22.02.2016.
 */
public class VisibilityChecker {
    private static final int RAY_COUNT = 60;
    private ObjectMap<Integer, Ray> m_rays = new ObjectMap<Integer, Ray>(64);
    private ObjectMap<Integer, DrawableVector> m_vectors = new ObjectMap<Integer, DrawableVector>(RAY_COUNT);
    private RayCaster m_rc = new RayCaster();
    private long timeSum = 0;
    private int counter = 0;

    private ObjectSet<SpatialHash> visitedBuckets = new ObjectSet<SpatialHash>(16);
    private Queue<SpatialHash> bucketQueue = new Queue<SpatialHash>(16);

    public OrderedMap<Integer, Point> updateVisibilityZone(Point source, float centerAngle_deg, float rangeAngle_deg) {
        long startTime = System.nanoTime();
        SpatialHashTable<LineSegment> segmentsTable = CollisionManager.getInstance().getStaticLineSegments();
        OrderedMap<Integer, Point> result = new OrderedMap<Integer, Point>(RAY_COUNT);
        Vector2 dir = new Vector2(1, 0).rotate(centerAngle_deg - rangeAngle_deg / 2);
        Ray ray = new Ray(source, dir);
        SpatialHash rayHash = SpatialHash.createFromPosition(source.x, source.y);
        m_rc.setRay(ray);
        final float ROTATION_STEP = rangeAngle_deg / RAY_COUNT;
        for (int i = 0; i < RAY_COUNT; ++i) {
            dir.rotate(ROTATION_STEP);

            visitedBuckets.clear();
            bucketQueue.clear();
            bucketQueue.addLast(rayHash);
            Point nearest = null;
            float minRayParameter = Float.MAX_VALUE;
            while (bucketQueue.size > 0) {
                SpatialHash currentHash = bucketQueue.removeFirst();
                ObjectSet<LineSegment> segmentsBucket = segmentsTable.getBucket(currentHash);
                if (segmentsBucket == null) {
                    continue;
                }
                for (LineSegment s : segmentsBucket) {
                    Point newIntersection = m_rc.findIntersection(s);
                    float newRayParameter = m_rc.getRayParameter();
                    if (newIntersection != null && newRayParameter < minRayParameter) {
                        nearest = newIntersection;
                        minRayParameter = newRayParameter;
                    }
                }
                if (nearest != null) {
                    // found
                    break;
                } else {
                    visitedBuckets.add(currentHash);
                    for (int deltaX = -1; deltaX <= 1; ++deltaX) {
                        for (int deltaY = -1; deltaY <= 1; ++deltaY) {
                            if (deltaX == 0 && deltaY == 0) {
                                continue;
                            }
                            SpatialHash nearbyHash = new SpatialHash(currentHash.x + deltaX, currentHash.y + deltaY);
                            if (!visitedBuckets.contains(nearbyHash)) {
                                bucketQueue.addLast(nearbyHash);
                            }
                        }
                    }
                }
            }

            if (nearest != null) {
                result.put(i, nearest);
                DrawableVector v = m_vectors.get(i, null);
                if (v == null) {
                    v = new DrawableVector(source.x, source.y, nearest.x, nearest.y, Assets.getInstance().gui.visibilityVector, 0.03f, false);
                    m_vectors.put(i, v);
                } else {
                    v.setOrigin(source.x, source.y);
                    v.setVector(nearest.x, nearest.y);
                }
            } else {
                m_vectors.remove(i);
            }
        }
        timeSum += System.nanoTime() - startTime;
        ++counter;
        if (counter >= 60) {
            Gdx.app.log("VisibilityChecker", "Time elapsed: " + timeSum / counter / 1000000.0f);
            counter = 0;
            timeSum = 0;
        }
        return result;
    }

    public void draw(Batch batch) {
        for (DrawableVector v : m_vectors.values())
            v.draw(batch);
    }
}
