import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

class RuleTest {

	@Test
	void testAbstractDeclarations() {
		assertTrue(Modifier.isAbstract(Rule.class.getModifiers()));

		try {
			Method method = Rule.class.getDeclaredMethod("evolve",
					boolean[].class);
			int modifiers = method.getModifiers();
			assertTrue(Modifier.isAbstract(modifiers));
		} catch (NoSuchMethodException e) {
			fail();
		}

		try {
			Method method = Rule.class.getDeclaredMethod("getNeighborhood",
					int.class, Generation.class);
			int modifiers = method.getModifiers();
			assertTrue(Modifier.isAbstract(modifiers));
		} catch (NoSuchMethodException e) {
			fail();
		}

		try {
			Method method = Rule.class.getDeclaredMethod("ruleTableString",
					char.class, char.class);
			int modifiers = method.getModifiers();
			assertTrue(Modifier.isAbstract(modifiers));
		} catch (NoSuchMethodException e) {
			fail();
		}
	}

	@Test
	void testConstructor() {
		Rule rule = new MockRule();
		assertEquals(2334, rule.getRuleNum());
	}

	@Test
	void testEvolveGeneration() {
		Generation current = new Generation("RLRLRRLRLRLL", 'R');

		Rule rule = new MockRule();
		Generation next = rule.evolve(current);
		assertEquals("LLLLLLLLLLLL", next.getStates('L', 'R'));
	}

	@Test
	void testGetNeighborhoodByRadius() {
		Generation current = new Generation("10001001", '1');
		boolean[] result = Rule.getNeighborhoodByRadius(0, 3, current);
		assertArrayEquals(new boolean[] {false, false, true, true, false, false, false}, result);
		
		current = new Generation("10", '1');
		result = Rule.getNeighborhoodByRadius(0, 2, current);
		assertArrayEquals(new boolean[] {true, false, true, false, true}, result);
		
		current = new Generation("10", '1');
		result = Rule.getNeighborhoodByRadius(1, 2, current);
		assertArrayEquals(new boolean[] {false, true, false, true, false}, result);
		
		current = new Generation("101", '1');
		result = Rule.getNeighborhoodByRadius(1, 20, current);
		assertArrayEquals(new boolean[] {
				true,
				true, false, true, true, false, true, true, false, true,
				true, false, true, true, false, true, true, false, true,
				true,
				false,
				true,
				true, false, true, true, false, true, true, false, true,
				true, false, true, true, false, true, true, false, true,
				true
		}, result);
	}
	
	// Extend Rule with a concrete class to test the non-abstract methods.
	private static class MockRule extends Rule {

		public MockRule() {
			super(2334);
		}

		@Override
		public boolean evolve(boolean[] neighborhood) {
			return false;
		}

		@Override
		public boolean[] getNeighborhood(int idx, Generation gen) {
			return null;
		}

		@Override
		public String ruleTableString(char falseSymbol, char trueSymbol) {
			return null;
		}
	}
}
