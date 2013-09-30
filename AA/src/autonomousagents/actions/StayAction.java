package autonomousagents.actions;

import autonomousagents.agent.Agent;
import autonomousagents.world.Point;

public class StayAction extends Action
{

	public StayAction(final double probability)
	{
		super(probability);
	}

	@Override
	public void apply(final Agent a)
	{
	}

	@Override
	public Point apply(final Point p)
	{
		return p;
	}

	@Override
	public String toString()
	{
		return "x";
	}
}
