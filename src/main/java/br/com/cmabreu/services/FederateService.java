package br.com.cmabreu.services;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import br.com.cmabreu.managers.AircraftManager;
import br.com.cmabreu.managers.FederateManager;
import br.com.cmabreu.managers.IEntityManager;
import br.com.cmabreu.managers.SurfaceManager;
import br.com.cmabreu.misc.EncoderDecoder;
import br.com.cmabreu.misc.FederateAmbassador;
import br.com.cmabreu.model.InteractionValue;
import br.com.cmabreu.model.Module;
import br.com.cmabreu.model.ObjectClassList;
import br.com.cmabreu.rti1516e.InteractionClass;
import br.com.cmabreu.rti1516e.ObjectClass;
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
import hla.rti1516e.exceptions.NameNotFound;

@Service
public class FederateService {
	private RTIambassador rtiamb;
   	private FederateAmbassador fedamb;  
	private Logger logger = LoggerFactory.getLogger( FederateService.class );
	private List<Module> modules = new ArrayList<Module>();
	private String hlaVersion;
	
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
	
    public String getHlaVersion() {
		return hlaVersion;
	}
    
    public String getFederationName() {
    	return this.federationName;
    }
    
    @PostConstruct
    public void initializer() {
		this.physicalEntities = new ArrayList<IEntityManager>();
    }
    
    public void startRti() throws Exception {
    	if( !fomFolder.endsWith("/") ) fomFolder = fomFolder + "/";
		createRtiAmbassador();
		connect();
		createFederation( federationName );
		joinFederation( federationName, federateName);
		
		// Armazena todas as classes encontradas nos modulos
		parseModules();
		
		subscribeToAll();
		hlaVersion = rtiamb.getHLAversion();
    }
    
