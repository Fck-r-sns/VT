package com.vt.physics;

import com.badlogic.gdx.utils.Array;
import com.vt.physics.colliders.Collidable;

/**
 * Created by Fck.r.sns on 08.07.2015.
 */
public class CollisionManager {
    private static CollisionManager instance = null;
    private Array<Collidable> m_staticCollidables = new Array<Collidable>(32);
    private Array<Collidable> m_dynamicCollidables = new Array<Collidable>(32);

    public static CollisionManager getInstance() {
        if (instance == null)
            instance = new CollisionManager();
        return instance;
    }

    private CollisionManager() {
    }

    public void update(float delta) {
        int sizeStatics = m_staticCollidables.size;
        int sizeDynamics = m_dynamicCollidables.size;
        for (int firstIdx = 0; firstIdx < sizeDynamics; ++firstIdx) {
            Collidable first = m_dynamicCollidables.get(firstIdx);
            for (int secondIdx = firstIdx + 1; secondIdx < sizeDynamics; ++secondIdx) {
                Collidable second = m_dynamicCollidables.get(secondIdx);
                if (first.checkCollision(second)) {
                    first.onCollision(second);
                    second.onCollision(first);
                }
            }
            for (int secondIdx = 0; secondIdx < sizeStatics; ++secondIdx) {
                Collidable second = m_staticCollidables.get(secondIdx);
                if (first.checkCollision(second)) {
                    first.onCollision(second);
                    second.onCollision(first);
                }
            }
        }
    }

    public void registerStaticCollidableObject(Collidable object) {
        m_staticCollidables.add(object);
    }

    public void registerDynamicCollidableObject(Collidable object) {
        m_dynamicCollidables.add(object);
    }
}
