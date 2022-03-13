import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class RuleNumExceptionTest {

	@Test
	void testInheritance() {
		RuleNumException exception = new RuleNumException(0, 0);
		assertTrue(exception instanceof Exception);
	}

	@Test
	void testConstructor() {
		Exception exception = new RuleNumException(42, 101);
		assertEquals("ruleNum is outside the range [42, 101].",
				exception.getMessage());

		exception = new RuleNumException(2334, -451);
		assertEquals("ruleNum is outside the range [2334, -451].",
				exception.getMessage());
	}
}
