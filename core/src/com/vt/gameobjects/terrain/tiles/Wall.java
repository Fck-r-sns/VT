package com.vt.gameobjects.terrain.tiles;

import com.vt.physics.CollisionManager;
import com.vt.physics.colliders.Collidable;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 07.07.2015.
 */
public class Wall extends Tile {
    public Wall() {
        m_passable = false;
        setTexture(Assets.getInstance().terrain.simpleWall);
        CollisionManager.getInstance().registerStaticCollidableObject(getId(), this);
    }

    @Override
    public Collidable.Type getColliderType() {
        return Collidable.Type.Wall;
    }

    @Override
    public void onCollision(Collidable other) {
    }
}
