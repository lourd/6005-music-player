package player;

import javax.sound.midi.MidiUnavailableException;

public interface PlayableVisitor {

	public void visit(Note note);

	public void visit(Join join);

	public void visit(Rest rest);

	public void visit(Together together);

	public void visit(Tuplet tuplet);
	
	public void play() throws MidiUnavailableException;

}
