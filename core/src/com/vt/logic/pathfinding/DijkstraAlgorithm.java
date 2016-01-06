package com.vt.logic.pathfinding;

import com.badlogic.gdx.utils.Array;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fckrsns on 06.01.2016.
 */
public class DijkstraAlgorithm implements Pathfinder {
    private final Float INFINITY = Float.POSITIVE_INFINITY;
    private Graph m_graph;

    public DijkstraAlgorithm(Graph graph) {
        m_graph = graph;
    }

    @Override
    public List<Graph.Node> findPath(Graph.Node fromNode, Graph.Node toNode) {
        // initial
        Set<Tile.Index> processedNodes = new HashSet<Tile.Index>();
        Map<Tile.Index, Float> d = new HashMap<Tile.Index, Float>();
        d.put(fromNode.index, 0.0f);
        processedNodes.add(fromNode.index);

        // calculate path costs
        Graph.Node currentNode = fromNode;
        while (currentNode.index != toNode.index) {
            for (Graph.DestinationNode adjacentNode : currentNode.incidentNodes) {
                Tile.Index index = adjacentNode.node.index;
                if (!d.containsKey(index))
                    d.put(index, INFINITY);
                d.put(index, Math.min(d.get(index), d.get(currentNode.index) + adjacentNode.edgeWeight));
            }
            Float min = INFINITY;
            Tile.Index minIndex = null;
            for (Map.Entry entry : d.entrySet()) {
                Float currentValue = (Float) entry.getValue();
                Tile.Index currentIndex = (Tile.Index) entry.getKey();
                if (!processedNodes.contains(currentIndex) && currentValue < min) {
                    min = currentValue;
                    minIndex = currentIndex;
                }
            }
            if (min == INFINITY || minIndex == null)
                return null;
            currentNode = m_graph.getNode(minIndex);
            processedNodes.add(minIndex);
        }

        // get optimal path
        currentNode = toNode;
        List<Graph.Node> path = new ArrayList<Graph.Node>();
        path.add(toNode);
        while (currentNode.index != fromNode.index) {
            Float min = INFINITY;
            Tile.Index minIndex = null;
            for (Graph.DestinationNode adjacentNode : currentNode.incidentNodes) {
                Tile.Index index = adjacentNode.node.index;
                if (!d.containsKey(index))
                    d.put(index, INFINITY);
                Float weight = d.get(index) + adjacentNode.edgeWeight;
                if (weight < min) {
                    min = weight;
                    minIndex = index;
                }
            }
            if (minIndex == null)
                return null;
            currentNode = m_graph.getNode(minIndex);
            path.add(currentNode);
        }

        Collections.reverse(path);
        return path;
    }
}
