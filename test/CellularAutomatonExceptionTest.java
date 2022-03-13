import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CellularAutomatonExceptionTest {

	@Test
	void testException() {
		Exception e = new CellularAutomatonNotFoundException("LMNO");
		assertEquals("Unknown cellular automaton type LMNO", e.getMessage());
	}
}
