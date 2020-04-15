package br.com.cmabreu.services;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.cmabreu.entities.IEntity;
import br.com.cmabreu.managers.AircraftManager;
import br.com.cmabreu.managers.IEntityManager;
import br.com.cmabreu.misc.EncoderDecoder;
import br.com.cmabreu.misc.FederateAmbassador;
import br.com.cmabreu.model.InteractionValue;
import br.com.cmabreu.model.Module;
import br.com.cmabreu.model.ObjectClassList;
import br.com.cmabreu.rti1516e.InteractionClass;
import br.com.cmabreu.rti1516e.ObjectClass;
import br.com.cmabreu.rti1516e.ObjectInstance;
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
	
	private List<IEntityManager> physicalEntities;
	
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
		createFederation( federationName );
		joinFederation( federationName, federateName);
		subscribeToAll();
		hlaVersion = rtiamb.getHLAversion();
		started = true;
		return "STARTED_NOW";
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

    	
		// Adiciona todo tipo de controladores de entidades em uma lista
		// dessa forma, quando chegar eventos eu posso descobrir
		// que tipo de controlador deve processar o evento.
		this.physicalEntities = new ArrayList<IEntityManager>();
		this.physicalEntities.add( new AircraftManager( rtiamb ) );
    	
		
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
		
		// Procura qual controlador deve processar este evendo, baseado no tipo de objeto
		for( IEntityManager pe : this.physicalEntities ) {
			if( pe.isAKindOfMe( theObjectClass ) ) {
				pe.discoverObjectInstance( theObject, theObjectClass, objectName, simpMessagingTemplate );
			}
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

	public void reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder) {
		
		// Procura qual controlador possui a instancia deste objeto e passa o evento pra ele
		try {
			for( IEntityManager pem : this.physicalEntities ) {
				IEntity pe = pem.doIHaveThisObject(theObject);
				if( pe != null ) {
					pe.reflectAttributeValues( theObject, theAttributes, tag, sentOrder, simpMessagingTemplate);
				}
			}
		} catch ( Exception e ) {
			logger.error("Erro ao receber atualizacao de atributos");
		}
		
	}

		
	public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] tag, OrderType orderType, SupplementalRemoveInfo supInfo) {
		Integer handle = encoderDecoder.getObjectHandle( theObject );
		
		// Procura qual controlador deve processar este evendo, baseado no tipo de objeto
		for( IEntityManager pem : this.physicalEntities ) {
			IEntity pe = pem.doIHaveThisObject(theObject);
			if( pe != null ) {
				pem.removeObjectInstance( theObject );
			}
		}
		
		// Remove do meu controle (interface)
		instanceRemove( handle );
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

	public void refreshData() {
		logger.warn("A interface chamou REFRESH mas nao sei o que fazer ainda.");
		
	}
    
    
}
