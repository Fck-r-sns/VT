package com.vt.gameobjects.terrain.tiles;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.physics.colliders.Collidable;

import static com.badlogic.gdx.scenes.scene2d.utils.Align.bottom;
import static com.badlogic.gdx.scenes.scene2d.utils.Align.left;
import static com.badlogic.gdx.scenes.scene2d.utils.Align.right;
import static com.badlogic.gdx.scenes.scene2d.utils.Align.top;

/**
 * Created by Fck.r.sns on 22.06.2015.
 */
public class Tile extends GameObject implements Collidable {
    public enum Type {
        Unknown,
        Floor,
        Wall
    }

    private Vector2 m_boundingRect;
    private Type m_type;

    protected Tile() {
        setSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
        setPosition(0, 0);
        setOrigin(Align.center);
        m_boundingRect = new Vector2(Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    @Override
    public Collidable.Type getColliderType() {
        return Collidable.Type.Unknown;
    }

    @Override
    public Rectangle getBoundingShape() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
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
        return Intersector.overlaps(rectangle, getBoundingShape());
    }
}
