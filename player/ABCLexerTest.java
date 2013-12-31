package player;

import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ABCLexerTest {

	// Single element tests

	@Test
	public void basicNoteTest() throws Exception { // need try/catch instead of
													// throws?
		List<Token> expected = new ArrayList<Token>();
		String input = "b";
		expected.add(new Token(Type.PITCH, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void complicatedNoteTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "^^^C,,,/4";
		expected.add(new Token(Type.PITCH, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void basicBarTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "|";
		expected.add(new Token(Type.BAR, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void doubleBarTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "||";
		expected.add(new Token(Type.DOUBLE_BAR, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void bracketBarTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "|]";
		expected.add(new Token(Type.DOUBLE_BAR, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void startRepeatTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "|:";
		expected.add(new Token(Type.START_REPEAT, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void endRepeatTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = ":|";
		expected.add(new Token(Type.END_REPEAT, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void basicRestTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "z";
		expected.add(new Token(Type.REST, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void restLengthTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "z4";
		expected.add(new Token(Type.REST, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void voiceTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "V:2";
		expected.add(new Token(Type.VOICE, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				System.out.println(ex.getType());
				System.out.println(abc.getType());
				System.out.println(ex.getValue());
				System.out.println(abc.getValue());
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void multiNoteTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "[Bc3/4^^d''4__e,,2]";
		expected.add(new Token(Type.MULTI_NOTE, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void tupletTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "(3abc";
		expected.add(new Token(Type.TUPLET_ELEMENT, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void repeat1Test() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "[1";
		expected.add(new Token(Type.REPEAT1, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void repeat2Test() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "[2";
		expected.add(new Token(Type.REPEAT2, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void noNumeratorNoteLengthTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "A/4";
		expected.add(new Token(Type.PITCH, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void noDenominatorNoteLengthTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "a3/";
		expected.add(new Token(Type.PITCH, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

	@Test
	public void noNumOrDenomNoteLengthTest() throws Exception {
		List<Token> expected = new ArrayList<Token>();
		String input = "a/";
		expected.add(new Token(Type.PITCH, input));
		boolean condition = false;
		for (Token ex : expected) {
			for (Token abc : ABCLexer.getTokens(input)) {
				condition = (ex.getType().equals(abc.getType()) && ex
						.getValue().equals(abc.getValue()));
			}
		}
		assertTrue(condition);
	}

}
