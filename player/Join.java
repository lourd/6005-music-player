package player;

public class Join implements Playable {

	final Playable p1;
	final Playable p2;

	public Join(Playable p1, Playable p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public void accept(PlayableVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public double getDuration() {
		return p1.getDuration() + p2.getDuration();
	}
	
	@Override
	public void setDuration(double ratio){
	    p1.setDuration(ratio);
	    p2.setDuration(ratio);
	}

}
