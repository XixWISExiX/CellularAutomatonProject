import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApplicationTest {

	private static final InputStream SYSTEM_INPUT_STREAM = System.in;
	private static final PrintStream SYSTEM_OUTPUT_STREAM = System.out;
	private static final PrintStream SYSTEM_ERROR_STREAM = System.err;

	private ByteArrayOutputStream testOutputStream;
	private ByteArrayOutputStream testErrorStream;
	
	@BeforeEach
	public void redirectOutputStream() {
		// Capture the output of System.out in testOutputStream
		testOutputStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(testOutputStream));
	}
	
	@BeforeEach
	public void redirectErrorStream() {
		// Capture the output of System.err in testErrorStream
		testErrorStream = new ByteArrayOutputStream();
		System.setErr(new PrintStream(testErrorStream));
	}

	@AfterEach
	public void restoreSystemStreams() {
		System.setIn(SYSTEM_INPUT_STREAM);
		System.setOut(SYSTEM_OUTPUT_STREAM);
		System.setErr(SYSTEM_ERROR_STREAM);
	}

	private String getOutput() {
		return testOutputStream.toString();
	}
	
	private String getErrorOutput() {
		return testErrorStream.toString();
	}
	
	@Test
	void testConstructorValidArgs() {
		String[] args = {
				"eca",
				"22",
				"0", "1",
				"0001000",
				"3"
		};
		// Don't need to store Application in variable since only
		// the constructor is being tested
		new Application(args);
		
		// With valid constructor args, no output/errors should occur
		assertEquals("", getOutput());
		assertEquals("", getErrorOutput());
	}
	
	@Test
	void testConstructorInvalidArgs() {
		String[] args = {"W", "X", "Y", "Z"};
		try {
			// Don't need to store Application in variable since only
			// the constructor is being tested
			new Application(args);
			fail("Application should have failed due to invalid arguments");
		} catch (RuntimeException e) {
			assertEquals(
					"Usage: java Application ca rule-num false-symbol true-symbol initial-generation num-evolutions", 
					e.getMessage());
		}
		
		assertEquals("", getOutput());
	}
	
	@Test
	void testParseArgsInvalidCellularAutomaton() {
		String[] args = {"WXYZ", "22", "0", "1", "0001000", "3"};
		
		try {
			// Don't need to store Application in variable since only
			// the constructor is being tested
			Application app = new Application(args);
			app.run();
			fail("Application should have failed due an invalid CellularAutomaton");
		} catch (RuntimeException e) {
			assertEquals(
					"Unknown cellular automaton type WXYZ", 
					e.getMessage());
		}
		
		assertEquals("", getOutput());
	}
	
	@Test
	void testParseArgsInvalidRuleNumberParse() {
		String[] args = {"tca", "TWENTY", "0", "1", "0001000", "3"};
		try {
			Application app = new Application(args);
			app.run();

		} catch (RuntimeException e) {
			assertEquals(
					"For input string: \"TWENTY\"", 
					e.getMessage());
		}
		
		assertEquals("", getOutput());
	}
	
	@Test
	void testParseArgsInvalidRuleNumberBoundsECA() {
		String[] args = {"eca", "-1", "0", "1", "0001000", "3"};
		try {
			Application app = new Application(args);
			app.run();
			fail("Application should have failed due to an invalid rule number input");
		} catch (RuntimeException e) {
			assertEquals(
					"ruleNum is outside the range [0, 255].", 
					e.getMessage());
		}
		
		assertEquals("", getOutput());
	}
	
	@Test
	void testParseArgsInvalidRuleNumberBoundsTCA() {
		String[] args = {"tca", "-1", "0", "1", "0001000", "3"};
		try {
			Application app = new Application(args);
			app.run();
			fail("Application should have failed due to an invalid rule number input");
		} catch (RuntimeException e) {
			assertEquals(
					"ruleNum is outside the range [0, 63].", 
					e.getMessage());
		}
		
		assertEquals("", getOutput());
	}
	
	@Test
	void testMainInvalidArgs() {
		String[] args = {"a", "22", "0", "1", "0001000", "3"};
		Application.main(args);
		
		assertEquals("", getOutput());
		assertEquals("Unknown cellular automaton type a" + System.lineSeparator(), getErrorOutput());
	}
	
	@Test
	void testValidRun() {
		String[] args = {"eca", "22", "0", "1", "0001000", "3"};
		
		Application app = new Application(args);
		app.run();
		
		assertEquals(
				"0001000" + System.lineSeparator() +
				"0011100" + System.lineSeparator() +
				"0100010" + System.lineSeparator() +
				"1110111" + System.lineSeparator(), 
				getOutput());
		assertEquals("", getErrorOutput());
	}
	
	@Test
	void testValidRun2() {
		String[] args = {"eca", "22", ".", "O", "...O...", "3"};
		
		Application app = new Application(args);
		app = new Application(args);
		app.run();
		
		assertEquals(
				"...O..." + System.lineSeparator() +
				"..OOO.." + System.lineSeparator() +
				".O...O." + System.lineSeparator() +
				"OOO.OOO" + System.lineSeparator(), 
				getOutput());
		assertEquals("", getErrorOutput());
	}
	
	@Test
	void testParseArgeInvalidArgsEvolve() {
		String[] args = {"eca", "22", "0", "1", "0001000", "X"};
		try {
			Application app = new Application(args);
			app.run();
			fail("Application should have failed due to an invalid evolution input");
		} catch (RuntimeException e) {
			assertEquals(
					"For input string: \"X\"", 
					e.getMessage());
		}
		
		assertEquals("", getOutput());
	}
	
	@Test
	void testMainInvalidArgsEvolve() {
		String[] args = {"tca", "122", "0", "1", "0001000", "X"};
		Application.main(args);
		
		assertEquals("", getOutput());
		assertEquals("For input string: \"X\"" + System.lineSeparator(), getErrorOutput());
	}
	
	@Test
	void testMainInvalidArgsCellularAutomaton() {
		String[] args = {"X", "122", "0", "1", "0001000", "X"};
		Application.main(args);
		
		assertEquals("", getOutput());
		assertEquals("Unknown cellular automaton type X" + System.lineSeparator(), getErrorOutput());
	}
}
