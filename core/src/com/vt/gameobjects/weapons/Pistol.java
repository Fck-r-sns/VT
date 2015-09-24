package com.vt.gameobjects.weapons;

import com.badlogic.gdx.utils.Array;
import com.vt.game.Constants;
import com.vt.game.Environment;
import com.vt.physics.CollisionManager;

/**
 * Created by Fck.r.sns on 06.08.2015.
 */
public class Pistol extends AbstractWeapon {
    public Pistol() {
        setOrigin(-Constants.PISTOL_SHOOTING_POINT_X, -Constants.PISTOL_SHOOTING_POINT_Y);
        final int POOL_SIZE = 16;
        Array<Projectile> pool = new Array<Projectile>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; ++i) {
            Projectile p = new Projectile();
            p.setPosition(-100500, -100500);
            p.setActive(false);
            Environment.getInstance().currentStage.addActor(p);
            pool.add(p);
        }
        setProjectilesPool(pool);
    }

    @Override
    public void shoot() {
        Projectile p = getProjectileFromPool();
        p.setPosition(getX(), getY(), Constants.ALIGN_ORIGIN);
        p.launch(getRotation(), 15);
        CollisionManager.getInstance().registerDynamicCollidableObject(p.getId(), p);
        p.setActive(true);
    }

    @Override
    public void reload() {

    }
}
