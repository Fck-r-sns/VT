package com.vt.gameobjects.terrain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.vt.game.Constants;
import com.vt.physics.colliders.Collidable;

import static com.badlogic.gdx.scenes.scene2d.utils.Align.bottom;
import static com.badlogic.gdx.scenes.scene2d.utils.Align.left;
import static com.badlogic.gdx.scenes.scene2d.utils.Align.right;
import static com.badlogic.gdx.scenes.scene2d.utils.Align.top;

/**
 * Created by Fck.r.sns on 22.06.2015.
 */
public class Tile implements Collidable {
    public enum Type {
        Unknown,
        Floor,
        Wall
    }

    private Vector2 m_position;
    private Vector2 m_size;
    private Vector2 m_boundingRect;
    private TextureRegion m_texture;
    private Type m_type;

    protected Tile() {
        m_size = new Vector2(Constants.TILE_SIZE, Constants.TILE_SIZE);
        m_position = new Vector2(0, 0);
        m_boundingRect = new Vector2(Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public void update(float delta) {
    }

    public void draw(SpriteBatch batch) {
        if (m_texture == null)
            return;
        batch.draw(m_texture, getX(), getY(), getWidth(), getHeight());
    }

    protected void setTexture(TextureRegion texture) {
        m_texture = texture;
    }

    @Override
    public Vector2 getPosition() {
        return m_position;
    }

    @Override
    public void setPosition(float x, float y) {
        m_position.set(x, y);
    }

    @Override
    public float getX() {
        return m_position.x;
    }

    @Override
    public float getX(int alignment) {
        float x = getX();
        if ((alignment & right) != 0)
            x += getWidth();
        else if ((alignment & left) == 0)
            x += getWidth() / 2;
        return x;
    }

    @Override
    public float getY() {
        return m_position.y;
    }

    @Override
    public float getY(int alignment) {
        float y = getY();
        if ((alignment & top) != 0)
            y += getHeight();
        else if ((alignment & bottom) == 0)
            y += getHeight() / 2;
        return y;
    }

    @Override
    public float getWidth() {
        return m_size.x;
    }

    @Override
    public float getHeight() {
        return m_size.y;
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
