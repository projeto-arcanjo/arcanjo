package br.com.cmabreu.services;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.cmabreu.misc.EncoderDecoder;
import br.com.cmabreu.misc.FederateAmbassador;
import br.com.cmabreu.model.AttributeValue;
import br.com.cmabreu.model.AttributeValueList;
import br.com.cmabreu.model.InteractionValue;
import br.com.cmabreu.model.Module;
import br.com.cmabreu.model.ObjectClassList;
import br.com.cmabreu.rti1516e.Attribute;
import br.com.cmabreu.rti1516e.InteractionClass;
import br.com.cmabreu.rti1516e.ObjectClass;
import br.com.cmabreu.rti1516e.ObjectInstance;
import hla.rti1516e.AttributeHandle;
import hla.rti1516e.AttributeHandleSet;
import hla.rti1516e.AttributeHandleValueMap;
import hla.rti1516e.CallbackModel;
import hla.rti1516e.FederateAmbassador.SupplementalReceiveInfo;
import hla.rti1516e.FederateAmbassador.SupplementalRemoveInfo;
import hla.rti1516e.InteractionClassHandle;
import hla.rti1516e.ObjectClassHandle;
import hla.rti1516e.ObjectInstanceHandle;
import hla.rti1516e.OrderType;
import hla.rti1516e.ParameterHandleValueMap;
import hla.rti1516e.RTIambassador;
import hla.rti1516e.RtiFactoryFactory;
import hla.rti1516e.SynchronizationPointFailureReason;
import hla.rti1516e.TransportationTypeHandle;
import hla.rti1516e.exceptions.FederationExecutionAlreadyExists;

@Service
public class FederateService {
	private RTIambassador rtiamb;
	private FederateAmbassador fedamb;  
	private Logger logger = LoggerFactory.getLogger( FederateService.class );
	private List<Module> modules = new ArrayList<Module>();
	private boolean started = false;
	private String hlaVersion;
	
	private List<ObjectInstance> instances = new ArrayList<ObjectInstance>();
	private EncoderDecoder encoderDecoder;
	private double federateTime;
	private boolean regulating;
	private boolean constrained;
	private boolean advancing;
	
	private XPlaneAircraftManager xPlaneAircraftManager;
	
    @Value("${federation.fomfolder}")
    String fomFolder;	

    @Value("${federation.federationName}")
    String federationName;	
    
    @Value("${federation.federateName}")
    String federateName;	
    
    @Autowired
    ModuleProcessorService moduleProcessorService;
    
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;       
    
	public FederateService() {
		super();
		try {
			this.encoderDecoder = new EncoderDecoder();
		} catch ( Exception e ) {
			e.printStackTrace();
		}
	}
	
    public boolean isStarted() {
    	return started;
    }
    
    public String getHlaVersion() {
		return hlaVersion;
	}
    
    public String getFederationName() {
    	return this.federationName;
    }
    
    public String startRti() throws Exception {
    	if( started ) return "ALREADY_STARTED";
    	if( !fomFolder.endsWith("/") ) fomFolder = fomFolder + "/";
		createRtiAmbassador();
		connect();
		try {
			createFederation( federationName );
			joinFederation( federationName, federateName);
			subscribeToAll();
			hlaVersion = rtiamb.getHLAversion();
			started = true;
			return "STARTED_NOW";
		} catch( FederationExecutionAlreadyExists exists ) {
			this.rtiamb.disconnect();
			logger.error("*********************************************");
			logger.error("**        FEDERATE ALREADY EXISTS          **");
			logger.error("**  Please close all connected federates   **");
			logger.error("**  and destroy the federation because     **");
			logger.error("**  this may lead to unexpected behaviour  **");
			logger.error("*********************************************");
			logger.error( federationName );
			logger.error("*********************************************");
			started = false;
			return "FEDERATE_ALREADY_EXISTS";
		}
    }
    
    
    /**
     * Parse all XML modules found in "/foms" directory. 
     * @throws Exception
     */
    private void parseModules() throws Exception {
    	logger.info("Parsing modules");
    	moduleProcessorService.parseModules( this.modules );
    	logger.info("Acquiring class handles");
    	for( ObjectClass objectClass : moduleProcessorService.getObjectList().getList() ) {
    		ObjectClassHandle objectClassHandle = this.rtiamb.getObjectClassHandle( objectClass.getMyName() );
    		objectClass.setHandle( encoderDecoder.getObjectClassHandle( objectClassHandle ) );
    		logger.info("  > [" +  objectClass.getHandle() + "] " + objectClass.getMyName() );
    	}
    }
    
