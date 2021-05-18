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

    protected void Initialisation(Label[] tabLabel, ShortestPathData data) {
    	for (Node node: data.getGraph().getNodes()) {
        	tabLabel[node.getId()] = new Label(node) ;
        }
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData() ;
        ShortestPathSolution solution = null ;
        
        Label[] tabLabel = new Label[data.getGraph().size()] ;
        
        Initialisation(tabLabel, data);
        
        BinaryHeap<Label> Tas = new BinaryHeap<Label>() ;
        ArrayList<Arc> listArcs = new ArrayList<Arc>() ;
        
        Label s = tabLabel[data.getOrigin().getId()] ;
        s.setCost(0) ;
        Tas.insert(s) ;
        
        notifyOriginProcessed(data.getOrigin());
        
        Label d = tabLabel[data.getDestination().getId()];
        while (!d.getMark() && !Tas.isEmpty()) {
        	Label x = Tas.findMin() ;
        	Tas.remove(x) ;
        	x.setMark(true) ;
        	System.out.println("Le coût total : " + x.getTotalCost() + " du noeud " + x.getSommet().getId() + " et le coût estimé : " + x.getEstimCost());
        	notifyNodeReached(x.getSommet());
        	for (Arc arc : x.getSommet().getSuccessors()) {
        		if (!data.isAllowed(arc)) {
                    continue;
                }
        		Label y = tabLabel[arc.getDestination().getId()] ;
        		if (!y.getMark()) {
        			double update = x.getCost() + data.getCost(arc);
        			if (y.getCost() > update) {
        				if (Tas.exist(y)) {
        					Tas.remove(y);
        					y.setCost(update);
        					Tas.insert(y);
        					y.setFather(arc);
        				} else {
        					y.setCost(update);
        					Tas.insert(y);
            				y.setFather(arc);
        				}
        			}
        		}
        	}
        }
        
        notifyDestinationReached(data.getDestination());
        
        Arc c = d.getFather();
        while (c != null) {
        	listArcs.add(c);
        	c = tabLabel[c.getOrigin().getId()].getFather();
        }
        
        Collections.reverse(listArcs);
        
        Path solutionPath = new Path(data.getGraph(), listArcs);
        
        solution = new ShortestPathSolution(data, Status.OPTIMAL, solutionPath);
        return solution;
    }

}
