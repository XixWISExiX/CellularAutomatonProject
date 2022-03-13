import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

class ElementaryRuleTest {

	@Test
	void testInheritance() throws RuleNumException {
		ElementaryRule elementaryRule = new ElementaryRule(0);
		assertTrue(elementaryRule instanceof Rule);
	}

	@Test
	void testConstructor() throws RuleNumException {
		Rule rule = new ElementaryRule(22);
		assertEquals(22, rule.getRuleNum());

		rule = new ElementaryRule(30);
		assertEquals(30, rule.getRuleNum());

		// Check edge cases.
		try {
			rule = new ElementaryRule(-42);
			fail();
		} catch (RuleNumException e) {
			assertEquals("ruleNum is outside the range [0, 255].", e.getMessage());
		}

		try {
			rule = new ElementaryRule(2334);
			fail();
		} catch (RuleNumException e) {
			assertEquals("ruleNum is outside the range [0, 255].", e.getMessage());
		}
	}

	@Test
	void testGetNeighborhood() throws RuleNumException {
		Rule rule = new ElementaryRule(0);
		Generation gen = new Generation(false, true, true, false);

		boolean[] neighborhood = rule.getNeighborhood(1, gen);
		assertArrayEquals(new boolean[] {false, true, true}, neighborhood);

		neighborhood = rule.getNeighborhood(2, gen);
		assertArrayEquals(new boolean[] {true, true, false}, neighborhood);

		// Check boundary conditions.
		neighborhood = rule.getNeighborhood(0, gen);
		assertArrayEquals(new boolean[] {false, false, true}, neighborhood);

		neighborhood = rule.getNeighborhood(3, gen);
		assertArrayEquals(new boolean[] {true, false, false}, neighborhood);
	}

	@Test
	void testGetRuleTable() throws RuleNumException {
		Rule rule = new ElementaryRule(22);
		String table =
				"111 110 101 100 011 010 001 000" + System.lineSeparator() +
				" 0   0   0   1   0   1   1   0 ";
		assertEquals(table, rule.ruleTableString('0', '1'));

		rule = new ElementaryRule(30);
		table = "TTT TTF TFT TFF FTT FTF FFT FFF" + System.lineSeparator() +
				" F   F   F   T   T   T   T   F ";
		assertEquals(table, rule.ruleTableString('F', 'T'));
	}

	@Test
	void testEvolveNeighborhood() throws RuleNumException {
		Rule rule = new ElementaryRule(22);
		assertFalse(rule.evolve(new boolean[] {true, true, true}));
		assertFalse(rule.evolve(new boolean[] {true, true, false}));
		assertFalse(rule.evolve(new boolean[] {true, false, true}));
		assertTrue(rule.evolve(new boolean[] {true, false, false}));
		assertFalse(rule.evolve(new boolean[] {false, true, true}));
		assertTrue(rule.evolve(new boolean[] {false, true, false}));
		assertTrue(rule.evolve(new boolean[] {false, false, true}));
		assertFalse(rule.evolve(new boolean[] {false, false, false}));

		rule = new ElementaryRule(30);
		assertFalse(rule.evolve(new boolean[] {true, true, true}));
		assertFalse(rule.evolve(new boolean[] {true, true, false}));
		assertFalse(rule.evolve(new boolean[] {true, false, true}));
		assertTrue(rule.evolve(new boolean[] {true, false, false}));
		assertTrue(rule.evolve(new boolean[] {false, true, true}));
		assertTrue(rule.evolve(new boolean[] {false, true, false}));
		assertTrue(rule.evolve(new boolean[] {false, false, true}));
		assertFalse(rule.evolve(new boolean[] {false, false, false}));
	}

	@Test
	void testEvolveGeneration() throws RuleNumException {
		Generation current = new Generation("10001110", '1');

		Rule rule = new ElementaryRule(22);
		Generation next = rule.evolve(current);
		assertEquals("11010000", next.getStates('0', '1'));

		rule = new ElementaryRule(30);
		next = rule.evolve(current);
		assertEquals("TTFTTFFF", next.getStates('F', 'T'));
	}
}
