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
    public Map<Tile.Index, DrawableVector> vectors = new HashMap<Tile.Index, DrawableVector>();
    public Set<DrawableVector> variations = new HashSet<DrawableVector>();

    public abstract List<Graph.Vertex> findPath(Graph.Vertex start, Graph.Vertex goal);
}
