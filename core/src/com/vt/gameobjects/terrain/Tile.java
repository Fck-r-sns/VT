package com.vt.gameobjects.terrain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.vt.game.Constants;

/**
 * Created by Fck.r.sns on 22.06.2015.
 */
public abstract class Tile {
    private Vector2 m_size;
    private Vector2 m_position;
    private TextureRegion m_texture;

    protected Tile() {
        m_size = new Vector2(Constants.TILE_SIZE, Constants.TILE_SIZE);
        m_position = new Vector2();
    }

    public  void setSize(Vector2 size) {
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

    protected void setTexture(TextureRegion texture) {
        m_texture = texture;
    }

    public void update(float delta) {

    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(m_texture, m_position.x, m_position.y, m_size.x, m_size.y);
    }
}
