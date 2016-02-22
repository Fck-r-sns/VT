package com.vt.gameobjects.terrain.tiles;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.vt.game.Constants;
import com.vt.gameobjects.GameObject;
import com.vt.physics.colliders.Collidable;
import com.vt.physics.geometry.LineSegment;
import com.vt.physics.geometry.Point;

/**
 * Created by Fck.r.sns on 22.06.2015.
 */
public class Tile extends GameObject implements Collidable {
    public enum Type {
        Unknown,
        Floor,
        Wall
    }

    public static class Index {
        public int x;
        public int y;

        public Index(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setValue(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x + 31 * y;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null)
                return false;
            if (this.getClass() != obj.getClass())
                return false;
            Index other = (Index) obj;
            return x == other.x && y == other.y;
        }

        @Override
        public String toString() {
            return "(" + x + ";" + y + ")";
        }
    }

    private Vector2 m_boundingRect;
    private Type m_type;
    protected boolean m_passable;


    protected Tile(float x, float y) {
        setSize(Constants.TILE_SIZE, Constants.TILE_SIZE);
        setPosition(x, y);
        setOrigin(Align.center);
        m_boundingRect = new Vector2(Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public boolean isPassable() {
        return m_passable;
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
