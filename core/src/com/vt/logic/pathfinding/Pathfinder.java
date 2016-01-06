package com.vt.logic.pathfinding;

import java.util.List;

/**
 * Created by fckrsns on 06.01.2016.
 */
public interface Pathfinder {
    List<Graph.Node> findPath(Graph.Node fromNode, Graph.Node toNode);
}
