package com.vt.logic.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                return (getClass() == obj.getClass())
                        && vertex.equals(((MarkedVertex) obj).vertex);
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
        Set<Tile.Index> processed = new HashSet<Tile.Index>();
        HashMap<Tile.Index, Graph.Vertex> previous = new HashMap<Tile.Index, Graph.Vertex>();

        // calculate path costs
        boolean goalAchieved = false;
        dist.add(start.index, new MarkedVertex(start, 0));
        while (!dist.isEmpty()) {
            MarkedVertex v = dist.first();
            dist.remove(v.vertex.index);
            processed.add(v.vertex.index);
            if (vectors.containsKey(v.vertex.index))
                variations.add(vectors.get(v.vertex.index));
            if (v.vertex == goal) {
                goalAchieved = true;
                break;
            }
            for (Graph.Edge e : v.vertex.incidentEdges) {
                Graph.Vertex v2 = e.destination;
                if (processed.contains(v2.index))
                    continue;
                float d = v.distance + e.weight;
                if (!dist.containsKey(v2.index)) {
                    dist.add(v2.index, new MarkedVertex(v2, d));
                    previous.put(v2.index, v.vertex);
                    vectors.put(v2.index, new DrawableVector(v.vertex.x, v.vertex.y, v2.x, v2.y, Color.RED, 0.05f, true));
                } else {
                    MarkedVertex vertex = dist.take(v2.index);
                    if (vertex.distance > d) {
                        vertex.distance = d;
                        previous.put(v2.index, v.vertex);
                        vectors.put(v2.index, new DrawableVector(v.vertex.x, v.vertex.y, v2.x, v2.y, Color.RED, 0.05f, true));
                    }
                    dist.add(v2.index, vertex);
                }
            }
        }

        if (!goalAchieved)
            return null;

        // get optimal path
        Graph.Vertex v = goal;
        List<Graph.Vertex> path = new ArrayList<Graph.Vertex>();
        path.add(goal);
        while (v.index != start.index) {
            v = previous.get(v.index);
            path.add(v);
        }

        Collections.reverse(path);
        return path;
    }
}
