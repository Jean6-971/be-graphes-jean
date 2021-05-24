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
        
        this.Initialisation(tabLabel, data);
        
        BinaryHeap<Label> Tas = new BinaryHeap<Label>() ;
        
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
        	notifyNodeMarked(x.getSommet());
        	int nb_successeur = 0 ;
        	for (Arc arc : x.getSommet().getSuccessors()) {
        		nb_successeur ++ ;
        		if (!data.isAllowed(arc)) {
                    continue;
                }
        		Label y = tabLabel[arc.getDestination().getId()] ;
        		if (!y.getMark()) {
        			notifyNodeReached(y.getSommet());
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
        	System.out.println("Nombre de sucesseur explorés = "+nb_successeur +" et nombre de successeur du node = "+x.getSommet().getNumberOfSuccessors());
        	System.out.println("tas valide ?"+Tas.isValid());
        	if(tabLabel[data.getDestination().getId()].getMark()==true) {
            	System.out.println("Destination trouvé");
            }
        }
        
        if (tabLabel[data.getDestination().getId()].getFather() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
	        notifyDestinationReached(data.getDestination());
	        ArrayList<Arc> listArcs = new ArrayList<Arc>() ;
	        Arc c = d.getFather();
	        while (c != null) {
	        	listArcs.add(c);
	        	c = tabLabel[c.getOrigin().getId()].getFather();
	        }
	        Collections.reverse(listArcs);
	        solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(data.getGraph(), listArcs));
        }
        return solution;
    }

}
