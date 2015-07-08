package com.vt.gameobjects.terrain;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fck.r.sns on 26.06.2015.
 */
public abstract class AbstractLevel {
    protected HashMap<Vector2, Tile> m_tiles;

    protected AbstractLevel() {
        m_tiles = new HashMap<Vector2, Tile>();
    }

    protected void setTile(Vector2 position, Tile tile) {
        m_tiles.put(position, tile);
    }

    public void update(float delta) {
        for (Map.Entry tile : m_tiles.entrySet())
            ((Tile)tile.getValue()).update(delta);
    }

    public void draw(SpriteBatch spriteBatch) {
        for (Map.Entry tile : m_tiles.entrySet())
            ((Tile)tile.getValue()).draw(spriteBatch);
    }
}