    private void subscribeToAll() throws Exception {
    	parseModules();

    	this.xPlaneAircraftManager = new XPlaneAircraftManager( rtiamb );
    	
    	
		// Fill the interaction classes with respective handles
    	// and Subscribe to Interaction Root to receive anything
		for( InteractionClass interaction : moduleProcessorService.getInteractionClassList().getList() ) {
			String iName = interaction.getFullName();
			try {
				InteractionClassHandle iHandle = rtiamb.getInteractionClassHandle( iName );
				rtiamb.subscribeInteractionClass( iHandle );
				Integer cHandle = encoderDecoder.getInteractionHandle( iHandle );
				interaction.setHandle( cHandle );
				logger.info(" > Interaction " + iName + " registred as handle " + cHandle );
			} catch ( Exception e ) {
				logger.error( "Error when getting handle for " + e.getMessage() );
			}
		}
		
		
    }
    
	private void createRtiAmbassador() throws Exception {
		logger.info( "Creating RTIambassador." );
		this.rtiamb = RtiFactoryFactory.getRtiFactory().getRtiAmbassador();
	}   
	
	private void connect() throws Exception {
		logger.info( "Connecting..." );
		this.fedamb = new FederateAmbassador( this );
		this.rtiamb.connect( this.fedamb, CallbackModel.HLA_IMMEDIATE );
	}	

	private void loadModules() throws Exception {
		File fomFolderScan = new File( fomFolder );
		logger.info("Scanning for modules. This will be the loading order:");
		
		// The Standard MIM must be the first one.
		File mim = new File( fomFolder + "HLAstandardMIM.xml" ) ;
		this.modules.add( new Module( mim ) );
		// -------------------------------------------------------------
		logger.info( "  > HLAstandardMIM.xml" );
	    for (final File fileEntry : fomFolderScan.listFiles()) {
	        if ( fileEntry.isDirectory() ) {
	            //
	        } else {
	        	if ( !fileEntry.getName().toLowerCase().equals("hlastandardmim.xml")  &&  fileEntry.getName().toLowerCase().endsWith(".xml") ) {
	        		logger.info( "  > " + fileEntry.getName() );
	        		this.modules.add( new Module( fileEntry ) );
	        	}
	        }
	    }
	}

	
	private void createFederation( String federationName ) throws Exception {
		logger.info( "Creating Federation " + federationName );
		
		try	{
			loadModules();
			URL[] modules = new URL[ this.modules.size() ];
			for (int i =0; i < this.modules.size(); i++) modules[i] = this.modules.get(i).getFile().toURI().toURL(); 
			rtiamb.createFederationExecution( federationName, modules );
			logger.info( "Created Federation. HLA Version " + rtiamb.getHLAversion() );
		} catch( MalformedURLException urle )	{
			logger.error( "Exception loading one of the FOM modules from disk: " + urle.getMessage() );
			urle.printStackTrace();
			return;
		}
	}	
	
	private void joinFederation( String federationName, String federateName ) throws Exception  {
		rtiamb.joinFederationExecution( federateName, "ControlPanelFederateType", federationName );   
		logger.info( "Joined Federation as " + federateName );
	}


	/*
			METHODS USED BY CONTROLLERS
	*/
	
