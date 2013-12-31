package player;

import java.util.List;

/**
 * Main entry point of your application.
 */
public class Main {

	/**
	 * Plays the input file using Java MIDI API and displays header information
	 * to the standard output stream.
	 * 
	 * <p>
	 * Your code <b>should not</b> exit the application abnormally using
	 * System.exit()
	 * </p>
	 * 
	 * @param file
	 *            the name of input abc file
	 * @throws Exception
	 */
	public static void play(String file) throws Exception {
		ABCFileReader reader;
		try {
		    System.out.println("Reading the file...");
			reader = new ABCFileReader(file);
			System.out.println("Setting up the parser...");
			ABCParser parser = new ABCParser(reader.header(), reader.voices());
			String str = reader.abcData();
			System.out.println("Tokenizing the data...");
			List<Token> l = ABCLexer.getTokens(str);
			System.out.println("Parsing...");
			Playable music = parser.parse(l);
			PlayableVisitor player = parser.makePlayableVisitor();

			reader.printHeader();
			System.out.println("Playing...");
			music.accept(player);
			player.play();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		try {

			play("sample_abc/invention.abc");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
