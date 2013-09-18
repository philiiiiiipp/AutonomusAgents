package autonomousagents.actions;

import autonomousagents.Agent;
import autonomousagents.Point;

public class StayAction extends Action
{

	public StayAction(final float probability)
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

}
