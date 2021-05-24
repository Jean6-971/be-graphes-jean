package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
	
public class LabelStar extends Label implements Comparable<Label> {
	
	protected double coutEstime ;
	
	protected LabelStar(Node courant, double coutEstime) {
        super(courant);
        this.coutEstime = coutEstime ;
    }
	
	public double getEstimCost() {return this.coutEstime ;}
    
    public double getTotalCost() {return super.getCost() + this.coutEstime ;}
	
}