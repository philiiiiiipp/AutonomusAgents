import java.util.Random;

public abstract class Agent
{

	protected Point currentPosition;
	protected final State state;
	protected static Random RAND = new Random(0);

	public Agent(final Point p, final State s)
	{
		this.currentPosition = p;
		this.state = s;
	}

	public boolean isPresent(final int x, final int y)
	{
		return this.currentPosition.getX() == x
				&& this.currentPosition.getY() == y;
	}

	public abstract boolean step();

}
