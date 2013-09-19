package autonomousagents.actions;

import autonomousagents.agent.Agent;
import autonomousagents.util.GameField;
import autonomousagents.world.Point;

public class SouthAction extends Action
{
	public SouthAction(final double probability)
	{
		super(probability);
	}

	@Override
	public void apply(final Agent a)
	{
		int newY = a.getPosition().getY() == GameField.YMAX ? GameField.YMIN
				: a.getPosition().getY() + 1;

		a.moveTo(new Point(a.getPosition().getX(), newY));
	}

	@Override
	public Point apply(final Point p)
	{
		int newY = p.getY() == GameField.YMAX ? GameField.YMIN : p.getY() + 1;
		return new Point(p.getX(), newY);
	}

}
