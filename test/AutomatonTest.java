import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class AutomatonTest {

	@Test
	void testAbstractDeclarations() {
		assertTrue(Modifier.isAbstract(Automaton.class.getModifiers()));

		try {
			Method method = Automaton.class.getDeclaredMethod("createRule",
					int.class);
			int modifiers = method.getModifiers();
			assertTrue(Modifier.isProtected(modifiers));
			assertTrue(Modifier.isAbstract(modifiers));
		} catch (NoSuchMethodException e) {
			fail();
		}
	}

	@Test
	void testConstructor() throws RuleNumException {
		Automaton automaton = new MockAutomaton();

		// Check the Rule.
		assertEquals(12345, automaton.getRuleNum());
		assertEquals("0 1", automaton.ruleTableString());

		// Check the Generations.
		assertEquals(0, automaton.getTotalSteps());
		assertArrayEquals(new boolean[] {true, false, false, true},
				automaton.getGeneration(0).getStates());
		assertEquals("1001", automaton.toString());
	}

	@Test
	void testEvolve() throws RuleNumException {
		Automaton automaton = new MockAutomaton();

		assertEquals(0, automaton.evolve(-1234));
		assertEquals(0, automaton.getTotalSteps());
		String evolution = "1001";
		assertEquals(evolution, automaton.toString());

		assertEquals(1, automaton.evolve(1));
		assertEquals(1, automaton.getTotalSteps());
		evolution =
				"1001" + System.lineSeparator() +
				"1111";
		assertEquals(evolution, automaton.toString());

		assertEquals(1, automaton.evolve(1));
		assertEquals(2, automaton.getTotalSteps());
		automaton.falseSymbol = '_';
		automaton.trueSymbol = '%';
		evolution =
				"%__%" + System.lineSeparator() +
				"%%%%" + System.lineSeparator() +
				"%%%%";
		assertEquals(evolution, automaton.toString());

		Generation gen = automaton.getGeneration(4);
		assertEquals("OOOO", gen.getStates('.', 'O'));
		assertEquals(4, automaton.getTotalSteps());
		automaton.falseSymbol = '.';
		automaton.trueSymbol = 'O';
		evolution =
				"O..O" + System.lineSeparator() +
				"OOOO" + System.lineSeparator() +
				"OOOO" + System.lineSeparator() +
				"OOOO" + System.lineSeparator() +
				"OOOO";
		assertEquals(evolution, automaton.toString());
	}
	
	@Test
	void testGetCurrentGenerationZero() throws RuleNumException {
		Automaton eca = new MockAutomaton();
		assertEquals("1001", 
				eca.getCurrentGeneration().getStates(eca.falseSymbol, eca.trueSymbol));
	}

	@Test
	void testGetCurrentGenerationFive() throws RuleNumException {
		Automaton eca = new MockAutomaton();
		eca.evolve(5);
		assertEquals("1111", 
				eca.getCurrentGeneration().getStates(eca.falseSymbol, eca.trueSymbol));
	}

	@Test
	void testSaveEvolution() throws RuleNumException, IOException {
		Automaton automaton = new MockAutomaton();
		automaton.falseSymbol = '-';
		automaton.trueSymbol = '+';
		String filename = "testSaveEvolution-output.txt";
		automaton.saveEvolution(filename);

		// Read the output file, check the contents, and delete it.
		File file = new File(filename);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		assertEquals("+--+", reader.readLine());
		reader.close();
		file.delete();
	}
	
	@Test
	void testCreateAutomaton() throws RuleNumException {
		Generation initial = new Generation(true, false, true);
		
		Automaton eca = Automaton.createAutomaton(CellularAutomaton.ECA, 22, initial);
		assertEquals(22, eca.getRuleNum());
		assertArrayEquals(new boolean[] {true, false, true}, eca.getCurrentGeneration().getStates());
		
		Automaton tca = Automaton.createAutomaton(CellularAutomaton.TCA, 22, initial);
		assertEquals(22, tca.getRuleNum());
		assertArrayEquals(new boolean[] {true, false, true}, tca.getCurrentGeneration().getStates());
	}
	
	@Test
	void testCreateAutomatonForZylabs() throws RuleNumException {
		Generation initial = new Generation(false, true, false, false, true);
		
		Automaton eca = Automaton.createAutomaton(CellularAutomaton.ECA, 189, initial);
		assertEquals(189, eca.getRuleNum());
		assertArrayEquals(new boolean[] {false, true, false, false, true}, eca.getCurrentGeneration().getStates());
		
		Automaton tca = Automaton.createAutomaton(CellularAutomaton.TCA, 60, initial);
		assertEquals(60, tca.getRuleNum());
		assertArrayEquals(new boolean[] {false, true, false, false, true}, tca.getCurrentGeneration().getStates());
		
		Automaton nca = Automaton.createAutomaton(null, 60, initial);
		assertNull(nca);
	}

	// Extend Automaton and Rule to test the non-abstract methods.
	private static class MockAutomaton extends Automaton {

		public MockAutomaton() throws RuleNumException {
			super(-1, new Generation(true, false, false, true));
		}

		@Override
		protected Rule createRule(int ruleNum) {
			return new MockRule();
		}
	}

	private static class MockRule extends Rule {

		public MockRule() {
			super(12345);
		}

		@Override
		public boolean evolve(boolean[] neighborhood) {
			return true;
		}

		@Override
		public boolean[] getNeighborhood(int idx, Generation gen) {
			return null;
		}

		@Override
		public String ruleTableString(char falseSymbol, char trueSymbol) {
			return falseSymbol + " " + trueSymbol;
		}
	}
}
