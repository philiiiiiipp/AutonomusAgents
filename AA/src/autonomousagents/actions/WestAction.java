package autonomousagents.actions;

import autonomousagents.agent.Agent;
import autonomousagents.util.GameField;
import autonomousagents.world.Point;

public class WestAction extends Action
{

	public WestAction(final double probability)
	{
		super(probability);
	}

	@Override
	public void apply(final Agent a)
	{
		int newX = a.getPosition().getX() == GameField.XMIN ? GameField.XMAX
				: a.getPosition().getX() - 1;
		a.moveTo(new Point(newX, a.getPosition().getY()));
	}

	@Override
	public Point apply(final Point p)
	{
		int newX = p.getX() == GameField.XMIN ? GameField.XMAX : p.getX() - 1;
		return new Point(newX, p.getY());
	}
}
