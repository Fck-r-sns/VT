package com.vt.logic.pathfinding;

/**
 * Created by fckrsns on 06.01.2016.
 */
public class DijkstraAlgorithm extends AStarAlgorithm {

    public DijkstraAlgorithm(Graph graph) {
        super(graph, new AStarAlgorithm.Heuristic() {
            @Override
            public float calculate(Graph.Node node, Graph.Node targetNode) {
                return 0;
            }
        });
    }
}