	// Request an update for all MOM attributes 
    public void refreshData() {
		for( ObjectInstance instance : this.getInstances() ) {
			ObjectInstanceHandle objectInstanceHandle = encoderDecoder.getObjectHandle( instance.getObjectInstanceHandle() );
			ObjectClass objectClass = instance.getObjectClass();
			try {
				AttributeHandleSet attributeHS = rtiamb.getAttributeHandleSetFactory().create();
				for( Attribute attribute : objectClass.getAttributes() ) {
					if( attribute.getHandle() != null ) {
						logger.info( "Ask RTI to send me attribute [" + attribute.getHandle() + "] " + attribute.getName() + " of " + instance.getObjectName() );
						attributeHS.add( encoderDecoder.getAttributeHandle( attribute.getHandle() ) );
					}
				}
				rtiamb.requestAttributeValueUpdate( objectInstanceHandle, attributeHS, "Request Attribute Update".getBytes() );
			} catch ( Exception e ) {
				logger.error("Error in attribute update request: " + e.getMessage() );
				e.printStackTrace();
			}
			
		}
    }
	
	
	// Modules
    public List<Module> getModules() {
    	List<Module> result = new ArrayList<Module>();
    	for( Module module : this.modules ) {
    		String sf = module.getFileName().replace(".","");
			module.getClasses().addAll( getClasses( sf ) );
    		result.add( module );
    	}
		return result;
	}
    public Module getModule( String moduleFileName ) {
    	for( Module module : this.modules ) {
    		String sf = module.getFileName().replace(".","");
    		
    		System.out.println(sf.toLowerCase() + " = " + moduleFileName.toLowerCase() );
    		
    		if( sf.toLowerCase().equals( moduleFileName.toLowerCase() ) ) {
    			module.getClasses().addAll( getClasses( moduleFileName ) );
    			return module;
    		}
    	}
    	return null;
    }

    
    // Classes
    public List<ObjectClass> getClasses() {
    	return moduleProcessorService.getObjectList().getList();
    }
    private List<ObjectClass> getClasses( String moduleFileName ) {
    	List<ObjectClass> result = new ArrayList<ObjectClass>();
		for( ObjectClass objectClass : moduleProcessorService.getObjectList().getList() ) {
			if( objectClass.getModuleName().replace(".","").toLowerCase().equals( moduleFileName.toLowerCase() ) ){
				result.add( objectClass );
			}
		}
    	return result;
    	
    }
    public ObjectClass getClass( String className ) {
		for( ObjectClass objectClass : moduleProcessorService.getObjectList().getList() ) {
			if( objectClass.getMyName().toLowerCase().equals( className.toLowerCase() ) ) {
				return objectClass;
			}
		}
		return null;
    }
    public ObjectClass getClass( Integer classHandle ) {
		for( ObjectClass objectClass : moduleProcessorService.getObjectList().getList() ) {
			if( objectClass.getHandle().equals( classHandle ) ) {
				return objectClass;
			}
		}
		return null;
    }

    // Instances
    public List<ObjectInstance> getInstances(){
    	return instances;
    }
    public ObjectInstance getInstance( Integer objectInstanceHandle ) {
		for( ObjectInstance instance : instances ) {
			if( instance.getObjectInstanceHandle() == objectInstanceHandle ) return instance;
		}
		return null;
    }
    public ObjectInstance getInstance( String objectName ) {
		for( ObjectInstance instance : instances  ) {
			if( instance.getObjectName().toLowerCase().equals( objectName.toLowerCase() ) ) {
				return instance;
			}
		}
		return null;
    }
    
    // Interactions
    public List<InteractionClass> getInteractions(){
    	return moduleProcessorService.getInteractionClassList().getList();
    }
    public InteractionClass getInteraction( String interactionName ) {
		for( InteractionClass interactionClass : moduleProcessorService.getInteractionClassList().getList() ) {
			if( interactionClass.getName().toLowerCase().equals( interactionName.toLowerCase() ) ) {
				return interactionClass;
			}
		}
    	return null;
    }
    
