
/**
 * This throws an exception if the Range is outside the min and max values.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class RuleNumException extends Exception {
	
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Prints out the ruleNum ranges which the value should be inside.
	 * 
	 * @param min  The lowest value the rule can be.
	 * @param max  The highest value the rule can be.
	 */
	public RuleNumException(int min, int max) {
		super("ruleNum is outside the range [" + min + ", " + max + "].");
	}
}
