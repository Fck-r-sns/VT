package com.vt.logic.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fckrsns on 06.01.2016.
 */
public class Graph {
    public static class Vertex {
        Tile.Index index;
        public float x;
        public float y;

        public Vertex(Tile.Index index, float x, float y) {
            this.index = index;

            this.x = x;
            this.y = y;
        }
    }

    public static class Node {
        public Vertex vertex;
        public Array<DestinationNode> incidentNodes = new Array<DestinationNode>();
    }

    public static class DestinationNode {
        public Node node;
        public float edgeWeight;
    }

    private Map<Tile.Index, Node> m_nodes = new HashMap<Tile.Index, Node>();
    private Color m_edgeColor = Color.BLACK;
    private float m_edgeWidth = 0.02f;

    public Map<Tile.Index, Node> getNodes() {
        return m_nodes;
    }

    public void addNode(Node node) {
        m_nodes.put(node.vertex.index, node);
    }

    public Node getNode(Tile.Index index) {
        return m_nodes.get(index);
    }

    public void draw(ShapeRenderer renderer) {
        for (Map.Entry entry : m_nodes.entrySet()) {
            Node node = (Node) entry.getValue();
            float sourceX = node.vertex.x;
            float sourceY = node.vertex.y;
            for (DestinationNode destNode : node.incidentNodes) {
                float destX = destNode.node.vertex.x;
                float destY = destNode.node.vertex.y;
                new DrawableVector(sourceX, sourceY, destX, destY, m_edgeColor, m_edgeWidth).draw(renderer);
            }
        }
    }
}
