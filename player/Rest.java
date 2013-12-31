package player;

public class Rest implements Playable {
	public double duration;

	public Rest(double duration) {
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
	    duration = duration*ratio;
	}

}
