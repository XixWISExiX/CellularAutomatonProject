
/**
 * The Application class includes a main method accepting command line arguments to create and simulate an appropriate CA.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class Application {

	private static final int NUM_EXPECTED_ARGS = 6;

	private static final int IDX_CA = 0;
	private static final int IDX_RULE_NUM = 1;
	private static final int IDX_FALSE_SYMBOL = 2;
	private static final int IDX_TRUE_SYMBOL = 3;
	private static final int IDX_INITIAL_GENERATION = 4;
	private static final int IDX_NUM_EVOLVE = 5;

	private static final String ARG_NAMES = "ca rule-num false-symbol true-symbol initial-generation num-evolutions";
	
	// Source and class file names must match, so Application can be hard-coded
	private static final String USAGE_FMT_STRING_CLASS = "Usage: java Application " + ARG_NAMES;

	// The jar file may be renamed, so this will be varied
	private static final String USAGE_FMT_STRING_JAR = "Usage: java -jar %s " + ARG_NAMES;

	/**
	 * Stores the Arguments into a string array.
	 */
	private String[] appArgs;
	
	/**
	 * Creates an application with the argument of inputs.
	 * 
	 * @param args  The arguments the make an application.
	 */
	public Application(String[] args) {
		validateNumArgs(args);
	}

	/**
	 * Checks if the args array is the correct length
	 * 
	 * @param args  The arguments the make an application.
	 */
	private void validateNumArgs(String[] args) {
		if(args.length == NUM_EXPECTED_ARGS) {
			appArgs = new String[args.length];
			for(int i = 0; i < args.length; ++i)
				appArgs[i] = args[i];
		}
		else
			throwRuntimeExceptionWithUsageMessage();
	}

	/**
	 * Throws a run time exception with the message contaning the correct format.
	 */
	private void throwRuntimeExceptionWithUsageMessage() {
		// Implementation provided
		if (runningAsJar()) {
			// Get the path to the executing file
			String path = Application.class
					.getProtectionDomain()
					.getCodeSource()
					.getLocation()
					.getPath();
			// Only take path after last slash (to get file name).
			// A hard-coded slash is fine since Java URLs use /
			String file = path.substring(path.lastIndexOf("/") + 1);
			throw new RuntimeException(String.format(USAGE_FMT_STRING_JAR, file));
		} else {
			throw new RuntimeException(String.format(USAGE_FMT_STRING_CLASS));
		}
	}

	/**
	 * If Application is running as a JAR.
	 * @return Returns true if the Application is running as a JAR and false otherwise.
	 */
	private boolean runningAsJar() {
		// Implementation provided
		return Application.class
				.getResource("Application.class")
				.toString()
				.startsWith("jar");
	}

	/**
	 * Parses each of the six arguments, constructs the appropriate Automaton,
	 * and prints out the full evolution to System.out.
	 * @param args  The arguments the make an application (automaton with other added parts).
	 */
	private void parseArgs(String[] args){
		Automaton auto;
			try {
				CellularAutomaton.parse(appArgs[0]);
				Integer.parseInt(appArgs[5]);
				Generation gen = new Generation(appArgs[4], appArgs[3].charAt(0));
				auto = Automaton.createAutomaton(CellularAutomaton.parse(appArgs[0]), Integer.parseInt(appArgs[1]), gen);
				auto.falseSymbol = appArgs[2].charAt(0);
				auto.trueSymbol = appArgs[3].charAt(0);
				auto.evolve(Integer.parseInt(appArgs[5]));
				System.out.println(auto);
			} catch (NumberFormatException | RuleNumException | CellularAutomatonNotFoundException e) {
				throw new RuntimeException(e.getMessage());
			}
	}

	/**
	 * Calls the parseArgs(String[] args) method using the previously given arguments.
	 */
	public void run() {
		parseArgs(appArgs);
	}

	/**
	 * Constructs and runs an Application using the supplied main method arguments.
	 * @param args  The arguments the make an application.
	 */
	public static void main(String[] args) {
		Application app = new Application(args);
		try {
			app.run();
		}
		catch(RuntimeException e) {
			System.err.println(e.getMessage());
		}
	}
}