	public void setFederateTime( double federateTime ) {
		this.federateTime = federateTime;
	}
	
	public void setConstrained(boolean constrained) {
		this.constrained = constrained;
	}
	
	public void setRegulating(boolean regulating) {
		this.regulating = regulating;
	}
	
	public double getFederateTime() {
		return federateTime;
	}
	
	public void setAdvancing(boolean advancing) {
		this.advancing = advancing;
	}
	
	public boolean isAdvancing() {
		return this.advancing;
	}
	
	public boolean isRegulating() {
		return regulating;
	}
	
	public boolean isConstrained() {
		return constrained;
	}
	
	private ObjectClass whatIsThis( ObjectClassHandle theObjectClass ) throws Exception {
		ObjectClassList classList = moduleProcessorService.getObjectList();
		
		for( ObjectClass objectClass : classList.getList() ) {
			if(   objectClass.getHandle() == encoderDecoder.getObjectClassHandle( theObjectClass ) ) {
				logger.info( "class " + theObjectClass + " is " + objectClass.getMyName() );
				return objectClass;
			}
		}
		return null;
	}
	
	private synchronized boolean instanceExists( int handle ) {
		for( ObjectInstance instance : instances ) {
			if( instance.getObjectInstanceHandle() == handle ) return true;
		}
		return false;
	}
	
