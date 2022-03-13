import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class TotalisticRuleTest {

	@Test
	void testInheritance() throws RuleNumException {
		TotalisticRule totalisticRule = new TotalisticRule(0);
		assertTrue(totalisticRule instanceof Rule);
	}

	@Test
	void testConstructor() throws RuleNumException {
		Rule rule = new TotalisticRule(22);
		assertEquals(22, rule.getRuleNum());

		rule = new TotalisticRule(30);
		assertEquals(30, rule.getRuleNum());

		// Check edge cases.
		try {
			rule = new TotalisticRule(-1);
			fail();
		} catch (RuleNumException e) {
			assertEquals("ruleNum is outside the range [0, 63].", e.getMessage());
		}

		try {
			rule = new TotalisticRule(110);
			fail();
		} catch (RuleNumException e) {
			assertEquals("ruleNum is outside the range [0, 63].", e.getMessage());
		}
	}

	@Test
	void testGetNeighborhood() throws RuleNumException {
		Rule rule = new TotalisticRule(0);
		Generation gen = new Generation(false, true, true, false, false, true);

		boolean[] neighborhood = rule.getNeighborhood(2, gen);
		assertArrayEquals(new boolean[] {false, true, true, false, false},
				neighborhood);

		neighborhood = rule.getNeighborhood(3, gen);
		assertArrayEquals(new boolean[] {true, true, false, false, true},
				neighborhood);

		// Check boundary conditions.
		neighborhood = rule.getNeighborhood(0, gen);
		assertArrayEquals(new boolean[] {false, true, false, true, true},
				neighborhood);

		neighborhood = rule.getNeighborhood(1, gen);
		assertArrayEquals(new boolean[] {true, false, true, true, false},
				neighborhood);

		neighborhood = rule.getNeighborhood(4, gen);
		assertArrayEquals(new boolean[] {true, false, false, true, false},
				neighborhood);

		neighborhood = rule.getNeighborhood(5, gen);
		assertArrayEquals(new boolean[] {false, false, true, false, true},
				neighborhood);
	}

	@Test
	void testGetRuleTable() throws RuleNumException {
		Rule rule = new TotalisticRule(22);
		String table =
				"5 4 3 2 1 0" + System.lineSeparator() +
				"0 1 0 1 1 0";
		assertEquals(table, rule.ruleTableString('0', '1'));

		rule = new TotalisticRule(30);
		table = "5 4 3 2 1 0" + System.lineSeparator() +
				"F T T T T F";
		assertEquals(table, rule.ruleTableString('F', 'T'));
		
		rule = new TotalisticRule(32);
		table = "5 4 3 2 1 0" + System.lineSeparator() +
				"O . . . . .";
		assertEquals(table, rule.ruleTableString('.', 'O'));
		
		rule = new TotalisticRule(63);
		table = "5 4 3 2 1 0" + System.lineSeparator() +
				"O O O O O O";
		assertEquals(table, rule.ruleTableString('.', 'O'));
		
		rule = new TotalisticRule(0);
		table = "5 4 3 2 1 0" + System.lineSeparator() +
				". . . . . .";
		assertEquals(table, rule.ruleTableString('.', 'O'));
		
		rule = new TotalisticRule(43);
		table = "5 4 3 2 1 0" + System.lineSeparator() +
				"x x x x x x";
		assertEquals(table, rule.ruleTableString('x', 'x'));
		
		rule = new TotalisticRule(1);
		table = "5 4 3 2 1 0" + System.lineSeparator() +
				". . . . . x";
		assertEquals(table, rule.ruleTableString('.', 'x'));
	}

	@Test
	void testEvolveNeighborhood() throws RuleNumException {
		Rule rule = new TotalisticRule(22);

		// Check total 5.
		assertFalse(rule.evolve(new boolean[] {true, true, true, true, true}));

		// Check total 4.
		assertTrue(rule.evolve(new boolean[] {false, true, true, true, true}));
		assertTrue(rule.evolve(new boolean[] {true, false, true, true, true}));
		assertTrue(rule.evolve(new boolean[] {true, true, false, true, true}));
		assertTrue(rule.evolve(new boolean[] {true, true, true, false, true}));
		assertTrue(rule.evolve(new boolean[] {true, true, true, true, false}));

		// Check total 3.
		assertFalse(rule.evolve(new boolean[] {false, false, true, true, true}));
		assertFalse(rule.evolve(new boolean[] {false, true, false, true, true}));
		assertFalse(rule.evolve(new boolean[] {false, true, true, false, true}));
		assertFalse(rule.evolve(new boolean[] {false, true, true, true, false}));
		assertFalse(rule.evolve(new boolean[] {true, false, false, true, true}));
		assertFalse(rule.evolve(new boolean[] {true, false, true, false, true}));
		assertFalse(rule.evolve(new boolean[] {true, false, true, true, false}));
		assertFalse(rule.evolve(new boolean[] {true, true, false, false, true}));
		assertFalse(rule.evolve(new boolean[] {true, true, false, true, false}));
		assertFalse(rule.evolve(new boolean[] {true, true, true, false, false}));

		// Check total 2.
		assertTrue(rule.evolve(new boolean[] {true, true, false, false, false}));
		assertTrue(rule.evolve(new boolean[] {true, false, true, false, false}));
		assertTrue(rule.evolve(new boolean[] {true, false, false, true, false}));
		assertTrue(rule.evolve(new boolean[] {true, false, false, false, true}));
		assertTrue(rule.evolve(new boolean[] {false, true, true, false, false}));
		assertTrue(rule.evolve(new boolean[] {false, true, false, true, false}));
		assertTrue(rule.evolve(new boolean[] {false, true, false, false, true}));
		assertTrue(rule.evolve(new boolean[] {false, false, true, true, false}));
		assertTrue(rule.evolve(new boolean[] {false, false, true, false, true}));
		assertTrue(rule.evolve(new boolean[] {false, false, false, true, true}));

		// Check total 1.
		assertTrue(rule.evolve(new boolean[] {true, false, false, false, false}));
		assertTrue(rule.evolve(new boolean[] {false, true, false, false, false}));
		assertTrue(rule.evolve(new boolean[] {false, false, true, false, false}));
		assertTrue(rule.evolve(new boolean[] {false, false, false, true, false}));
		assertTrue(rule.evolve(new boolean[] {false, false, false, false, true}));

		// Check total 0.
		assertFalse(rule.evolve(new boolean[] {false, false, false, false, false}));
	}

	@Test
	void testEvolveGeneration() throws RuleNumException {
		// Define a binary De Bruijn sequence of order 5.
		String states = "00000100011001010011101011011111";
		Generation current = new Generation(states, '1');

		Rule rule = new TotalisticRule(22);
		Generation next = rule.evolve(current);
		assertEquals("11011111111011111000100000111010", next.getStates('0', '1'));
	}
}
