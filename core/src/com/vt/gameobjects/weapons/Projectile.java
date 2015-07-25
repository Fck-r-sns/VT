package com.vt.gameobjects.weapons;

import com.vt.game.Constants;
import com.vt.gameobjects.ActingGameObject;
import com.vt.physics.colliders.Collidable;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 11.07.2015.
 */
public class Projectile extends ActingGameObject {
    public Projectile() {
        setTexture(Assets.getInstance().gameEntities.projectile);
        setSize(Constants.PROJECTILE_WIDTH, Constants.PROJECTILE_HEIGHT);
    }

    public void setStartingVelocity(float vel) {
        if (m_linearVelocity.len2() == 0.0f)
            m_linearVelocity.set(1, 0);
        m_linearVelocity.nor().scl(vel);
    }

    public void setStartingAngle(float angle) {
        setRotation(angle);
        if (m_linearVelocity.len2() == 0.0f)
            m_linearVelocity.set(1, 0);
        m_linearVelocity.rotate(angle);
    }

    @Override
    protected void processCollisionWithWall(Collidable wall) {
        m_linearVelocity.setZero();
        setVisible(false);
    }
}
