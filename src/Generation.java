import java.util.Arrays;

/**
 * This class represents a row of cells at a fixed time. Each Generation encapsulates a boolean array
 * that represents the cell states. The cells are indexed from left to right starting at zero, so the
 * state of the first cell is cellStates[0], the state of the second cell is cellStates[1], and so on.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class Generation {
	
	/**
	 * The boolean list of all the cells.
	 */
	private boolean[] cellStates;
	
	/**
	 * Generation mehtod for no inputs
	 */
	public Generation() {
		boolean[] nul = new boolean[1];
		nul[0] = false;
		cellStates = nul;
		cellStates = Arrays.copyOf(cellStates, size());
	}
	
	/**
	 * This class represents a row of cells at a fixed time. Each Generation encapsulates a boolean
	 * array that represents the cell states. The cells are indexed from left to right starting at zero,
	 * so the state of the first cell is cellStates[0], the state of the second cell is cellStates[1], and so on.
	 * 
	 * @param states  A list of boolean values.
	 */
	public Generation(boolean... states) {
		if(states == null) {
			boolean[] nul = new boolean[1];
			nul[0] = false;
			cellStates = nul;
		}
		else
			cellStates = states;
		cellStates = Arrays.copyOf(cellStates, size());
	}
	
	/**
	 * Creates a Generation with one cell for each character in the given String. If a character is equal to trueSymbol,
	 * the state of the corresponding cell is true; otherwise, the state is false. If the String is empty ("") or the
	 * method is given a null reference, this methods creates a Generation with one cell in the false state.
	 * 
	 * @param states  A list of values represented as a String.
	 * @param trueSymbol  The symbol of the true value.
	 */
	public Generation(String states, char trueSymbol) {
		if(states == null || states.compareTo("") == 0) {
			cellStates = new boolean[1];
			cellStates[0] = false;
		}
		else {
			cellStates = new boolean[states.length()];
			for(int i = 0; i < size(); ++i)
			{
				if(states.charAt(i) == trueSymbol) {
					cellStates[i] = true;
				}
				else
					cellStates[i] = false;
			}
		}
		cellStates = Arrays.copyOf(cellStates, size());
	}
	
	/**
	 * Returns the state of the cell with the given index.
	 * 
	 * @param idx  The index of the cell which the method checks.
	 * @return Returns the state of the cell.
	 */
	public boolean getState(int idx) {
		return Arrays.copyOf(cellStates, size())[idx];
	}
	
	/**
	 * Returns an array with all of the cell states.
	 * 
	 * @return Returns the array values of all the cells.
	 */
	public boolean[] getStates() {
		return Arrays.copyOf(cellStates, size());
	}
	
	/**
	 *  Returns a String representation of the cell states using falseSymbol and
	 *  trueSymbol as the symbols for false and true, respectively.
	 * 
	 * @param falseSymbol  The symbol of the false value.
	 * @param trueSymbol  The symbol of the true value.
	 * @return Returns the String of the states with the new symbols.
	 */
	public String getStates(char falseSymbol, char trueSymbol) {
		String list = "";
		for(int i = 0; i < size(); ++i) {
			if(cellStates[i] == false)
				list += falseSymbol;
			else
				list += trueSymbol;
		}
		return list;
	}
	
	/**
	 * Returns the number of cells.
	 * @return Returns the number of cells.
	 */
	public int size() {
		return cellStates.length;
	}
	
	/**
	 * Prints out the cells states.
	 */
	public String toString() {
		return Arrays.toString(cellStates);
	}
}
