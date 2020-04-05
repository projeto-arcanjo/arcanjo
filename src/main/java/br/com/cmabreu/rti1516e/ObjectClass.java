package br.com.cmabreu.rti1516e;

import java.io.Serializable;
import java.util.List;

public class ObjectClass  implements Serializable {
	private static final long serialVersionUID = 1L;
	private String myParentName;
	private String myName;
	private String sharing;
	private Integer handle;
	private List<Attribute> attributes;
	private ObjectClass parent; 
	private String moduleName;
	private String semantics;
	
	public ObjectClass(String myParent, String myName, List<Attribute> attributes, String sharing, String moduleName, String semantics) throws Exception {
		this.myParentName = myParent;
		this.myName = myName;
		this.attributes = attributes;
		this.moduleName = moduleName;
		this.sharing = sharing;
		this.semantics = semantics;
	}

	public String getModuleName() {
		return moduleName;
	}
	
	public ObjectClass getParent() {
		return parent;
	}
	
	public String getMyParentName() {
		return myParentName;
	}

	public String getMyName() {
		return myName;
	}
	
	public Integer getHandle() {
		return this.handle;
	}
	
	public String getSharing() {
		return sharing;
	}
	
	public void setHandle( Integer handle ) {
		this.handle = handle;
	}
	
	public List<Attribute> getAttributes() {
		return attributes;
	}
	
	public void setParent(ObjectClass myParent) {
		this.parent = myParent;
		
	}

	public String getSemantics() {
		return semantics;
	}
	
}
