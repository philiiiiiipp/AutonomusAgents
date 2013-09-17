package autonomousagents.actions;

import autonomousagents.Agent;
import autonomousagents.Point;
import autonomousagents.State;

public class EastAction extends Action
{

	public EastAction(final float probability)
	{
		super(probability);
	}

	@Override
	public void apply(final Agent a)
	{
		int newX = a.getPosition().getX() == State.XMAX ? State.XMIN : a
				.getPosition().getX() + 1;
		a.moveTo(new Point(newX, a.getPosition().getY()));
	}

}
