package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData() ;
        ShortestPathSolution solution = null ;
        
        BinaryHeap<Label> Tas = new BinaryHeap<Label>() ;
        ArrayList<Arc> tasArcs = new ArrayList<Arc>() ;
        Label[] tabLabel = new Label[data.getGraph().size()] ;
        
        for (Node node: data.getGraph().getNodes()) {
        	tabLabel[node.getId()] = new Label(node) ;
        }
        
        Label s = tabLabel[data.getOrigin().getId()] ;
        s.setCost(0) ;
        Tas.insert(s) ;
        
        Label d = tabLabel[data.getDestination().getId()];
        while (!d.getMark()) {
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
        
        Label c = d;
        while (c.getFather() != null) {
        	tasArcs.add(c.getFather());
        	c = tabLabel[c.getFather().getOrigin().getId()];
        }
        
        Collections.reverse(tasArcs);
        
        Path solutionPath = new Path(data.getGraph(), tasArcs);
        
        solution = new ShortestPathSolution(data, Status.OPTIMAL, solutionPath);
        return solution;
    }

}
