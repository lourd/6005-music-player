package player;

import java.util.ArrayList;
import java.util.Iterator;

public class Tuplet implements Playable {

	final ArrayList<Playable> playables;
	final double ratio;

	public Tuplet(ArrayList<Playable> playables) {
		this.playables=playables;
		int numNotes = playables.size();
		switch(numNotes){
		case 2:
		    ratio = 3.0/2.0;
		    break;
		case 3:
		    ratio = 2.0/3.0;
		    break;
		case 4:
		    ratio = 4.0/3.0;
		    break;
		default:
		    // Should never reach here
		    ratio = 1;
		}
		Iterator<Playable> iter = this.playables.iterator();
		while(iter.hasNext()){
		    iter.next().setDuration(ratio);
		}
	}
	
	@Override
	public void accept(PlayableVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public double getDuration() {
		double duration=0;
		Iterator<Playable> iter = playables.iterator();
		while(iter.hasNext()){
		    duration+=iter.next().getDuration();
		}
		return duration;
	}
	
	@Override
	public void setDuration(double ratio){
	    Iterator<Playable> iter = playables.iterator();
	    while(iter.hasNext()){
	        iter.next().setDuration(ratio);
	    }
	}

}