package com.vt.gameobjects.terrain.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fck.r.sns on 26.06.2015.
 */
public abstract class AbstractLevel {
    private Array<Vector2> m_enemies;
    private Vector2 m_playerPosition;
    protected HashMap<Vector2, Tile> m_tiles;

    protected AbstractLevel() {
        m_enemies = new Array<Vector2>(16);
        m_playerPosition = new Vector2(0, 0);
        m_tiles = new HashMap<Vector2, Tile>();
    }

    protected void setPlayerPosition(float x, float y) {
        m_playerPosition.set(x, y);
    }

    public Vector2 getPlayerPosition() {
        return m_playerPosition;
    }

    protected void addEnemyPosition(float x, float y) {
        m_enemies.add(new Vector2(x, y));
    }

    public int getEnemiesCount() {
        return m_enemies.size;
    }

    public Vector2 getEnemyPosition(int enemyIdx) {
        return m_enemies.get(enemyIdx);
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
