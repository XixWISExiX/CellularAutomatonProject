import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class CellularAutomatonTest {

	@Test
	void testConstants() {
		assertEquals("ECA", CellularAutomaton.ECA.name());
		assertEquals("TCA", CellularAutomaton.TCA.name());
	}
	
	@Test
	void testParse() throws CellularAutomatonNotFoundException {
		assertEquals(CellularAutomaton.ECA, CellularAutomaton.parse("ECA"));
		assertEquals(CellularAutomaton.TCA, CellularAutomaton.parse("TCA"));
		
		try {
			CellularAutomaton.parse("I <3 Java");
			fail("CellularAutomatonNotFoundException should have been thrown");
		} catch (CellularAutomatonNotFoundException e) {
			// This catch block should execute
			assertEquals("Unknown cellular automaton type I <3 Java", e.getMessage());
		}
	}
}
