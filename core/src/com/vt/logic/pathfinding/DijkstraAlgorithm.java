package com.vt.logic.pathfinding;

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
public class DijkstraAlgorithm implements Pathfinder {
    private Graph m_graph;

    public DijkstraAlgorithm(Graph graph) {
        m_graph = graph;
    }

    @Override
    public List<Graph.Vertex> findPath(Graph.Vertex start, Graph.Vertex goal) {
        IndexedPriorityQueue<Tile.Index, Float> dist = new IndexedPriorityQueue<Tile.Index, Float>(m_graph.getOrder());
        Set<Tile.Index> processed = new HashSet<Tile.Index>();
        HashMap<Tile.Index, Graph.Vertex> previous = new HashMap<Tile.Index, Graph.Vertex>();

        // calculate path costs
        boolean goalAchieved = false;
        dist.insert(start.index, 0.0f);
        while (!dist.isEmpty()) {
            IndexedPriorityQueue.Result<Tile.Index, Float> nextVertex = dist.deleteMin();
            Tile.Index index = nextVertex.key;
            float distance = nextVertex.priority;
            Graph.Vertex v = m_graph.getVertex(index);
            processed.add(index);
            if (v == goal) {
                goalAchieved = true;
                break;
            }
            for (Graph.Edge e : v.incidentEdges) {
                Graph.Vertex v2 = e.destination;
                if (processed.contains(v2.index))
                    continue;
                float d = distance + e.weight;
                if (!dist.contains(v2.index)) {
                    dist.insert(v2.index, d);
                    previous.put(v2.index, v);
                } else {
                    float oldD = dist.getPriority(v2.index);
                    if (oldD > d) {
                        dist.changePriority(v2.index, d);
                        previous.put(v2.index, v);
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
