package autonomousagents.actions;

import autonomousagents.agent.Agent;
import autonomousagents.util.GameField;
import autonomousagents.world.Point;

public class NorthAction extends Action
{

	public NorthAction(final double probability)
	{
		super(probability);
	}

	@Override
	public void apply(final Agent a)
	{
		int newY = a.getPosition().getY() == GameField.YMIN ? GameField.YMAX
				: a.getPosition().getY() - 1;

		a.moveTo(new Point(a.getPosition().getX(), newY));
	}

	@Override
	public Point apply(final Point p)
	{
		int newY = p.getY() == GameField.YMIN ? GameField.YMAX : p.getY() - 1;
		return new Point(p.getX(), newY);
	}
}
