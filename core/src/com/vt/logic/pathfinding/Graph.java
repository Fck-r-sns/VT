package com.vt.logic.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fckrsns on 06.01.2016.
 */
public class Graph {
    public static class Node {
        Tile.Index index;
        public float x;
        public float y;
        public Array<DestinationNode> incidentNodes;

        public Node(Tile.Index index, float x, float y) {
            this.index = index;
            this.x = x;
            this.y = y;
            incidentNodes = new Array<DestinationNode>();
        }
    }

    public static class DestinationNode {
        public Node node;
        public float edgeWeight;
    }

    private Map<Tile.Index, Node> m_nodes = new HashMap<Tile.Index, Node>();
    private static final Color m_edgeColor = Color.BLACK;
    private static final float m_edgeWidth = 0.02f;
    private static final Color m_pathColor = Color.NAVY;
    private static final float m_pathWidth = 0.06f;

    public Map<Tile.Index, Node> getNodes() {
        return m_nodes;
    }

    public void addNode(Node node) {
        m_nodes.put(node.index, node);
    }

    public Node getNode(Tile.Index index) {
        return m_nodes.get(index);
    }

    public void draw(ShapeRenderer renderer) {
        for (Map.Entry entry : m_nodes.entrySet()) {
            Node node = (Node) entry.getValue();
            float sourceX = node.x;
            float sourceY = node.y;
            for (DestinationNode destNode : node.incidentNodes) {
                float destX = destNode.node.x;
                float destY = destNode.node.y;
                new DrawableVector(sourceX, sourceY, destX, destY, m_edgeColor, m_edgeWidth).draw(renderer);
            }
        }
    }

    public static void drawPath(ShapeRenderer renderer, List<Node> path) {
        float x1, y1;
        float x2 = Float.POSITIVE_INFINITY;
        float y2 = Float.POSITIVE_INFINITY;
        for (Node node : path) {
            x1 = x2;
            y1 = y2;
            x2 = node.x;
            y2 = node.y;
            if (x1 == Float.POSITIVE_INFINITY || y2 == Float.POSITIVE_INFINITY)
                continue;
            new DrawableVector(x1, y1, x2, y2, m_pathColor, m_pathWidth, true).draw(renderer);
        }
    }
}
