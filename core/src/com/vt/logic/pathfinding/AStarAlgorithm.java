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
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by fckrsns on 06.01.2016.
 */
public class AStarAlgorithm extends Pathfinder {
    public interface Heuristic {
        float calculate(Graph.Vertex vertex, Graph.Vertex targetVertex);
    }

    private Graph m_graph;
    private Heuristic m_heuristic;

    public AStarAlgorithm(Graph graph, Heuristic heuristic) {
        m_graph = graph;
        m_heuristic = heuristic;
    }

    @Override
    public List<Graph.Vertex> findPath(Graph.Vertex start, Graph.Vertex goal) {
        class MarkedVertex {
            Graph.Vertex vertex;
            float distance;
            float heuristic;

            MarkedVertex(Graph.Vertex vertex, float distance, float heuristic) {
                this.vertex = vertex;
                this.distance = distance;   // g
                this.heuristic = heuristic; // h
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
                int value = (int) Math.signum((o1.distance + o1.heuristic) - (o2.distance + o2.heuristic));
                if (value == 0)
                    return o1.vertex.hashCode() - o2.vertex.hashCode();
                return value;
            }
        };

        IndexedPriorityQueue<Tile.Index, MarkedVertex> open = new IndexedPriorityQueue<Tile.Index, MarkedVertex>(cmp);
        Set<Tile.Index> closed = new HashSet<Tile.Index>();
        Map<Tile.Index, Graph.Vertex> previous = new HashMap<Tile.Index, Graph.Vertex>();

        // calculate path costs
        boolean goalAchieved = false;
        open.add(start.index, new MarkedVertex(start, 0, 0));
        while (!open.isEmpty()) {
            MarkedVertex v = open.first();
            open.remove(v.vertex.index);
            closed.add(v.vertex.index);
            if (vectors.containsKey(v.vertex.index))
                variations.add(vectors.get(v.vertex.index));
            if (v.vertex == goal) {
                goalAchieved = true;
                break;
            }
            for (Graph.Edge e : v.vertex.incidentEdges) {
                Graph.Vertex v2 = e.destination;
                if (closed.contains(v2.index))
                    continue;
                float d = v.distance + e.weight;
                if (!open.containsKey(v2.index)) {
                    float h = m_heuristic.calculate(v2, goal);
                    open.add(v2.index, new MarkedVertex(v2, d, h));
                    previous.put(v2.index, v.vertex);
                    vectors.put(v2.index, new DrawableVector(v.vertex.x, v.vertex.y, v2.x, v2.y, Color.RED, 0.05f, true));
                } else {
                    MarkedVertex vertex = open.take(v2.index);
                    if (vertex.distance > d) {
                        vertex.distance = d;
                        previous.put(v2.index, v.vertex);
                        vectors.put(v2.index, new DrawableVector(v.vertex.x, v.vertex.y, v2.x, v2.y, Color.RED, 0.05f, true));
                    }
                    open.add(v2.index, vertex);
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
