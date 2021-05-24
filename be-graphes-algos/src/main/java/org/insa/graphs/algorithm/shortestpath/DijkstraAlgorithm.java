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
    
    // Initialisation d'un tableau de labels
    protected void Initialisation(Label[] tabLabel, ShortestPathData data) {
    	for (Node node: data.getGraph().getNodes()) {
        	tabLabel[node.getId()] = new Label(node) ;
        }
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData() ;
        ShortestPathSolution solution = null ;
        
        // Création et initialisation d'un tableau de labels
        Label[] tabLabel = new Label[data.getGraph().size()] ;
        this.Initialisation(tabLabel, data);
        
        // Création d'un tas de labels
        BinaryHeap<Label> Tas = new BinaryHeap<Label>() ;
        
        // Création du labels associé au noeud de départ
        Label s = tabLabel[data.getOrigin().getId()] ;
        s.setCost(0) ;
        Tas.insert(s) ;
        
        // Notification de l'atteinte du noeud de départ
        notifyOriginProcessed(data.getOrigin());
        
        // Création du labels associé au noeud de destination
        Label d = tabLabel[data.getDestination().getId()];
        
        // Boucle jusqu'au marquage du noeud de destination ou jusqu'à la fin du tas
        while (!d.getMark() && !Tas.isEmpty()) {
        	
        	// Extraction et marquage du minimum du tas
        	Label x = Tas.findMin() ;
        	Tas.remove(x) ;
        	x.setMark(true) ;
        	
        	// Affichage des informations sur le noeud courant
        	System.out.println("Le coût total : " + x.getTotalCost() + " du noeud " + x.getSommet().getId() + " et le coût estimé : " + x.getEstimCost());
        	notifyNodeMarked(x.getSommet());
        	
        	// Boucle sur les successeurs
        	int nb_successeur = 0 ;
        	for (Arc arc : x.getSommet().getSuccessors()) {
        		nb_successeur ++ ;
        		if (!data.isAllowed(arc)) {
                    continue;
                }
        		Label y = tabLabel[arc.getDestination().getId()] ;
        		if (!y.getMark()) {
        			notifyNodeReached(y.getSommet());
        			
        			// Calcul du potentiel nouveau coût
        			double update = x.getCost() + data.getCost(arc);
        			if (y.getCost() > update) {
        				
        				// Mise à jour du tas et du coût
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
        	
        	// Verification du nombre de successeurs
        	System.out.println(nb_successeur + " successeurs explorés sur " + x.getSommet().getNumberOfSuccessors() + " explorables");
        	
        	// Vérification de la validité du tas (désactivé car trop coutant en ressources)
        	//System.out.println("tas valide ? "+Tas.isValid());
        	
        	if(tabLabel[data.getDestination().getId()].getMark()==true) {
            	System.out.println("Destination trouvé");
            }
        }
        
        // Vérification de la faisabilité de la solution
        if (tabLabel[data.getDestination().getId()].getFather() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	
        	// Création de la solution grâce à la liste des arcs parcourus
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
