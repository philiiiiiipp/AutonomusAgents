package autonomousagents;

import java.util.Random;

public abstract class Agent
{

	protected Point currentPosition;
	protected final State state;
	protected static Random RAND = new Random();

	public Agent(final Point p, final State s)
	{
		this.currentPosition = p;
		this.state = s;
	}

	public Point getPoint()
	{
		return this.currentPosition;
	}

	public boolean isPresent(final Point p)
	{
		return this.currentPosition.equals(p);
	}

	public abstract boolean step();

	public abstract boolean canIGoThere(final Point p);

}
