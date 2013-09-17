package autonomousagents;

import autonomousagents.policy.Policy;

public abstract class Agent
{
	protected Policy policy;
	protected Point currentPosition;
	protected final Environment environment;

	// protected static Random RAND = new Random();

	public Agent(final Point p, final Environment environment,
			final Policy policy)
	{
		this.policy = policy;
		this.currentPosition = p;
		this.environment = environment;
	}

	public Point getPosition()
	{
		return this.currentPosition;
	}

	public boolean isPresent(final Point p)
	{
		return this.currentPosition.equals(p);
	}

	public abstract boolean step();

	public abstract boolean canIGoThere(final Point p);

	public void moveTo(final Point point)
	{
		this.currentPosition = point;
	}
}