	private synchronized boolean instanceRemove( int handle ) {
		for( ObjectInstance instance : instances ) {
			if( instance.getObjectInstanceHandle() == handle ) {
				instances.remove( instance );
				return true;
			}
		}
		return false;
	}	
	
	
	/**
	 * 
	 * 			HELPERS TO THE FEDERATE AMBASSADOR
	 * 
	 * 
	 */

	
	public void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName ) {
		logger.info("new object (handle " + theObject + ") discovered: " + objectName + " of class " + theObjectClass );
		
		
		if( xPlaneAircraftManager.isAKindOfMe( theObjectClass ) ) {
			
		}
		
		
		try {
			ObjectClass objectClass = whatIsThis(theObjectClass);
			if( objectClass != null ) {
				ObjectInstance instance = new ObjectInstance( encoderDecoder.getObjectHandle( theObject ), objectClass, objectName );
				if( !instanceExists( instance.getObjectInstanceHandle() ) ) {
					instances.add( instance );
					logger.info( "Sending new instance '" + objectName + "' to the frontend..." );
					simpMessagingTemplate.convertAndSend("/instances/new", instance );
				} else {
					logger.warn( objectName + " is already in my list.");
				}
			} else {
				logger.warn("cannot find any class handle for object [" + theObjectClass + "] - " + objectName );
			}
		} catch( Exception e ) {
			logger.error( e.getMessage() );
		}
	}

	public void provideAttributeValueUpdate( ObjectInstanceHandle theObject, AttributeHandleSet theAttributes, byte[] userSuppliedTag ) {
		// Nothing to do since the panel is passive and have no object ownership
		// May I notify the frontend?
	}

	public void reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder) {
		logger.info("New attribute packet update arrived. Must update " + theAttributes.size() + " attributes.");
		
		AttributeValueList avl = new AttributeValueList();
		int objectHandle = encoderDecoder.getObjectHandle( theObject );

		
		if( avl.getValues().size() > 0 ) 
			simpMessagingTemplate.convertAndSend("/attributes/reflectvalues", avl ); 
		else
			logger.error("Cannot decode attribute packet.");	
	}

	private Attribute getAttribute( Integer handle, Integer objectHandle ) {
		logger.info("Searching for attribute [" + handle + "] in discovered instance " + objectHandle );
		
		for( ObjectInstance instance : this.getInstances() ) {
			
			ObjectClass objectClass = instance.getObjectClass();
			logger.info(" > scanning " + instance.getObjectName() + " of class " + objectClass.getMyName() );
			for( Attribute attribute : objectClass.getAttributes() ) {
				logger.info( "   > attribute " +  attribute.getName() + " = " + attribute.getHandle() ); 
				if( ( attribute.getHandle() != null ) && attribute.getHandle().equals( handle ) ) {
					logger.info( "   > found attribute " + attribute.getName() + " in " + instance.getObjectName() + " (" + instance.getObjectClass().getMyName() + ")");
					return attribute;
				}
			}
			
		}
		
		
		logger.info("Attribute [" + handle + "] not found.");
		return null;
		
	}
	
	
	public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] tag, OrderType orderType, SupplementalRemoveInfo supInfo) {
		Integer handle = encoderDecoder.getObjectHandle( theObject );
		if( instanceExists( handle ) ) {
			instanceRemove( handle );
		}
		simpMessagingTemplate.convertAndSend("/instances/remove", handle );
	}

	public void announceSynchronizationPoint(String label, byte[] tag){
		try {
			// Will inform the Federation we're ready to go anyway
			this.rtiamb.synchronizationPointAchieved( label );
		} catch( Exception e ) {
			logger.error(e.getMessage() + " : " + e.getCause() );
		}
	}

	public void federationSynchronized(String label) {
		// TODO Auto-generated method stub
		
	}

	public void synchronizationPointRegistrationSucceeded(String label) {
		// TODO Auto-generated method stub
		
	}

	public void synchronizationPointRegistrationFailed(String label, SynchronizationPointFailureReason reason) {
		// TODO Auto-generated method stub
		
	}

	public void setRTIAmbassador(RTIambassador rtiamb) {
		this.rtiamb = rtiamb;
	}

	public void receiveInteraction(InteractionClassHandle ich, ParameterHandleValueMap parameterHandleValueMap, byte[] tag,
			OrderType orderType, TransportationTypeHandle transportationTypeHandle, SupplementalReceiveInfo supplementalReceiveInfo) {
		String federateName = "";
		
		System.out.println("Incoming interaction...");
		
		/*
		if( supplementalReceiveInfo.hasProducingFederate() ) {
			FederateHandle fh = supplementalReceiveInfo.getProducingFederate();
			Integer federateHandle = encoderDecoder.getFederateHandle( fh );
			// Get the actual federate here.
			for( ObjectInstance instance : getInstances() ) {
				System.out.println(" > " + instance.getObjectInstanceHandle() + ": " + instance.getObjectName() );
				if( instance.getObjectInstanceHandle().equals( federateHandle ) ) {
					federateName = instance.getObjectName();
				}
			}
		}
		*/

		Integer interactionClassHandle = encoderDecoder.getInteractionHandle( ich );
		Integer parameterCount = parameterHandleValueMap.size();
		String interactionName = "";
		String interactionModuleName = "";
		boolean found = false;
		for( InteractionClass interaction : getInteractions() ) {
			interactionName = interaction.getFullName();
			interactionModuleName = interaction.getModuleName();
			
			if( ( interaction.getHandle() != null ) && interaction.getHandle().equals( interactionClassHandle ) ) {
				found = true;
				logger.info("  > " + interactionName + " (" + interactionClassHandle + ")" );
				InteractionValue iv = new InteractionValue( parameterCount, interactionName, interactionModuleName, interactionClassHandle, federateName );
				simpMessagingTemplate.convertAndSend("/interactions/receive", iv );
			} 
		}
		if( !found ) logger.error("Cannot find handle " + interactionClassHandle + " in my interaction list" );
		
	}

	
	public Integer getFederateCount() {
		Integer result = 0;
		for( ObjectInstance obj : getInstances() ) {
			if( obj.getObjectName().startsWith("MOM.Federat") ) result++;
		}
		return result;
	}

	public Integer getInstancesCount() {
		Integer result = 0;
		for( ObjectInstance obj : getInstances() ) {
			if( !obj.getObjectName().startsWith("MOM.Federat") ) result++;
		}
		return result;
	}
    
    
}