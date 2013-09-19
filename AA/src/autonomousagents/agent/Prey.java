package autonomousagents.agent;

import autonomousagents.policy.Policy;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;

public class Prey extends Agent
{

	public Prey(final Point p, final Environment environment,
			final Policy policy)
	{
		super(p, environment, policy);
	}

	@Override
	public boolean step()
	{
		this.policy
				.nextProbabalisticActionForState(this.environment.getState())
				.apply(this);
		return false;
	}
}