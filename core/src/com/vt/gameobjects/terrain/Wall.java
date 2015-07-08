package com.vt.gameobjects.terrain;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.vt.physics.colliders.Collidable;
import com.vt.physics.CollisionManager;
import com.vt.resources.Assets;

/**
 * Created by Fck.r.sns on 07.07.2015.
 */
public class Wall extends Tile implements Collidable {
    public Wall() {
        setType(Tile.Type.Wall);
        setTexture(Assets.getInstance().terrain.boxGreyCross);
        CollisionManager.getInstance().registerStaticCollidableObject(this);
    }

    @Override
    public Collidable.Type getColliderType() {
        return Collidable.Type.Wall;
    }

    @Override
    public Rectangle getBoundingShape() {
        return new Rectangle(getPosition().x, getPosition().y, getSize().x, getSize().y);
    }

    @Override
    public void onCollision(Collidable other) {
    }

    @Override
    public boolean checkCollision(Collidable other) {
        return other.checkShapeCollision(getBoundingShape());
    }

    @Override
    public boolean checkShapeCollision(Circle circle) {
        return Intersector.overlaps(circle, getBoundingShape());
    }

    @Override
    public boolean checkShapeCollision(Rectangle rectangle) {
        return Intersector.overlaps(getBoundingShape(), rectangle);
    }

    @Override
    public float getCenterX() {
        return getPositionCenterX();
    }

    @Override
    public float getCenterY() {
        return getPositionCenterY();
    }

    @Override
    public float getWidth() {
        return getSize().x;
    }

    @Override
    public float getHeight() {
        return getSize().y;
    }
}
