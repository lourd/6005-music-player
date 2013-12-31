package player;

public class Together implements Playable {
	public final Playable p1;
	public final Playable p2;

	public Together(Playable p1, Playable p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public void accept(PlayableVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public double getDuration() {
		double d1 = p1.getDuration();
		double d2 = p2.getDuration();

		return (d1 > d2) ? d1 : d2;
	}
	
	@Override
	public void setDuration(double ratio){
	    p1.setDuration(ratio);
	    p2.setDuration(ratio);
	}

}
