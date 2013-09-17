package autonomousagents.actions;

import autonomousagents.Agent;
import autonomousagents.Point;
import autonomousagents.State;

public class SouthAction extends Action
{
	public SouthAction(final float probability)
	{
		super(probability);
	}

	@Override
	public void apply(final Agent a)
	{
		int newY = a.getPosition().getY() == State.YMAX ? State.YMIN : a
				.getPosition().getY() + 1;

		a.moveTo(new Point(a.getPosition().getX(), newY));
	}

}
