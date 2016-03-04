package com.vt.physics.raycasting;

import com.badlogic.gdx.Gdx;
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

/**
 * Created by fckrsns on 22.02.2016.
 */
public class VisibilityChecker {
    private static final int RAY_COUNT = 60;
//    private ObjectMap<Integer, Ray> m_rays = new ObjectMap<Integer, Ray>(64);
    private ObjectMap<Integer, DrawableVector> m_vectors = new ObjectMap<Integer, DrawableVector>(RAY_COUNT);
    private RayCaster m_rc = new RayCaster();
    private long timeSum = 0;
    private int counter = 0;

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

            SpatialHash currentHash = rayHash;
            Point nearest = null;
            float minRayParameter = Float.MAX_VALUE;
            int iterationCounter = 0;
            final int maxIterations = segmentsTable.getBucketCount();
            while (iterationCounter < maxIterations) {
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
                    currentHash = findNextBucket(currentHash, dir, m_rc);
                }
            }

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

    private static SpatialHash findNextBucket(SpatialHash currentHash, Vector2 dir, RayCaster rayCaster) {
        // find intersections with bounds of current bucket to find next.
        // i used such long names for variables
        // because i don't want to forget for what i created it
        final float currentBucketXLeft = currentHash.x * Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH;
        final float currentBucketXRight = (currentHash.x + 1) * Constants.SPATIAL_HASH_TABLE_BUCKET_WIDTH;
        final float currentBucketYBottom = currentHash.y * Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT;
        final float currentBucketYTop = (currentHash.y + 1) * Constants.SPATIAL_HASH_TABLE_BUCKET_HEIGHT;
        int delta = 0;
        LineSegment bucketBound;
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
                    throw new RuntimeException("No next bucket, wtf?");
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
                    throw new RuntimeException("No next bucket, wtf?");
                }
            }
        }
    }
}
