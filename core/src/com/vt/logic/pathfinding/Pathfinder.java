package com.vt.logic.pathfinding;

import com.vt.gameobjects.pointers.DrawableVector;
import com.vt.gameobjects.terrain.tiles.Tile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fckrsns on 06.01.2016.
 */
public abstract class Pathfinder {
    public static class DecisionInfo {
        public float sumWeight;
        public float heuristic;

        DecisionInfo(float sumWeight, float heuristic) {
            this.sumWeight = sumWeight;
            this.heuristic = heuristic;
        }

        DecisionInfo(float sumWeight) {
            this(sumWeight, 0.0f);
        }

        DecisionInfo() {
            this(0.0f);
        }
    }
    Float INFINITY = Float.POSITIVE_INFINITY;
    public Set<Tile.Index> processedNodes = new HashSet<Tile.Index>();
    public Map<Tile.Index, DecisionInfo> d = new HashMap<Tile.Index, DecisionInfo>();
    public Map<Tile.Index, DrawableVector> vectors = new HashMap<Tile.Index, DrawableVector>();
    public Set<DrawableVector> variations = new HashSet<DrawableVector>();

    public abstract List<Graph.Node> findPath(Graph.Node fromNode, Graph.Node toNode);
}
