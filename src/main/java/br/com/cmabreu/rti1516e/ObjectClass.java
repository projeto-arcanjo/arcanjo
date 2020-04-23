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
	private String moduleName;
	private String semantics;
	private String singleName;
	
	public ObjectClass(String myParent, String myName, List<Attribute> attributes, String sharing, String moduleName, String semantics, String singleName) throws Exception {
		this.myParentName = myParent;
		this.myName = myName;
		this.attributes = attributes;
		this.moduleName = moduleName;
		this.sharing = sharing;
		this.semantics = semantics;
		this.singleName = singleName;
	}
	
	public String getSingleName() {
		return singleName;
	}

	public String getModuleName() {
		return moduleName;
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
	
	public String getSemantics() {
		return semantics;
	}

	public void setMyParentName(String myParentName) {
		this.myParentName = myParentName;
	}

	public void setMyName(String myName) {
		this.myName = myName;
	}

	public void setSharing(String sharing) {
		this.sharing = sharing;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public void setSemantics(String semantics) {
		this.semantics = semantics;
	}
	
	
}
