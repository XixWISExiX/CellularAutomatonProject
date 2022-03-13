import java.util.Arrays;

/**
 * ElementaryRule represents any one of the 256 rules that govern the evolution of elementary CAs.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class ElementaryRule extends Rule {
	
	/**
	 * The binary of the rule up to 8 digits.
	 */
	private String binary;
	
	/**
	 * Rule must be between 0 & 255
	 * 
	 * @param ruleNum  The input for the rule.
	 * @throws RuleNumException  If the ruleNum is not between 0 & 255.
	 */
	protected ElementaryRule(int ruleNum) throws RuleNumException {
		super(ruleNum);
		if(ruleNum < 0 || ruleNum > 255)
			throw new RuleNumException(0, 255);
	}
	
	/**
	 * Evolves the neighborhood to the next generation and returns the of it (ECA).
	 */
	public boolean evolve(boolean[] neighborhood) {
		String binary = getBinary();
		for(int i = 0; i < 8; ++i) {
			if(Arrays.equals(values(i), neighborhood) && binary.charAt(i) == '1')
				return true;
		}
		return false;
	}
	
	/**
	 * Return the cell states in the neighborhood of the cell with the given index (ECA).
	 */
	public boolean[] getNeighborhood(int idx, Generation gen) throws ArrayIndexOutOfBoundsException{
		return getNeighborhoodByRadius(idx, 1, gen);
	}
	
	/**
	 * Returns a two-line representation of the elementary rule table. The first line
	 * shows the 8 possible neighborhoods separated by spaces; the second shows the states
	 * of the center cells in the next generation. Align each state character on the second line
	 * with the center of the corresponding neighborhood.
	 */
	public String ruleTableString(char falseSymbol, char trueSymbol) {
		String ruleTable = "";
		String binary = getBinary();
		for(int i = 0; i < 7; ++i)
			ruleTable += valuesChar(values(i), falseSymbol, trueSymbol) + " ";
		ruleTable += valuesChar(values(7), falseSymbol, trueSymbol) + System.lineSeparator();
		for(int i = 0; i < 7; ++i)
			ruleTable += " " + valueChar(binary.charAt(i), falseSymbol, trueSymbol) + "  ";
		ruleTable += " " + valueChar(binary.charAt(7), falseSymbol, trueSymbol) + " ";
		return ruleTable;
	}
	
	/**
	 * Returns the correct binary for the rule.
	 * @return Returns the correct binary for the rule.
	 */
	public String getBinary() {
	binary = Integer.toBinaryString(getRuleNum());
	if(getRuleNum() < 2)
		binary = "0000000" + binary;
	else if(getRuleNum() < 4)
		binary = "000000" + binary;
	else if(getRuleNum() < 8)
		binary = "00000" + binary;
	else if(getRuleNum() < 16)
		binary = "0000" + binary;
	else if(getRuleNum() < 32)
		binary = "000" + binary;
	else if(getRuleNum() < 64)
		binary = "00" + binary;
	else if(getRuleNum() < 128)
		binary = "0" + binary;
	return binary;
	}

	/**
	 * Values which the binary is compared to to determine whether the next value is true of false.
	 * @param value  Character position of the binary.
	 * @return Returns an array of list to compare to in other methods.
	 */
	public boolean[] values(int value) {
		boolean[] list = new boolean[3];
		if(value == 0) {
			list[0] = true;
			list[1] = true;
			list[2] = true;
		}
		else if(value == 1) {
			list[0] = true;
			list[1] = true;
			list[2] = false;
		}
		else if(value == 2) {
			list[0] = true;
			list[1] = false;
			list[2] = true;
		}
		else if(value == 3) {
			list[0] = true;
			list[1] = false;
			list[2] = false;
		}
		else if(value == 4) {
			list[0] = false;
			list[1] = true;
			list[2] = true;
		}
		else if(value == 5) {
			list[0] = false;
			list[1] = true;
			list[2] = false;
		}
		else if(value == 6) {
			list[0] = false;
			list[1] = false;
			list[2] = true;
		}
		else if(value == 7) {
			list[0] = false;
			list[1] = false;
			list[2] = false;
		}
		return list;
	}
	
	/**
	 * Makes a string from the values.
	 * 
	 * @param values  The values of the booleans.
	 * @param falseSymbol  The symbol of the false value.
	 * @param trueSymbol  The symbol of the true value.
	 * @return Returns the String of the value whether it be 1 or 0.
	 */
	public String valuesChar(boolean[] values, char falseSymbol, char trueSymbol) {
		String valueList = "";
		for(int i = 0; i < values.length; ++i) {
			if(values[i])
				valueList += trueSymbol;
			else
				valueList += falseSymbol;
		}
		return valueList;
	}
	
	/**
	 * Makes a string from the value.
	 * 
	 * @param value  The value of the character.
	 * @param falseSymbol  The symbol of the false value.
	 * @param trueSymbol  The symbol of the true value.
	 * @return Returns the String of the value whether it be 1 or 0.
	 */
	public String valueChar(char value, char falseSymbol, char trueSymbol) {
		String valueList = "";
		if(value == '1')
			valueList += trueSymbol;
		else
			valueList += falseSymbol;
		return valueList;
	}
}
