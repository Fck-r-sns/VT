package com.vt.gameobjects.terrain.levels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.vt.gameobjects.terrain.tiles.Tile;
import com.vt.logic.pathfinding.Graph;

import java.util.Map;

/**
 * Created by Fck.r.sns on 26.06.2015.
 */
public abstract class AbstractLevel {
    private Array<Vector2> m_enemies;
    private Vector2 m_playerPosition;
    protected ObjectMap<Tile.Index, Tile> m_tiles;

    protected AbstractLevel() {
        m_enemies = new Array<Vector2>(16);
        m_playerPosition = new Vector2(0, 0);
        m_tiles = new ObjectMap<Tile.Index, Tile>();
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

    protected void setTile(Tile.Index index, Tile tile) {
        m_tiles.put(index, tile);
    }

    public void update(float delta) {
        for (ObjectMap.Entry<Tile.Index, Tile> tile : m_tiles)
            tile.value.update(delta);
    }

    public void draw(SpriteBatch spriteBatch) {
        for (ObjectMap.Entry<Tile.Index, Tile> tile : m_tiles)
            tile.value.draw(spriteBatch, 0.0f);
    }

    public Graph createGraph() {
        Graph g = new Graph();
        // add vertices
        for (ObjectMap.Entry<Tile.Index, Tile> entry : m_tiles) {
            Tile tile = entry.value;
            if (tile.isPassable()) {
                Tile.Index index = entry.key;
                Graph.Vertex vertex = new Graph.Vertex(index, tile.getX(Align.center), tile.getY(Align.center));
                g.addVertex(vertex);
            }
        }

        // add edges
        for (Map.Entry entry : g.getVertices().entrySet()) {
            Tile.Index index = (Tile.Index) entry.getKey();
            Graph.Vertex vertex = (Graph.Vertex) entry.getValue();
            // iterate for adjacent tiles
            final int offsets[] = {-1, 0, 1};
            for (int deltaX : offsets) {
                for (int deltaY : offsets) {
                    if (deltaX == 0 && deltaY == 0)
                        continue; // this is the same tile, not adjacent
                    if (Math.abs(deltaX) + Math.abs(deltaY) == 2) {
                        Tile.Index key1 = new Tile.Index(index.x + deltaX, index.y);
                        Tile.Index key2 = new Tile.Index(index.x, index.y + deltaY);

                        if (!m_tiles.containsKey(key1) || !m_tiles.get(key1).isPassable()
                                || !m_tiles.containsKey(key2) || !m_tiles.get(key2).isPassable())
                            continue;
                    }
                    Tile.Index adjacentIndex = new Tile.Index(index.x + deltaX, index.y + deltaY);
                    if (m_tiles.containsKey(adjacentIndex)) {
                        Tile adjacentTile = m_tiles.get(adjacentIndex);
                        if (adjacentTile.isPassable()) {
                            Graph.Edge destNode = new Graph.Edge();
                            destNode.destination = g.getVertex(adjacentIndex);
                            Tile currentTile = m_tiles.get(index);
                            float dX = adjacentTile.getX(Align.center) - currentTile.getX(Align.center);
                            float dY = adjacentTile.getY(Align.center) - currentTile.getY(Align.center);
                            destNode.weight = (float) Math.sqrt(dX * dX + dY * dY);
                            vertex.incidentEdges.add(destNode);
                        }
                    }
                }
            }
        }
        return g;
    }
}
