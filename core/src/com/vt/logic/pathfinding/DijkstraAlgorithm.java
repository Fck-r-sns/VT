package com.vt.logic.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by fckrsns on 06.01.2016.
 */
public class DijkstraAlgorithm extends Pathfinder {
    private Graph m_graph;

    public DijkstraAlgorithm(Graph graph) {
        m_graph = graph;
    }

    @Override
    public List<Graph.Vertex> findPath(Graph.Vertex start, Graph.Vertex goal) {
        class MarkedVertex {
            Graph.Vertex vertex;
            float distance;

            MarkedVertex(Graph.Vertex vertex, float distance) {
                this.vertex = vertex;
                this.distance = distance;
            }

            @Override
            public boolean equals(Object obj) {
                return vertex.equals(obj);
            }
        }

        Comparator<MarkedVertex> cmp = new Comparator<MarkedVertex>() {
            @Override
            public int compare(MarkedVertex o1, MarkedVertex o2) {
                // ignore different distances for same vertex
                if (o1.vertex == o2.vertex)
                    return 0;
                int value = (int) Math.signum(o1.distance - o2.distance);
                if (value == 0)
                    return o1.vertex.hashCode() - o2.vertex.hashCode();
                return value;
            }
        };

        IndexedPriorityQueue<Tile.Index, MarkedVertex> dist = new IndexedPriorityQueue<Tile.Index, MarkedVertex>(cmp);
        HashMap<Tile.Index, Float> result = new HashMap<Tile.Index, Float>();

        // calculate path costs
        boolean goalAchieved = false;
        dist.add(start.index, new MarkedVertex(start, 0));
        while (!dist.isEmpty()) {
            MarkedVertex v = dist.first();
            dist.remove(v.vertex.index);
            result.put(v.vertex.index, v.distance);
            if (vectors.containsKey(v.vertex.index))
                variations.add(vectors.get(v.vertex.index));
            if (v.vertex == goal) {
                goalAchieved = true;
                break;
            }
            for (Graph.Edge e : v.vertex.incidentEdges) {
                Graph.Vertex v2 = e.destination;
                if (result.containsKey(v2.index))
                    continue;
                float d = v.distance + e.weight;
                if (!dist.containsKey(v2.index)) {
                    dist.add(v2.index, new MarkedVertex(v2, d));
                } else {
                    MarkedVertex vertex = dist.take(v2.index);
                    vertex.distance = Math.min(vertex.distance, d);
                    dist.add(v2.index, vertex);
                }
                vectors.put(v2.index, new DrawableVector(v.vertex.x, v.vertex.y, v2.x, v2.y, Color.RED, 0.05f, true));
            }
        }

        if (!goalAchieved)
            return null;

        // get optimal path
        Graph.Vertex currentVertex = goal;
        List<Graph.Vertex> path = new ArrayList<Graph.Vertex>();
        path.add(goal);
        while (currentVertex.index != start.index) {
            Float min = Float.MAX_VALUE;
            Tile.Index minIndex = null;
            for (Graph.Edge e : currentVertex.incidentEdges) {
                Tile.Index index = e.destination.index;
                float weight = result.get(index) + e.weight;
                if (weight < min) {
                    min = weight;
                    minIndex = index;
                }
            }
            if (minIndex == null)
                return null;
            currentVertex = m_graph.getVertex(minIndex);
            path.add(currentVertex);
        }

        Collections.reverse(path);
        return path;
    }
}
