import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The abstract Automaton class represents any 1D, two-state CA that evolves according to a rule represented by the Rule class.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public abstract class Automaton {
	
	/**
	 * The Rule of the automaton.
	 */
	private Rule rule;
	
	/**
	 * The array list of generations.
	 */
	private ArrayList<Generation> generations;
	
	/**
	 * The false value of the Automaton.
	 */
	char falseSymbol = '0';
	
	/**
	 * The true value of the Automaton.
	 */
	char trueSymbol = '1';
	
	/**
	 * 
	 * @param ruleNum  The input number of the rule.
	 * @param initial  The initial generation.
	 * @throws RuleNumException If the ruleNum is not between the range of rules.
	 */
	protected Automaton(int ruleNum, Generation initial) throws RuleNumException {
		generations = new ArrayList<Generation>();
		generations.add(initial);
		rule = createRule(ruleNum);
	}
	
	/**
	 * Reads a file and turns it into a Automaton.
	 * 
	 * @param filename  The name of the file.
	 * @throws FileNotFoundException If the file is not found.
	 * @throws IOException If program can't read the given file.
	 * @throws NumberFormatException If the content in the file is not written correctly.
	 * @throws RuleNumException If the ruleNum is not between the range of rules.
	 */
	protected Automaton(String filename) throws FileNotFoundException, IOException, NumberFormatException, RuleNumException {
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			rule = createRule(Integer.parseInt(br.readLine()));
			falseSymbol = (char) br.read();
			br.read();
			trueSymbol = (char) br.read();
			br.readLine();
			generations = new ArrayList<Generation>();
			generations.add(new Generation(br.readLine(), trueSymbol));
		}

	}
	
	/**
	 * Evolves the generation baised on the rule type given.
	 * 
	 * @param numSteps  How many times the generation should evolve.
	 * @return Returns the number of times the generation evolved.
	 */
	public int evolve(int numSteps) {		
		if(numSteps > 0) {
            for(int i = 0; i < numSteps; ++i) {
            	generations.add(rule.evolve(getCurrentGeneration()));
            }
            return numSteps; 
        }
		else {
            return 0;
        }
	}
	
	/**
	 * Gets the generation of the input.
	 * 
	 * @param stepNum  The generation number of the generation.
	 * @return Returns the generation of number given.
	 */
	public Generation getGeneration(int stepNum) {
		evolve((stepNum + 1) - generations.size());
		return generations.get(stepNum);
	}
	
	/**
	 * Gets the current Generation.
	 * 
	 * @return Returns the most recent generation evolved.
	 */
	public Generation getCurrentGeneration() {
		return generations.get(generations.size()-1);
	}
	
	/**
	 * Gets the number of the rule.
	 * 
	 * @return Returns the number of the rule.
	 */
	public int getRuleNum() {
		return rule.getRuleNum();
	}
	
	/**
	 * Gets the number of evolutions made.
	 * 
	 * @return Returns the number of evolutions made.
	 */
	public int getTotalSteps() {
		return generations.size()-1;
	}
	
	/**
	 * Prints out every generation.
	 */
	public String toString() {
		String output = "";
		if(getTotalSteps() > 0) {
			for(int i = 0; i < getTotalSteps(); ++i) {
				output += generations.get(i).getStates(falseSymbol, trueSymbol);
				output += System.lineSeparator();
			}
		}
		else {
			return generations.get(0).getStates(falseSymbol, trueSymbol);
		}
		output += getCurrentGeneration().getStates(falseSymbol, trueSymbol);
		return output;
	}
	
	/**
	 * Saves the printed out Automaton into a file.
	 * 
	 * @param filename  The name of the file.
	 * @throws IOException If program can't read the given file.
	 */
	public void saveEvolution(String filename) throws IOException {
		
		BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
		bw.flush();
		for(int i = 0; i <= getTotalSteps(); ++i) {
			bw.write(getGeneration(i).getStates(falseSymbol, trueSymbol));
			bw.newLine();
		}
		bw.close();
	}
	
	/**
	 * Writes out the ruleTable as a string and returns it.
	 * 
	 * @return Returns the ruleTable string.
	 */
	public String ruleTableString() {
		return rule.ruleTableString(falseSymbol, trueSymbol);
	}
	
	/**
	 * Creates the Rule of the Automaton.
	 * 
	 * @param ruleNum  Number of the rule.
	 * @return Returns the rule.
	 * @throws RuleNumException If the ruleNum is not between the range of rules.
	 */
	protected abstract Rule createRule(int ruleNum) throws RuleNumException;
	
	/**
	 * Returns an instance of the appropriate Automaton child given a CellularAutomaton value (ECA or TCA).
	 * This static method returns null if ca is null.
	 * 
	 * @param ca  CellularAutomaton type.
	 * @param ruleNum  Number of the rule.
	 * @param initial  The initial generation.
	 * @return Returns an Automaton with the input given.
	 * @throws RuleNumException If the ruleNum is not between the range of rules.
	 */
	public static Automaton createAutomaton(CellularAutomaton ca, int ruleNum, Generation initial) throws RuleNumException {
		
		if(ca == CellularAutomaton.ECA) {
			return new ElementaryAutomaton(ruleNum, initial);
		}
		if(ca == CellularAutomaton.TCA) {
			return new TotalisticAutomaton(ruleNum, initial);
		}
		return null;
	}
}
