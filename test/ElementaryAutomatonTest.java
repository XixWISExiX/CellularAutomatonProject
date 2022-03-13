import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class ElementaryAutomatonTest {

	@Test
	void testInheritance() throws RuleNumException {
		ElementaryAutomaton elementaryAutomaton =
				new ElementaryAutomaton(0, new Generation());
		assertTrue(elementaryAutomaton instanceof Automaton);
	}

	@Test
	void testConstructor() throws RuleNumException {
		int ruleNum = 255;
		Generation initial = new Generation(false, true, false);
		Automaton automaton = new ElementaryAutomaton(ruleNum, initial);

		// Check the Rule.
		String table =
				"111 110 101 100 011 010 001 000" + System.lineSeparator() +
				" 1   1   1   1   1   1   1   1 ";
		assertEquals(ruleNum, automaton.getRuleNum());
		assertEquals(table, automaton.ruleTableString());

		// Check the Generations.
		assertEquals(0, automaton.getTotalSteps());
		assertArrayEquals(new boolean[] {false, true, false},
				automaton.getGeneration(0).getStates());
		assertEquals("010", automaton.toString());
	}

	@Test
	void testEvolve() throws RuleNumException {
		Automaton automaton = new ElementaryAutomaton(22,
				new Generation("000000010000000", '1'));
		automaton.evolve(1);
		assertEquals(1, automaton.getTotalSteps());
		String evolution =
				"000000010000000" + System.lineSeparator() +
				"000000111000000";
		assertEquals(evolution, automaton.toString());

		automaton.evolve(2);
		assertEquals(3, automaton.getTotalSteps());
		automaton.falseSymbol = '_';
		automaton.trueSymbol = '%';
		evolution =
				"_______%_______" + System.lineSeparator() +
				"______%%%______" + System.lineSeparator() +
				"_____%___%_____" + System.lineSeparator() +
				"____%%%_%%%____";
		assertEquals(evolution, automaton.toString());

		Generation gen = automaton.getGeneration(7);
		assertEquals("OOO.OOO.OOO.OOO", gen.getStates('.', 'O'));
		assertEquals(7, automaton.getTotalSteps());
		automaton.falseSymbol = '.';
		automaton.trueSymbol = 'O';
		evolution =
				".......O......." + System.lineSeparator() +
				"......OOO......" + System.lineSeparator() +
				".....O...O....." + System.lineSeparator() +
				"....OOO.OOO...." + System.lineSeparator() +
				"...O.......O..." + System.lineSeparator() +
				"..OOO.....OOO.." + System.lineSeparator() +
				".O...O...O...O." + System.lineSeparator() +
				"OOO.OOO.OOO.OOO";
		assertEquals(evolution, automaton.toString());
	}
	
	@Test
	void testStringConstructor() throws NumberFormatException, RuleNumException, IOException {    
	    String inputFile = "test/eca-test-input.txt";
	    Automaton eca = new ElementaryAutomaton(inputFile);
	    assertEquals(40, eca.getRuleNum());
	    assertArrayEquals(new boolean[] { false, false, false, false, true, true, true, true, false },
	            eca.getGeneration(0).getStates());
	    assertEquals(0, eca.getTotalSteps());
	    assertEquals("XXXXOOOOX", eca.toString());
	}
}
