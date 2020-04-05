package testfederate;

/**
 *
 * @author bergtwvd
 */
public class TestConfiguration {

	/*
	** Federation related data
	 */
	// Federation and Federate name
	private String federationName = "TheWorld";
	private String federateName = "TestFederate";
	
	// RTI connect attempts, each 1 sec
	private int nrConnectionAttempts = 10;

	/*
	** Time management data
	 */
	// Time regulating/constrained
	private boolean timeManagedMode = false;

	// -- in time managed mode: lookahead
	private double lookahead = 1.0;
	
	// -- in time managed mode: simulation time step
	private double simTimeStep = 1.0;

	// real time step
	private double realTimeStep = 1.0;

	public String getFederationName() {
		return federationName;
	}

	public void setFederationName(String federationName) {
		this.federationName = federationName;
	}

	public String getFederateName() {
		return federateName;
	}

	public void setFederateName(String federateName) {
		this.federateName = federateName;
	}

	public int getNrConnectionAttempts() {
		return nrConnectionAttempts;
	}

	public void setNrConnectionAttempts(int nrConnectionAttempts) {
		if (nrConnectionAttempts > 0) {
			this.nrConnectionAttempts = nrConnectionAttempts;
		}
	}

	public boolean isTimeManagedMode() {
		return timeManagedMode;
	}

	public void setTimeManagedMode(boolean timeManagedMode) {
		this.timeManagedMode = timeManagedMode;
	}

	public double getLookahead() {
		return lookahead;
	}

	public void setLookahead(double lookahead) {
		this.lookahead = lookahead;
	}

	public double getSimTimeStep() {
		return simTimeStep;
	}

	public void setSimTimeStep(double simTimeStep) {
		if (simTimeStep > 0.0) {
			this.simTimeStep = simTimeStep;
		}
	}

	public double getRealTimeStep() {
		return realTimeStep;
	}

	public void setRealTimeStep(double realTimeStep) {
		if (realTimeStep > 0.0) {
			this.realTimeStep = realTimeStep;
		}
	}

	private static TestConfiguration singleton = null;

	private TestConfiguration() {
	}

	// Static 'instance' method
	public static TestConfiguration getInstance() {
		if (singleton == null) {
			singleton = new TestConfiguration();
		}
		return singleton;
	}

}
