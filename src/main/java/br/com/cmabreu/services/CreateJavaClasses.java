package br.com.cmabreu.services;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import br.com.cmabreu.model.Module;
import br.com.cmabreu.rti1516e.Attribute;
import br.com.cmabreu.rti1516e.ObjectClass;

public class CreateJavaClasses {

	public static void main(String[] args) {
		
		try {
			FederateService fs = new FederateService();
			File fomFolderScan = new File( "c:/foms/" );
			fs.loadModules( fomFolderScan );
			
			ModuleProcessorService mps = new ModuleProcessorService();
			List<Module> modules = fs.getModules();
						
			mps.parseModules( modules );
			
			for( ObjectClass oc : mps.getObjectList().getList() ) {
				generateClass( oc );
			}
			
			
		} catch ( Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	private static void generateClass( ObjectClass oc ) {
		String className = oc.getSingleName().replaceAll("_","");
		StringBuilder sb = new StringBuilder();
		sb.append("package br.com.cmabreu.model.fom;\n");
		sb.append("\n");
		sb.append("public class "+ className +" implements Serializable {\n");
		sb.append("    private static final long serialVersionUID = 1L;\n");
		for( Attribute attr : oc.getAttributes() ) {
			char c[] = attr.getName().toCharArray();
			c[0] += 32;
			String attrName = new String(c);			
			sb.append("    private " + attr.getDataType() + " " + attrName + ";\n" );
		}
		sb.append("\n");
		
		for( Attribute attr : oc.getAttributes() ) {
			char c[] = attr.getName().toCharArray();
			c[0] += 32;
			String attrName = new String(c);			
			sb.append("    public " + attr.getDataType() + " get" + attr.getName() + "(){\n" );
			sb.append("        return this." + attrName + ";\n" );
			sb.append("    }\n\n");
		}
		
		
		
		sb.append("}\n");
		
		File file = new File("c:/classes/" + className + ".java");
		try {
		try ( BufferedWriter writer = new BufferedWriter( new FileWriter( file ) ) ) {
		    writer.write( sb.toString() );
		}	
		} catch ( Exception e ) {
			
		}
		
	}

}
