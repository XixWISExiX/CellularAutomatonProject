import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Turns the Automaton to type TCA.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public class TotalisticAutomaton extends Automaton{
	
	/**
	 * Constructs an Automaton in type TCA.
	 * 
	 * @param ruleNum  The input number of the rule.
	 * @param initial  The initial generation.
	 * @throws RuleNumException If the ruleNum is not between the range 0 & 63.
	 */
	protected TotalisticAutomaton(int ruleNum, Generation initial) throws RuleNumException {
		super(ruleNum, initial);
	}
	
	/**
	 * Reads a file and turns it into a Automaton in type TCA.
	 * 
	 * @param filename  The name of the file.
	 * @throws FileNotFoundException If the file is not found.
	 * @throws IOException If program can't read the given file.
	 * @throws NumberFormatException If the content in the file is not written correctly.
	 * @throws RuleNumException If the ruleNum is not between the range 0 & 63.
	 */
	protected TotalisticAutomaton(String filename) throws FileNotFoundException, IOException, NumberFormatException, RuleNumException {
		super(filename);
	}
	
	/**
	 * Creates a rule in type TCA.
	 */
	protected Rule createRule(int ruleNum) throws RuleNumException {
		Rule rule = new TotalisticRule(ruleNum);
		return rule;
	}
}
