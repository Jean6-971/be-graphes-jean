package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Node;
import org.insa.graphs.model.Arc;

public class Label implements Comparable<Label>{
	
	protected Node sommetCourant ;
	
	protected boolean marque ;
	
	protected float cout ;
	
	protected Arc pere ;
	
    public Label(Node courant) {
    	this.sommetCourant = courant ;
    	this.marque = false ;
    	this.cout = Float.MAX_VALUE ;
    	this.pere = null ;
    }
    
    public Node getSommet() {return this.sommetCourant ;}
    
    public float getCost() {return this.cout ;}
    
    public void setCost(float cout) {this.cout = cout ;}
    
    public boolean getMark() {return this.marque ;}
    
    public void setMark(boolean marque) {this.marque = marque;}
    
    public Arc getFather() {return this.pere ;}
    
    public void setFather(Arc pere) {this.pere = pere;}
    
    public int compareTo(Label other) {
        return Float.compare(getCost(), other.getCost());
    }

}