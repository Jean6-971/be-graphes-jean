package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData() ;
        ShortestPathSolution solution = null ;
        
        BinaryHeap<Label> Tas = new BinaryHeap<Label>() ; // QUESTION
        Label[] tabLabel = new Label[data.getGraph().size()] ;
        
        for (Node node: data.getGraph().getNodes()) {
        	tabLabel[node.getId()] = new Label(node) ;
        }
        
        Label s = tabLabel[data.getOrigin().getId()] ;
        s.setCost(0) ;
        Tas.insert(s) ;
        
        while (!tabLabel[data.getDestination().getId()].getMark()) {
        	Label x = Tas.findMin() ;
        	Tas.remove(x) ;
        	x.setMark(true) ;
        	for (Arc arc : x.getSommet().getSuccessors()) {
        		Label y = tabLabel[arc.getDestination().getId()] ;
        		if (!y.getMark()) {
        			float update = x.getCost()+arc.getLength() ;
        			y.setCost(java.lang.Math.min(y.getCost(),update)) ;
        			if (y.getCost() == update) {
        				Tas.insert(y) ;
        				y.setFather(arc);
        			}
        		}
        	}
        }
        
        
        return solution;
    }

}
