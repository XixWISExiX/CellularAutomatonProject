import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class TotalisticAutomatonTest {

	@Test
	void testInheritance() throws RuleNumException {
		TotalisticAutomaton totalisticAutomaton =
				new TotalisticAutomaton(0, new Generation());
		assertTrue(totalisticAutomaton instanceof Automaton);
	}

	@Test
	void testConstructor() throws RuleNumException {
		int ruleNum = 63;
		Generation initial = new Generation(true, false, true);
		Automaton automaton = new TotalisticAutomaton(ruleNum, initial);

		// Check the Rule.
		String table =
				"5 4 3 2 1 0" + System.lineSeparator() +
				"1 1 1 1 1 1";
		assertEquals(ruleNum, automaton.getRuleNum());
		assertEquals(table, automaton.ruleTableString());

		// Check the Generations.
		assertEquals(0, automaton.getTotalSteps());
		assertArrayEquals(new boolean[] {true, false, true},
				automaton.getGeneration(0).getStates());
		assertEquals("101", automaton.toString());
	}

	@Test
	void testEvolve() throws RuleNumException {
		Automaton automaton = new TotalisticAutomaton(22,
				new Generation("00000000000000100000000000000", '1'));
		automaton.evolve(1);
		assertEquals(1, automaton.getTotalSteps());
		String evolution =
				"00000000000000100000000000000" + System.lineSeparator() +
				"00000000000011111000000000000";
		assertEquals(evolution, automaton.toString());

		automaton.evolve(2);
		assertEquals(3, automaton.getTotalSteps());
		automaton.falseSymbol = '_';
		automaton.trueSymbol = '%';
		evolution =
				"______________%______________" + System.lineSeparator() +
				"____________%%%%%____________" + System.lineSeparator() +
				"__________%%_%_%_%%__________" + System.lineSeparator() +
				"________%%%___%___%%%________";
		assertEquals(evolution, automaton.toString());

		Generation gen = automaton.getGeneration(7);
		assertEquals("OOO..OOOOO.OOO.OOO.OOOOO..OOO", gen.getStates('.', 'O'));
		assertEquals(7, automaton.getTotalSteps());
		automaton.falseSymbol = '.';
		automaton.trueSymbol = 'O';
		evolution =
				"..............O.............." + System.lineSeparator() +
				"............OOOOO............" + System.lineSeparator() +
				"..........OO.O.O.OO.........." + System.lineSeparator() +
				"........OOO...O...OOO........" + System.lineSeparator() +
				"......OO...OOOOOOO...OO......" + System.lineSeparator() +
				"....OOOOOOO.O...O.OOOOOOO...." + System.lineSeparator() +
				"..OO.O...OO.OOOOO.OO...O.OO.." + System.lineSeparator() +
				"OOO..OOOOO.OOO.OOO.OOOOO..OOO";
		assertEquals(evolution, automaton.toString());
	}
	
	@Test
	void testStringConstructor() throws NumberFormatException, RuleNumException, IOException {    
	    String inputFile = "test/tca-test-input.txt";
	    Automaton eca = new TotalisticAutomaton(inputFile);
	    assertEquals(22, eca.getRuleNum());
	    assertArrayEquals(new boolean[] { false, false, false, true, false, false, false },
	            eca.getGeneration(0).getStates());
	    assertEquals(0, eca.getTotalSteps());
	    assertEquals("XXXOXXX", eca.toString());
	}
}
