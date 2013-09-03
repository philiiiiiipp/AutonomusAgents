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

	public boolean isPresent(final Point p)
	{
		return this.currentPosition.equals(p);
	}

	public abstract boolean step();

}
