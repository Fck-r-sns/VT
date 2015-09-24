package com.vt.gameobjects.weapons;

import com.vt.game.Constants;
import com.vt.gameobjects.ActingObject;
import com.vt.physics.CollisionManager;
import com.vt.physics.colliders.Collidable;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 11.07.2015.
 */
public class Projectile extends ActingObject {
    public Projectile() {
        setTexture(Assets.getInstance().gameEntities.projectile);
        setSize(Constants.PROJECTILE_WIDTH, Constants.PROJECTILE_HEIGHT);
        setOrigin(Constants.PROJECTILE_ORIGIN_RELATIVE_X * Constants.PROJECTILE_WIDTH,
                Constants.PROJECTILE_ORIGIN_RELATIVE_Y * Constants.PROJECTILE_HEIGHT);
        setBoundingRadius(Constants.PROJECTILE_BOUNDING_RADIUS);
    }

    public void launch(float angle, float vel) {
        setRotation(angle);
        setLinearVelocityX(1);
        setLinearVelocityY(0);
        getLinearVelocity().rotate(angle);
        getLinearVelocity().nor().scl(vel);
    }

    @Override
    protected void processCollisionWithWall(Collidable wall) {
        setLinearVelocityX(0);
        setLinearVelocityY(0);
        CollisionManager.getInstance().removeDynamicCollidableObject(getId());
        setActive(false);
    }
}
