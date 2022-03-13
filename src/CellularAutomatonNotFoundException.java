
/**
 * CellularAutomaton is an enum representing a type of cellular automaton. It includes a parse(String s)
 * method that converts a given string to a CellularAutomaton value. The parse method throws an exception
 * if the given string is not recognized.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class CellularAutomatonNotFoundException extends Exception {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prints out the String if the automaton type is not ECA or TCA.
	 * 
	 * @param s  The type of CellularAutomaton being used.
	 */
	public CellularAutomatonNotFoundException(String s) {
		super("Unknown cellular automaton type " + s);
	}
}
