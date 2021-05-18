package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    protected void Initialisation(LabelStar[] tabLabelstar, ShortestPathData data) {
    	for (Node node: data.getGraph().getNodes()) {
        	tabLabelstar[node.getId()] = new LabelStar(node, node.getPoint().distanceTo(data.getDestination().getPoint())) ;
        }
    }
    
}