    private void parseModules() throws Exception {
    	logger.info("Verificando Modulos");
    	moduleProcessorService.parseModules( this.modules );
    	
    	logger.info("Solicitando Identificadores de Classes para a RTI...");
    	for( ObjectClass objectClass : moduleProcessorService.getObjectList().getList() ) {
    		try {
    			ObjectClassHandle objectClassHandle = this.rtiamb.getObjectClassHandle( objectClass.getMyName() );
    			objectClass.setHandle( encoderDecoder.getObjectClassHandle( objectClassHandle ) );
    		} catch( NameNotFound nnfe ) {
    			logger.error( "Nome de Classe nao encontrado: " + nnfe.getMessage() );
    		}
    	}
    	logger.info("Identificadores de Classes carregados.");
    }
    
    
    private void addPhysicalManager( IEntityManager manager ) {
    	this.physicalEntities.add( manager );
    	logger.info( "Gerenciador " + manager.getClassFomName() + " adicionado." );
    }

    
	// Adiciona todo tipo de controladores de entidades em uma lista
	// dessa forma, quando chegar eventos eu posso descobrir
	// que tipo de controlador deve processar o evento.
    // Os controladores tambem sao encarrregados de subscrever e publicar
    // suas classes
    private void subscribeToAll() throws Exception {
		this.addPhysicalManager( new AircraftManager( rtiamb, simpMessagingTemplate ) );
		this.addPhysicalManager( new SurfaceManager( rtiamb, simpMessagingTemplate ) );
		this.addPhysicalManager( new FederateManager( rtiamb, simpMessagingTemplate ) );
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

	public void loadModules( File fomFolderScan ) throws Exception {
		logger.info("Carregando modulos em " + fomFolderScan.getPath() + " na seguinte ordem:");
		
		// The Standard MIM must be the first one.
		File mim = new File( fomFolderScan.getPath() + "/HLAstandardMIM.xml" ) ;
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
	
	private void loadModules() throws Exception {
		File fomFolderScan = new File( fomFolder );
		loadModules( fomFolderScan );
	}

	
	private void createFederation( String federationName ) throws Exception {
		logger.info( "Creating Federation " + federationName );
		
		try	{
			loadModules();
			URL[] modules = new URL[ this.modules.size() ];
			for (int i =0; i < this.modules.size(); i++) modules[i] = this.modules.get(i).getFile().toURI().toURL(); 
			rtiamb.createFederationExecution( federationName, modules );
			logger.info( "Created Federation. HLA Version " + rtiamb.getHLAversion() );
		} catch( Exception urle )	{
			logger.error( urle.getMessage() );
		}
		
	}	
	
	private void joinFederation( String federationName, String federateName ) throws Exception  {
		rtiamb.joinFederationExecution( federateName, "ControlPanelFederateType", federationName );   
		logger.info( "Joined Federation as " + federateName );
	}


    public List<Module> getModules() {
    	return this.modules;
    }
	
	/*
			METHODS USED BY CONTROLLERS
	*/
	
	// Modules
    public List<Module> getAllModules() {
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
			if( objectClass.getModuleName().replaceAll("\\.","").toLowerCase().equals( moduleFileName.toLowerCase() ) ){
				result.add( objectClass );
			}
		}
    	return result;
    }
    
    
	public List<ObjectClass> getClassByParentName(String parentName) {
    	List<ObjectClass> result = new ArrayList<ObjectClass>();
		for( ObjectClass objectClass : moduleProcessorService.getObjectList().getList() ) {
			if( objectClass.getMyParentName().replaceAll("\\.","").toLowerCase().equals( parentName.toLowerCase() ) ){
				result.add( objectClass );
			}
		}
    	return result;
	}
	
	
    public ObjectClass getClass( String className ) {
		for( ObjectClass objectClass : moduleProcessorService.getObjectList().getList() ) {
			if( objectClass.getMyName().replaceAll("\\.","").toLowerCase().equals( className.toLowerCase() ) ) {
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

    // Interactions
    public List<InteractionClass> getInteractions(){
    	return moduleProcessorService.getInteractionClassList().getList();
    }
    public InteractionClass getInteraction( String interactionName ) {
		for( InteractionClass interactionClass : moduleProcessorService.getInteractionClassList().getList() ) {
			if( interactionClass.getName().replaceAll("\\.","").toLowerCase().equals( interactionName.toLowerCase() ) ) {
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
			if(   objectClass.getHandle() == encoderDecoder.getObjectClassHandle( theObjectClass ) ) return objectClass;
		}
		return null;
	}
	

	// Responde para a interface reenviando todos os objetos que foram recebidos
	public int sendObjectsToInterface(){
		int count = 0;
		logger.info("uma interface solicitou todos os objetos cadastrados");
		for( IEntityManager pe : this.physicalEntities ) {
			int cc = pe.sendObjectsToInterface();
			count = count + cc;
		}
		return count;
	}
	
	
	/**
	 * 
	 * 			HELPERS TO THE FEDERATE AMBASSADOR
	 * 
	 * 
	 */

	
	public void discoverObjectInstance( ObjectInstanceHandle theObject, ObjectClassHandle theObjectClass, String objectName ) {
		logger.info("Novo Objeto (handle " + theObject + ") descoberto: " + objectName );
		String classeTipo = "N/E";
		
		try {
			// Metodo para descobrir que tipo de class eh esse objeto novo que entrou
			// Isso vai ser util para o Gateway Portico-Pitch
			ObjectClass objectClass = whatIsThis(theObjectClass);
			classeTipo = objectClass.getMyName();
			logger.info( "Novo objeto " + objectName + " e um " + classeTipo );
		} catch( Exception e ) {
			logger.warn("o novo objeto " + objectName + " nao pode ser classificado.");
		}
		
		// Procura qual controlador deve processar este evendo, baseado no tipo de objeto
		for( IEntityManager pe : this.physicalEntities ) {
			if( pe.isAKindOfMe( theObjectClass ) ) {
				logger.info("Designei " + objectName + " para o Gerenciador " + pe.getClassFomName() );
				pe.discoverObjectInstance( theObject, theObjectClass, objectName, classeTipo );
			}
		}
		
		
	}

	public void reflectAttributeValues(ObjectInstanceHandle theObject, AttributeHandleValueMap theAttributes, byte[] tag, OrderType sentOrder) {
		
		// Procura qual controlador possui a instancia deste objeto e passa o evento pra ele
		try {
			for( IEntityManager pem : this.physicalEntities ) {
				pem.reflectAttributeValues(theObject, theAttributes, tag, sentOrder);
			}
		} catch ( Exception e ) {
			logger.error("Erro ao receber atualizacao de atributos");
		}
		
	}

		
	public void removeObjectInstance(ObjectInstanceHandle theObject, byte[] tag, OrderType orderType, SupplementalRemoveInfo supInfo) {
		for( IEntityManager pem : this.physicalEntities ) {
			pem.removeObjectInstance( theObject );
		}
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

    
}
