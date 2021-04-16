package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label {
	
	protected Node sommetCourant ;
	
	protected boolean marque ;
	
	protected float cout ;
	
	protected Arc pere ;
	
    public Label(Node courant, boolean marque, float cout, Arc pere) {
    	this.sommetCourant = courant ;
    	this.marque = marque ;
    	this.cout = cout ;
    	this.pere = pere ;
    }
    
    public Float getCost() {return this.cout ;}

}