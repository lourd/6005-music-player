package player;

import org.junit.Test;

public class ABCFileReaderTest {

	@Test
	public void piece1Test() {
		try {

			ABCFileReader reader = new ABCFileReader("sample_abc/piece1.abc");
			ABCParser parser = new ABCParser(reader.header(),reader.voices());
			Playable music = parser.parse(ABCLexer.getTokens(reader.abcData()));
			PlayableVisitor player = parser.makePlayableVisitor();
			music.accept(player);
			player.play();
			//System.out.println(reader.voices());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void standardRepeatsTest(){
	    try {

            ABCFileReader reader = new ABCFileReader("sample_abc/repeats.abc");
            ABCParser parser = new ABCParser(reader.header(),reader.voices());
            Playable music = parser.parse(ABCLexer.getTokens(reader.abcData()));
            PlayableVisitor player = parser.makePlayableVisitor();
            music.accept(player);
            //player.play();
            //System.out.println(reader.voices());

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("unused")
	@Test(expected=InvalidFileException.class)
	public void badHeaderTest() throws Exception, InvalidFileException{
	    ABCFileReader reader = new ABCFileReader("sample_abc/badHeader.abc");
	}
	

}
