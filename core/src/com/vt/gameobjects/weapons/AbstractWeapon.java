package com.vt.gameobjects.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.vt.gameobjects.GameObject;

/**
 * Created by Fck.r.sns on 25.07.2015.
 */
public abstract class AbstractWeapon extends GameObject implements Shootable {
    private Array<Projectile> m_projectilesPool;
    private int m_projectileIndex = 0;

    public void rotate(float rotation) {
        super.setRotation(rotation);
        Vector2 rotated = new Vector2(-getOriginX(), -getOriginY());
        rotated.rotate(rotation);
        rotated.add(getPosition());
        setPosition(rotated.x, rotated.y);
    }

    protected void setProjectilesPool(Array<Projectile> pool) {
        m_projectilesPool = pool;
    }

    protected Projectile getProjectileFromPool() {
        Projectile p = m_projectilesPool.get(m_projectileIndex);
        ++m_projectileIndex;
        if (m_projectileIndex >= m_projectilesPool.size)
            m_projectileIndex = 0;
        return p;
    }
}
