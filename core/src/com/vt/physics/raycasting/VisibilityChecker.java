package com.vt.physics.raycasting;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.badlogic.gdx.utils.OrderedMap;
import com.vt.game.Constants;
import com.vt.gameobjects.primitives.DrawableVector;
import com.vt.physics.CollisionManager;
import com.vt.physics.SpatialHash;
import com.vt.physics.SpatialHashTable;
import com.vt.physics.geometry.LineSegment;
import com.vt.physics.geometry.Point;
import com.vt.physics.geometry.Ray;
import com.vt.resources.Assets;
import com.vt.utils.DumbProfiler;

/**
 * Created by fckrsns on 22.02.2016.
 */
public class VisibilityChecker {
    private static final int RAY_COUNT = 30;
    //    private ObjectMap<Integer, Ray> m_rays = new ObjectMap<Integer, Ray>(64);
    private ObjectMap<Integer, DrawableVector> m_vectors = new ObjectMap<Integer, DrawableVector>(RAY_COUNT);
    private RayCaster m_rc = new RayCaster();

    private DumbProfiler m_profiler = new DumbProfiler("VisibilityChecker", 60);

    {
        m_profiler.setEnabled(true);
    }

//    public OrderedMap<Integer, Point> updateVisibilityZone(Point source, float centerAngle_deg, float rangeAngle_deg) {
//        long startTime = System.nanoTime();
//        SpatialHashTable<LineSegment> segmentsTable = CollisionManager.getInstance().getStaticLineSegments();
//        OrderedMap<Integer, Point> result = new OrderedMap<Integer, Point>(RAY_COUNT);
//        SpatialHash rayHash = SpatialHash.createFromPosition(source.x, source.y);
//
//        ObjectSet<SpatialHash> visitedOrQueued = new ObjectSet<SpatialHash>(32);
//        Queue<SpatialHash> queue = new Queue<SpatialHash>(32);
//        queue.addLast(rayHash);
//
//        m_vectors.clear();
//        int counter = 0;
//        while (queue.size > 0) {
//            SpatialHash outerCurrentHash = queue.removeFirst();
//            visitedOrQueued.add(outerCurrentHash);
//            {
//                m_rays.clear();
//                Vector2 dir;
//                ObjectSet<LineSegment> bucket = segmentsTable.getBucket(outerCurrentHash);
//                if (bucket != null) {
//                    for (LineSegment s : bucket) {
//                        dir = new Vector2(s.p1.x - source.x, s.p1.y - source.y);
//                        m_rays.put(counter++, new Ray(source, dir.cpy().rotate(-0.1f)));
//                        m_rays.put(counter++, new Ray(source, dir.cpy().rotate(+0.1f)));
//                        dir = new Vector2(s.p2.x - source.x, s.p2.y - source.y);
//                        m_rays.put(counter++, new Ray(source, dir.cpy().rotate(-0.1f)));
//                        m_rays.put(counter++, new Ray(source, dir.cpy().rotate(+0.1f)));
//                    }
//                }
//            }
//
//            for (ObjectMap.Entry<Integer, Ray> entry : m_rays.entries()) {
//                int id = entry.key;
//                Ray ray = entry.value;
//                m_rc.setRay(ray);
//                SpatialHash currentHash = rayHash;
//                Point nearest = null;
//                float minRayParameter = Float.MAX_VALUE;
//                int iterationCounter = 0;
//                final int maxIterations = segmentsTable.getBucketCount();
//                while (iterationCounter < maxIterations) {
//                    ObjectSet<LineSegment> segmentsBucket = segmentsTable.getBucket(currentHash);
//                    if (segmentsBucket == null) {
//                        continue;
//                    }
//                    for (LineSegment s : segmentsBucket) {
//                        Point newIntersection = m_rc.findIntersection(s);
//                        float newRayParameter = m_rc.getRayParameter();
//                        if (newIntersection != null && newRayParameter < minRayParameter) {
//                            nearest = newIntersection;
//                            minRayParameter = newRayParameter;
//                        }
//                    }
//                    if (nearest != null) {
//                        // found
//                        break;
//                    } else {
//                        currentHash = findNextBucket(currentHash, ray.direction, m_rc);
//                    }
//                }
//
//                result.put(id, nearest);
//                DrawableVector v = m_vectors.get(id, null);
//                if (v == null) {
//                    v = new DrawableVector(source.x, source.y, nearest.x, nearest.y, Assets.getInstance().gui.visibilityVector, 0.03f, false);
//                    m_vectors.put(id, v);
//                } else {
//                    v.setOrigin(source.x, source.y);
//                    v.setVector(nearest.x, nearest.y);
//                }
//            }
//
//
//        }
//        timeSum += System.nanoTime() - startTime;
//        ++frameCounter;
//        if (frameCounter >= 60) {
//            Gdx.app.log("VisibilityChecker", "Time elapsed: " + timeSum / frameCounter / 1000000.0f);
//            frameCounter = 0;
//            timeSum = 0;
//        }
//        return result;
//    }

