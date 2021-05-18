package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Graph;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected void Initialisation(LabelStar[] tabLabelstar, Graph graph) {
    	for (Node node: graph.getNodes()) {
        	tabLabelstar[node.getId()] = new LabelStar(node, node.getPoint().distanceTo(getInputData().getDestination().getPoint())) ;
        }
    }
    
}
