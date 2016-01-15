package com.vt.logic.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        class Info implements Comparable<Info> {
            float distance;
            float heuristic;

            Info(float distance, float heuristic) {
                this.distance = distance;
                this.heuristic = heuristic;
            }

            @Override
            public int compareTo(Info o) {
                return (int)Math.signum((distance + heuristic) - (o.distance + o.heuristic));
            }
        }

        IndexedPriorityQueue<Tile.Index, Info> dist
                = new IndexedPriorityQueue<Tile.Index, Info>(m_graph.getOrder());
        Set<Tile.Index> processed
                = new HashSet<Tile.Index>();
        HashMap<Tile.Index, Graph.Vertex> previous
                = new HashMap<Tile.Index, Graph.Vertex>();

        // calculate path costs
        boolean goalAchieved = false;
        dist.insert(start.index, new Info(0.0f, 0.0f));
        while (!dist.isEmpty()) {
            IndexedPriorityQueue.Result<Tile.Index, Info> nextVertex = dist.deleteMin();
            Tile.Index index = nextVertex.key;
            Info info = nextVertex.priority;
            Graph.Vertex v = m_graph.getVertex(index);
            processed.add(index);
//            if (vectors.containsKey(index))
//                variations.add(vectors.get(index));
            if (v == goal) {
                goalAchieved = true;
                break;
            }
            for (Graph.Edge e : v.incidentEdges) {
                Graph.Vertex v2 = e.destination;
                if (processed.contains(v2.index))
                    continue;
                float d = info.distance + e.weight;
                if (!dist.contains(v2.index)) {
                    float h = m_heuristic.calculate(v2, goal);
                    dist.insert(v2.index, new Info(d, h));
                    previous.put(v2.index, v);
//                    vectors.put(v2.index, new DrawableVector(v.x, v.y, v2.x, v2.y, Color.RED, 0.05f, true));
                } else {
                    Info oldInfo = dist.getPriority(v2.index);
                    if (oldInfo.distance > d) {
                        oldInfo.distance = d;
                        dist.changePriority(v2.index, oldInfo);
                        previous.put(v2.index, v);
//                        vectors.put(v2.index, new DrawableVector(v.x, v.y, v2.x, v2.y, Color.RED, 0.05f, true));
                    }
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
