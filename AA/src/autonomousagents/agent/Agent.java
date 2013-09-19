package autonomousagents.agent;

import autonomousagents.policy.Policy;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;

public abstract class Agent
{
	protected Policy policy;
	protected Point currentPosition;
	protected final Environment environment;

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

	public void moveTo(final Point point)
	{
		this.currentPosition = point;
	}
}
