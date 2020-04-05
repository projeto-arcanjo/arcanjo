package br.com.cmabreu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.cmabreu.rti1516e.ObjectClass;

public class ObjectClassList  implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<ObjectClass> list;
	
	public boolean exists( String name ) {
		for( ObjectClass objClass : list ) {
			if( objClass.getMyName().equals( name ) ) return true;
		}
		return false;
	}

	public ObjectClass getByName( String name ) {
		for( ObjectClass objClass : list ) {
			if( objClass.getMyName().equals( name ) ) return objClass;
		}
		return null;
	}
	
	public boolean hasChildren( String name ) {
		for( ObjectClass objClass : list ) {
			if( objClass.getMyParentName().equals( name ) ) return true;
		}
		return false;
	}

	
	public ObjectClassList() {
		this.list = new ArrayList<ObjectClass>();
	}
	
	public synchronized void add( ObjectClass objectClass ) {
		if( !exists( objectClass.getMyName() ) ) this.list.add( objectClass );
	}
	
	public List<ObjectClass> getList(){
		return this.list;
	}

	public void normalize() {
		for( ObjectClass objectClass : this.list ) {
			ObjectClass myParent = getByName( objectClass.getMyParentName() );
			objectClass.setParent( myParent );
		}
	}
	
}
