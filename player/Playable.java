package player;

public interface Playable {
	public void accept(PlayableVisitor visitor);

	public double getDuration();
	
	public void setDuration(double ratio);
}
