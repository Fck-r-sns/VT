package com.vt.logic.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;
import com.vt.resources.Assets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fckrsns on 06.01.2016.
 */
public class Graph {
    public static class Vertex {
        Tile.Index index;
        public float x;
        public float y;
        public Array<Edge> incidentEdges;

        public Vertex(Tile.Index index, float x, float y) {
            this.index = index;
            this.x = x;
            this.y = y;
            incidentEdges = new Array<Edge>();
        }
    }

    public static class Edge {
        public Vertex destination;
        public float weight;
    }

    private Map<Tile.Index, Vertex> m_nodes = new HashMap<Tile.Index, Vertex>();
    private static final Color m_edgeColor = Color.BLACK;
    private static final float m_edgeWidth = 0.03f;
    private static final Color m_pathColor = Color.NAVY;
    private static final float m_pathWidth = 0.08f;

    public Map<Tile.Index, Vertex> getVertices() {
        return m_nodes;
    }

    public void addVertex(Vertex vertex) {
        m_nodes.put(vertex.index, vertex);
    }

    public Vertex getVertex(Tile.Index index) {
        return m_nodes.get(index);
    }

    public int getOrder() {
        return m_nodes.size();
    }

    public void draw(SpriteBatch batch) {
        for (Map.Entry entry : m_nodes.entrySet()) {
            Vertex vertex = (Vertex) entry.getValue();
            float sourceX = vertex.x;
            float sourceY = vertex.y;
            for (Edge edge : vertex.incidentEdges) {
                float destX = edge.destination.x;
                float destY = edge.destination.y;
                new DrawableVector(sourceX, sourceY, destX, destY, Assets.getInstance().gui.blueVector, m_edgeWidth).draw(batch);
            }
        }
    }

    public static void drawPath(SpriteBatch batch, List<Vertex> path) {
        float x1, y1;
        float x2 = Float.POSITIVE_INFINITY;
        float y2 = Float.POSITIVE_INFINITY;
        for (Vertex vertex : path) {
            x1 = x2;
            y1 = y2;
            x2 = vertex.x;
            y2 = vertex.y;
            if (x1 == Float.POSITIVE_INFINITY || y2 == Float.POSITIVE_INFINITY)
                continue;
            new DrawableVector(x1, y1, x2, y2, Assets.getInstance().gui.redVector, m_pathWidth, true).draw(batch);
        }
    }
}
