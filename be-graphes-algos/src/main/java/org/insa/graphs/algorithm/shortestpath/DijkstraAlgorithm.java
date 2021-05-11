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
        ArrayList<Arc> listArcs = new ArrayList<Arc>() ;
        Label[] tabLabel = new Label[data.getGraph().size()] ;
        
        for (Node node: data.getGraph().getNodes()) {
        	tabLabel[node.getId()] = new Label(node) ;
        }
        
        Label s = tabLabel[data.getOrigin().getId()] ;
        s.setCost(0) ;
        Tas.insert(s) ;
        
        notifyOriginProcessed(data.getOrigin());
        
        Label d = tabLabel[data.getDestination().getId()];
        while (!d.getMark() && !Tas.isEmpty()) {
        	Label x = Tas.findMin() ;
        	Tas.remove(x) ;
        	x.setMark(true) ;
        	System.out.println(x.getCost());
        	notifyNodeReached(x.getSommet());
        	for (Arc arc : x.getSommet().getSuccessors()) {
        		if (!data.isAllowed(arc)) {
                    continue;
                }
        		Label y = tabLabel[arc.getDestination().getId()] ;
        		if (!y.getMark()) {
        			double update = x.getCost() + data.getCost(arc);
        			if (y.getCost() > update) {
        				y.setCost(update);
        				if (Tas.exist(y)) {
        					Tas.remove(y);
        					Tas.insert(y);
        					y.setFather(arc);
        				} else {
        					Tas.insert(y);
            				y.setFather(arc);
        				}
        			}
        		}
        	}
        }
        
        notifyDestinationReached(data.getDestination());
        
        Label c = d;
        while (c.getFather() != null) {
        	listArcs.add(c.getFather());
        	c = tabLabel[c.getFather().getOrigin().getId()];
        }
        
        Collections.reverse(listArcs);
        
        Path solutionPath = new Path(data.getGraph(), listArcs);
        
        solution = new ShortestPathSolution(data, Status.OPTIMAL, solutionPath);
        return solution;
    }

}
