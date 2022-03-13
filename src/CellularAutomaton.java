
/**
 * CellularAutomaton is an enum representing a type of cellular automaton. It includes a
 * parse(String s) method that converts a given string to a CellularAutomaton value. The
 * parse method throws an exception if the given string is not recognized.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public enum CellularAutomaton {

	/**
	 * The cellular automaton type ECA.
	 */
	ECA,
	
	/**
	 * The cellular automaton type TCA.
	 */
	TCA;
	
	/**
	 * 
	 * @param s  The string input which is parsed to be either ECA or TCA.
	 * @return Returns the cellular automaton type.
	 * @throws CellularAutomatonNotFoundException If the cellular automaton type is not ECA or TCA.
	 */
	public static CellularAutomaton parse(String s) throws CellularAutomatonNotFoundException {
		if (s.equalsIgnoreCase("ECA")) { 
			return ECA;
		}
		else if (s.equalsIgnoreCase("TCA")) {
			return TCA;
		}
		else {
			throw new CellularAutomatonNotFoundException(s);
		}
	}
}