    public OrderedMap<Integer, Point> updateVisibilityZone(Point source, float centerAngle_deg, float rangeAngle_deg) {
        m_profiler.start();
        SpatialHashTable<LineSegment> segmentsTable = CollisionManager.getInstance().getStaticLineSegments();
        OrderedMap<Integer, Point> result = new OrderedMap<Integer, Point>(RAY_COUNT);
        Vector2 dir = new Vector2(1, 0).rotate(centerAngle_deg - rangeAngle_deg / 2);
        Ray ray = new Ray(source, dir);
        SpatialHash rayHash = SpatialHash.createFromPosition(source.x, source.y);
        m_rc.setRay(ray);
        final float ROTATION_STEP = rangeAngle_deg / RAY_COUNT;
        final int maxIterations = segmentsTable.getBucketCount();
        for (int i = 0; i < RAY_COUNT; ++i) {
            dir.rotate(ROTATION_STEP);
            SpatialHash currentHash = rayHash;
            Point nearest = null;
            float minRayParameter = Float.MAX_VALUE;
            int iterationCounter = 0;
            while (iterationCounter < maxIterations) {
                ++iterationCounter;

                ObjectSet<LineSegment> segmentsBucket = segmentsTable.getBucket(currentHash);
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
                    currentHash = findNextBucket(currentHash, dir, m_rc);
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
            }
        }
        m_profiler.process();
        return result;
    }

    public void draw(Batch batch) {
        for (DrawableVector v : m_vectors.values())
            v.draw(batch);
    }

    private static SpatialHash findNextBucket(SpatialHash currentHash, Vector2 dir, RayCaster rayCaster) {
        // find intersections with bounds of current bucket to find next.
        final float currentBucketXLeft = currentHash.x * Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH;
        final float currentBucketXRight = (currentHash.x + 1) * Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH;
        final float currentBucketYBottom = currentHash.y * Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT;
        final float currentBucketYTop = (currentHash.y + 1) * Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT;
        int delta = 0;
        LineSegment bucketBound;
        // use heuristics to determine which bound should be checked first
        if (Math.abs(dir.x) > Math.abs(dir.y)) {
            // then ray points mostly left or right
            if (dir.x < 0) {
                bucketBound = new LineSegment(
                        new Point(currentBucketXLeft, currentBucketYBottom),
                        new Point(currentBucketXLeft, currentBucketYTop)
                );
                delta = -1;
            } else {
                bucketBound = new LineSegment(
                        new Point(currentBucketXRight, currentBucketYBottom),
                        new Point(currentBucketXRight, currentBucketYTop)
                );
                delta = +1;
            }
            if (null != rayCaster.findIntersection(bucketBound)) {
                return new SpatialHash(currentHash.x + delta, currentHash.y);
            } else {
                if (dir.y < 0) {
                    bucketBound = new LineSegment(
                            new Point(currentBucketXLeft, currentBucketYBottom),
                            new Point(currentBucketXRight, currentBucketYBottom)
                    );
                    delta = -1;
                } else {
                    bucketBound = new LineSegment(
                            new Point(currentBucketXLeft, currentBucketYTop),
                            new Point(currentBucketXRight, currentBucketYTop)
                    );
                    delta = +1;
                }
                if (null != rayCaster.findIntersection(bucketBound)) {
                    return new SpatialHash(currentHash.x, currentHash.y + delta);
                } else {
                    return null;
                }
            }
        } else {
            // then ray points mostly down or up
            if (dir.y < 0) {
                bucketBound = new LineSegment(
                        new Point(currentBucketXLeft, currentBucketYBottom),
                        new Point(currentBucketXRight, currentBucketYBottom)
                );
                delta = -1;
            } else {
                bucketBound = new LineSegment(
                        new Point(currentBucketXLeft, currentBucketYTop),
                        new Point(currentBucketXRight, currentBucketYTop)
                );
                delta = +1;
            }
            if (null != rayCaster.findIntersection(bucketBound)) {
                return new SpatialHash(currentHash.x, currentHash.y + delta);
            } else {
                if (dir.x < 0) {
                    bucketBound = new LineSegment(
                            new Point(currentBucketXLeft, currentBucketYBottom),
                            new Point(currentBucketXLeft, currentBucketYTop)
                    );
                    delta = -1;
                } else {
                    bucketBound = new LineSegment(
                            new Point(currentBucketXRight, currentBucketYBottom),
                            new Point(currentBucketXRight, currentBucketYTop)
                    );
                    delta = +1;
                }
                if (null != rayCaster.findIntersection(bucketBound)) {
                    return new SpatialHash(currentHash.x + delta, currentHash.y);
                } else {
                    return null;
                }
            }
        }
    }
}
