package com.vt.physics;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.vt.physics.colliders.Collidable;
import com.vt.physics.geometry.LineSegment;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public class CollisionManager {
    private static CollisionManager instance = null;
    private ObjectSet<LineSegment> m_staticLineSegments;
    private ObjectMap<Integer, Collidable> m_staticCollidables;
    private ObjectMap<Integer, Collidable> m_dynamicCollidables;

    public static CollisionManager getInstance() {
        if (instance == null)
            instance = new CollisionManager();
        return instance;
    }

    private CollisionManager() {
        m_staticCollidables = new ObjectMap<Integer, Collidable>(32);
        m_dynamicCollidables = new ObjectMap<Integer, Collidable>(32);
    }

    public void update(float delta) {
        int sizeDynamics = m_dynamicCollidables.size;
        Array<Collidable> dynamicCollidablesArray = m_dynamicCollidables.values().toArray();
        for (int firstIdx = 0; firstIdx < sizeDynamics; ++firstIdx) {
            Collidable first = dynamicCollidablesArray.get(firstIdx);
            for (int secondIdx = firstIdx + 1; secondIdx < sizeDynamics; ++secondIdx) {
                Collidable second = dynamicCollidablesArray.get(secondIdx);
                if (first.checkCollision(second)) {
                    first.onCollision(second);
                    second.onCollision(first);
                }
            }
            for (Collidable second : m_staticCollidables.values()) {
                if (first.checkCollision(second)) {
                    first.onCollision(second);
                    second.onCollision(first);
                }
            }
        }
    }

    public void registerStaticCollidableObject(Integer key, Collidable object) {
        m_staticCollidables.put(key, object);
    }

    public void removeStaticCollidableObject(Integer key) {
        Collidable c = m_staticCollidables.remove(key);
    }

    public void registerDynamicCollidableObject(Integer key, Collidable object) {
        m_dynamicCollidables.put(key, object);
    }

    public void removeDynamicCollidableObject(Integer key) {
        m_dynamicCollidables.remove(key);
    }

    public void setStaticLineSegments(ObjectSet<LineSegment> segments) {
        m_staticLineSegments = segments;
    }

    public ObjectSet<LineSegment> getStaticLineSegments() {
        return m_staticLineSegments;
    }
}
