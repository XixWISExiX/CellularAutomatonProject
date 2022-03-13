
/**
 * The abstract Rule class represents any rule that governs the evolution of a 1D,
 * two-state CA.1 The behavior of different rule types (e.g., elementary, totalistic) is
 * simulated by extending the class.
 * 
 * @author Joshua Wiseman
 * @version 0.1
 */
public abstract class Rule {
	
	/**
	 * The number of the rule.
	 */
	private static int rule;
	
	/**
	 * Constructs the rule with ruleNum.
	 * 
	 * @param ruleNum  The input number of the rule.
	 */
	protected Rule(int ruleNum) {
		rule = ruleNum;
	}
	
	/**
	 * Returns the rule number.
	 * 
	 * @return Returns the rule number.
	 */
	public int getRuleNum() {
		return rule;
	}
	
	/**
	 * Return the cell states in the neighborhood of the cell with the given index.
	 * 
	 * @param idx  Index which the neighborhood will be around.
	 * @param gen  The generation of which the neighborhood will function in.
	 * @return Returns the neighborhood.
	 */
	public abstract boolean[] getNeighborhood(int idx, Generation gen);
	
	/**
	 * Evolves the generation depending on the type called.
	 * 
	 * @param neighborhood  The neighborhood which the method is going to evlove.
	 * @return Returns whether the neighborhood should be true or false.
	 */
	public abstract boolean evolve(boolean[] neighborhood);
	
	/**
	 * Evolves the generation with the Generation input.
	 * 
	 * @param gen  The generation which is going to evolve.
	 * @return Returns the evolved generation.
	 */
	public Generation evolve(Generation gen) {
		boolean[] nextGen = new boolean[gen.size()];
	    for (int i = 0; i < gen.size(); ++i) {
	    	nextGen[i] = evolve(getNeighborhood(i, gen));
	    }
	    return new Generation(nextGen);
	}
	
	/**
	 * Return the table that depicts the rule using the given characters to represent false and true.
	 * 
	 * @param falseSymbol  The symbol of the false value.
	 * @param trueSymbol  The symbol of the true value.
	 * @return Returns a String of the rule table.
	 */
	public abstract String ruleTableString(char falseSymbol, char trueSymbol);
	
	/**
	 * Return the cell states in the neighborhood of the cell with the given index and specified radius.
	 * The radius refers to the additional number of cells to the left/right of the given index.
	 * 
	 * @param idx  Index which the neighborhood will be around.
	 * @param radius  The range which you grab the values near the Index.
	 * @param gen  The generation of which the neighborhood will function in.
	 * @return Returns the neighborhood.
	 */
	public static boolean[] getNeighborhoodByRadius(int idx, int radius, Generation gen) {
		boolean[] nextNeighborhood = new boolean[radius*2+1];
		for(int i = 0; i < nextNeighborhood.length; ++i)
			nextNeighborhood[i] = gen.getStates()[Math.floorMod(idx - radius + i, gen.size())];
		return nextNeighborhood;
	}
}
