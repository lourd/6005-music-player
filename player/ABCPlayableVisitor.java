package player;

import java.util.Iterator;

import javax.sound.midi.MidiUnavailableException;

import sound.Pitch;
import sound.SequencePlayer;

public class ABCPlayableVisitor implements PlayableVisitor {
	private int nextStart;
	private int ticksPerQuarterNote;
	public SequencePlayer player;

	public ABCPlayableVisitor(SequencePlayer player, int ticksPerQuarterNote) {
		this.player = player;
		this.nextStart = 0;
		this.ticksPerQuarterNote = ticksPerQuarterNote;
	}

	@Override
	public void visit(Note note) {
	    // Multiply by 4 to normalize a quarter duration to 1
	    int tickLength = (int) (ticksPerQuarterNote*note.duration*4);
		player.addNote(new Pitch(note.pitch).transpose(note.semitonesUp)
				.toMidiNote(), nextStart, tickLength);
		nextStart += tickLength;
	}

	@Override
	public void visit(Join join) {
		join.p1.accept(this);
		join.p2.accept(this);
	}

	@Override
	public void visit(Rest rest) {
	    // Multiply by 4 to normalize a quarter duration to 1
	    int tickLength = (int) (ticksPerQuarterNote*rest.duration*4);
		nextStart += tickLength;
	}

	@Override
	public void visit(Together together) {
		int startPoint = nextStart;

		together.p1.accept(this);
		int endPoint = nextStart;
		nextStart = startPoint;
		together.p2.accept(this);

		if (endPoint > nextStart) {
			nextStart = endPoint;
		}
	}

	@Override
	public void visit(Tuplet tuplet) {
		Iterator<Playable> iter = tuplet.playables.iterator();
		while(iter.hasNext()){
		    iter.next().accept(this);
		}
	}
	
	@Override
	public void play() throws MidiUnavailableException {
		player.play();
	}
}
