package com.vt.gameobjects.terrain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vt.game.Constants;

/**
 * Created by Fck.r.sns on 22.06.2015.
 */
public abstract class Tile {
    public enum Type {
        Unknown,
        Floor,
        Wall
    }
    private Vector2 m_size;
    private Vector2 m_position;
    private Vector2 m_boundingRect;
    private boolean m_collidable;
    private TextureRegion m_texture;
    private Type m_type;

    protected Tile() {
        m_size = new Vector2(Constants.TILE_SIZE, Constants.TILE_SIZE);
        m_position = new Vector2();
        m_boundingRect = new Vector2(Constants.TILE_SIZE, Constants.TILE_SIZE);
        m_collidable = false;
        m_type = Type.Unknown;
    }

    public void setSize(Vector2 size) {
        m_size = size;
    }

    public void setSize(float width, float height) {
        m_size.set(width, height);
    }

    Vector2 getSize() {
        return m_size;
    }

    public void setPosition(Vector2 pos) {
        m_position = pos;
    }

    public void setPosition(float x, float y) {
        m_position.set(x, y);
    }

    public Vector2 getPosition() {
        return m_position;
    }

    public void setBoundinRect(Vector2 rect) {
        m_boundingRect = rect;
    }

    public void setBoundinRect(float x, float y) {
        m_boundingRect.set(x, y);
    }

    public Vector2 getBoundingRect() {
        return m_boundingRect;
    }

    protected void setTexture(TextureRegion texture) {
        m_texture = texture;
    }

    void setType(Type type) {
        m_type = type;
    }

    Type getType() {
        return m_type;
    }

    public void update(float delta) {
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(m_texture, m_position.x, m_position.y, m_size.x, m_size.y);
    }
}
