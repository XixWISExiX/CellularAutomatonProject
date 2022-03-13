
/**
 * TotalisticRule represents any one of the 64 rules that govern the evolution of totalistic CAs.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class TotalisticRule extends Rule{
	
	/**
	 * The binary of the rule up to 6 digits.
	 */
	private String binary;
	
	/**
	 * Rule must be between 0 & 63
	 * 
	 * @param ruleNum  The input for the rule.
	 * @throws RuleNumException  If the ruleNum is not between 0 & 63.
	 */
	protected TotalisticRule(int ruleNum) throws RuleNumException{
		super(ruleNum);
		if(ruleNum < 0 || ruleNum > 63)
			throw new RuleNumException(0, 63);
	}
	
	/**
	 * Evolves the neighborhood to the next generation and returns the of it (TCA).
	 */
	public boolean evolve(boolean[] neighborhood) {
		int count = 0;
		for(int i = 0; i < 5; ++i) {
			if(neighborhood[i])
				count++;
		}
		return getBinary().charAt(5-count) == '1';
	}
	
	/**
	 * Return the cell states in the neighborhood of the cell with the given index (TCA).
	 */
	public boolean[] getNeighborhood(int idx, Generation gen) throws ArrayIndexOutOfBoundsException{
		return getNeighborhoodByRadius(idx, 2, gen);
	}
	
	/**
	 * Return a two-line representation of the totalistic rule table.
	 */
	public String ruleTableString(char falseSymbol, char trueSymbol) {
		String ruleTable = "";
		String binary = getBinary();
		
		for(int i = 5; i > 0; --i)
			ruleTable += i + " ";
		ruleTable += 0 + System.lineSeparator();
		
		for(int i = 0; i < 5; ++i)
			ruleTable += valueChar(binary.charAt(i), falseSymbol, trueSymbol) + " ";
		ruleTable += valueChar(binary.charAt(5), falseSymbol, trueSymbol);
		return ruleTable;
	}
	
	/**
	 * Returns the correct binary for the rule.
	 * @return Returns the correct binary for the rule.
	 */
	public String getBinary() {
	binary = String.format("%06d", Integer.valueOf(Integer.toBinaryString(getRuleNum())));
	return binary;
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
