package br.com.cmabreu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.cmabreu.rti1516e.InteractionClass;

public class InteractionClassList implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<InteractionClass> list;
	
	public InteractionClassList() {
		this.list = new ArrayList<InteractionClass>();
	}
	
	
	public void add( InteractionClass interactionClass ) {
		if( this.exists( interactionClass.getFullName() ) ) return;
		this.list.add( interactionClass );
	}
	
	public List<InteractionClass> getList(){
		return this.list;
	}	
	
	public boolean exists( String fullName ) {
		for( InteractionClass interactionClass : list ) {
			if( interactionClass.getFullName().equals( fullName ) ) return true;
		}
		return false;
	}
	
}
