package player;

public class Note implements Playable {
	public final Character pitch;
	public double duration;
	public final int semitonesUp;

	public Note(Character pitch, int semitonesUp, double duration) {
		this.pitch = pitch;
		this.semitonesUp = semitonesUp;
		this.duration = duration;
	}
	
	@Override
	public void accept(PlayableVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public double getDuration() {
		return duration;
	}
	
	@Override
	public void setDuration(double ratio){
	    duration=duration*ratio;
	}

}
