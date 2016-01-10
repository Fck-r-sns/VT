package com.vt.logic.pathfinding;

import com.badlogic.gdx.graphics.Color;
import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        // initial
        d.put(start.index, new DecisionInfo());
        processedNodes.add(start.index);

        // calculate path costs
        Graph.Vertex currentVertex = start;
        while (currentVertex.index != goal.index) {
            for (Graph.Edge adjacentNode : currentVertex.incidentEdges) {
                Tile.Index index = adjacentNode.destination.index;
                if (!d.containsKey(index))
                    d.put(index, new DecisionInfo(INFINITY));
                DecisionInfo info = d.get(index);
                info.sumWeight = Math.min(info.sumWeight, d.get(currentVertex.index).sumWeight + adjacentNode.weight);
                info.heuristic = m_heuristic.calculate(adjacentNode.destination, goal);
                vectors.put(index, new DrawableVector(currentVertex.x, currentVertex.y, adjacentNode.destination.x, adjacentNode.destination.y, Color.RED, 0.05f, true));
            }
            Float min = INFINITY;
            Tile.Index minIndex = null;
            for (Map.Entry entry : d.entrySet()) {
                DecisionInfo info = (DecisionInfo) entry.getValue();
                Float currentValue = info.sumWeight + info.heuristic;
                Tile.Index currentIndex = (Tile.Index) entry.getKey();
                if (!processedNodes.contains(currentIndex) && currentValue < min) {
                    min = currentValue;
                    minIndex = currentIndex;
                }
            }
            if (min.equals(INFINITY) || minIndex == null)
                return null;
            currentVertex = m_graph.getVertex(minIndex);
            processedNodes.add(minIndex);
            if (variations != null)
                variations.add(vectors.get(minIndex));
        }

        // get optimal path
        currentVertex = goal;
        List<Graph.Vertex> path = new ArrayList<Graph.Vertex>();
        path.add(goal);
        while (currentVertex.index != start.index) {
            Float min = INFINITY;
            Tile.Index minIndex = null;
            for (Graph.Edge incidentEdge : currentVertex.incidentEdges) {
                Tile.Index index = incidentEdge.destination.index;
                if (!d.containsKey(index))
                    d.put(index, new DecisionInfo(INFINITY));
                Float weight = d.get(index).sumWeight + incidentEdge.weight;
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
