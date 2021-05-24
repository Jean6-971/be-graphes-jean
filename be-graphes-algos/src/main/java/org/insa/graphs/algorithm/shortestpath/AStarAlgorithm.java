package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    // Initialisation d'un tableau de labelstar
    protected void Initialisation(Label[] tabLabel, ShortestPathData data) {
    	for (Node node: data.getGraph().getNodes()) {
        	if(data.getMode().equals(AbstractInputData.Mode.LENGTH)) {
        		tabLabel[node.getId()]=new LabelStar(node,node.getPoint().distanceTo(data.getDestination().getPoint()));
    		}else {
    			tabLabel[node.getId()]=new LabelStar(node,node.getPoint().distanceTo(data.getDestination().getPoint())/(data.getGraph().getGraphInformation().getMaximumSpeed()*1000/3600));
    		}
    	}
    }
    
}
