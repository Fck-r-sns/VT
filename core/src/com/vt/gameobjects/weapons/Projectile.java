package com.vt.gameobjects.weapons;

import com.badlogic.gdx.math.Vector2;
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
        CollisionManager.getInstance().registerDynamicCollidableObject(getId(), this);
    }

    public void setStartingVelocity(float vel) {
        if (getLinearVelocity().len2() == 0.0f) {
            setLinearVelocityX(1);
            setLinearVelocityY(0);
        }
        Vector2 velocity = getLinearVelocity().cpy().nor().scl(vel);
        setLinearVelocityX(velocity.x);
        setLinearVelocityY(velocity.y);
    }

    public void setStartingAngle(float angle) {
        setRotation(angle);
        if (getLinearVelocity().len2() == 0.0f) {
            setLinearVelocityX(1);
            setLinearVelocityY(0);
        }
        Vector2 velocity = getLinearVelocity().cpy().rotate(angle);
        setLinearVelocityX(velocity.x);
        setLinearVelocityY(velocity.y);
    }

    @Override
    protected void processCollisionWithWall(Collidable wall) {
        setLinearVelocityX(0);
        setLinearVelocityY(0);
        CollisionManager.getInstance().removeDynamicCollidableObject(getId());
        remove();
    }
}
