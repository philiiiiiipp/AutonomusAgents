package autonomousagents.actions;

import autonomousagents.Agent;
import autonomousagents.Point;
import autonomousagents.State;

public class NorthAction extends Action
{

	public NorthAction(final float probability)
	{
		super(probability);
	}

	@Override
	public void apply(final Agent a)
	{
		int newY = a.getPosition().getY() == State.YMIN ? State.YMAX : a
				.getPosition().getY() - 1;

		a.moveTo(new Point(a.getPosition().getX(), newY));
	}
}
