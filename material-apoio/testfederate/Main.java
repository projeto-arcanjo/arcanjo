package testfederate;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import org.apache.commons.cli.*;

/**
 *
 * @author bergtwvd
 */
public class Main {
	
	// set the main logger
	static final Logger logger = Logger.getLogger(Main.class.getPackage().getName());

	/**
	 * @param args the command line arguments
	 * @throws java.lang.Exception
	 */
	public static void main(String[] args) throws Exception {
		
		// get the configuration data
		TestConfiguration config = TestConfiguration.getInstance();

		/*
		** Set Logger defaults
		 */
		String logFile = null;

		// logLevels are:
		// SEVERE	1000
		// WARNING	900
		// INFO		800
		// CONFIG	700
		// FINE		500
		// FINER	400
		// FINEST	300
		// OFF		Integer.MAX_VALUE
		// ALL		Integer.MIN_VALUE
		String logLevel = "SEVERE"; // either name or value can be used

		/*
		** Process commandline options
		 */
		// create Options
		Option helpOpt = Option.builder("h")
				.longOpt("help")
				.desc("Help information")
				.build();

		Option federationOpt = Option.builder("F")
				.longOpt("federation")
				.hasArg()
				.desc("Federation name [" + config.getFederationName() + "]")
				.build();

		Option federateOpt = Option.builder("f")
				.longOpt("federate")
				.hasArg()
				.desc("Federate name [" + config.getFederateName() + "]")
				.build();

		Option connectOpt = Option.builder("connectattempts")
				.hasArg()
				.desc("Number of RTI connection attempts [" + config.getNrConnectionAttempts() + "]")
				.build();
		
		Option constrainedOpt = Option.builder("timemanaged")
				.desc("Run in time managed mode [" + config.isTimeManagedMode() + "]")
				.build();
						
		Option simtimestepOpt = Option.builder("simtimestep")
				.hasArg()
				.desc("In constrained mode/timestepped, use this simulation time step [" + config.getSimTimeStep() + "]")
				.build();
				
		Option realtimestepOpt = Option.builder("realtimestep")
				.hasArg()
				.desc("Wallclock time step for dead reckoning RO spatials [" + config.getRealTimeStep() + "]")
				.build();
				
		Option logfileOpt = Option.builder("L")
				.longOpt("logfile")
				.hasArg()
				.desc("Logfile")
				.build();
				
		Option loglevelOpt = Option.builder("l")
				.longOpt("loglevel")
				.hasArg()
				.desc("Loglevel [SEVERE]")
				.build();
				
		Options options = new Options();
		options.addOption(helpOpt).
				addOption(federationOpt).
				addOption(federateOpt).
				addOption(connectOpt).
				addOption(constrainedOpt).
				addOption(simtimestepOpt).
				addOption(realtimestepOpt).
				addOption(logfileOpt).
				addOption(loglevelOpt);

		// Parse and handle the options
		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);

		config.setFederationName(cmd.getOptionValue(federationOpt.getOpt(), config.getFederationName()));
		config.setFederateName(cmd.getOptionValue(federateOpt.getOpt(), config.getFederateName()));
		
		if (cmd.hasOption(connectOpt.getOpt())) {
			config.setNrConnectionAttempts(Integer.valueOf(cmd.getOptionValue(connectOpt.getOpt())));
		}
		if (cmd.hasOption(constrainedOpt.getOpt())) {
			config.setTimeManagedMode(true);
		}
		if (cmd.hasOption(simtimestepOpt.getOpt())) {
			config.setSimTimeStep(Double.valueOf(cmd.getOptionValue(simtimestepOpt.getOpt())));
		}
		if (cmd.hasOption(realtimestepOpt.getOpt())) {
			config.setRealTimeStep(Double.valueOf(cmd.getOptionValue(realtimestepOpt.getOpt())));
		}			

		logFile = cmd.getOptionValue(logfileOpt.getOpt(), logFile);
		logLevel = cmd.getOptionValue(loglevelOpt.getOpt(), logLevel);

		if (cmd.hasOption(helpOpt.getOpt())) {
			// automatically generate the help statement
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("Test Federate", options);
			return;
		}

		/*
		** Initialize the Logger system
		 */
		if (logFile != null) {
			initLogger(logFile, logLevel);
		}

		logger.log(Level.INFO,
				"federationName={0}"
				+ "\nfederateName={1}"
				+ "\ntimeManagedMode={2}"
				+ "\nsimTimeStep={3}"
				+ "\nrealTimeStep={4}"
				+ "\nloglevel={5}",
				new Object[]{config.getFederationName(),
					config.getFederateName(),
					config.isTimeManagedMode(),
					config.getSimTimeStep(),
					config.getRealTimeStep(),
					logLevel});

		/*
		** create a new environment for the entities
		*/
		Environment env = new Environment(config);

		/*
		** Start the federate
		 */
		try {
			new Federate().runFederate(env);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void initLogger(String logFile, String logLevel) throws Exception {
		Level level = Level.parse(logLevel);
		logger.setLevel(level);

		FileHandler fh = new FileHandler(logFile);
		logger.addHandler(fh);
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
	}
}
