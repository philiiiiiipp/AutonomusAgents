package autonomousagents.util;

import autonomousagents.world.Point;
import autonomousagents.world.State;

public class ValueMap
{
	private final double[][] simplifiedValueMap = new double[6][6];

	public double getValueForState(final State s)
	{
		State simplifiedState = State.translateStrate(s);
		return this.simplifiedValueMap[simplifiedState.predatorPoint().getX()][simplifiedState
				.predatorPoint().getY()];
	}

	public double getValueForState(final Point predatorPoint,
			final Point preyPoint)
	{
		State simplifiedState = new State(predatorPoint, preyPoint);
		return this.simplifiedValueMap[simplifiedState.predatorPoint().getX()][simplifiedState
				.predatorPoint().getY()];
	}

	public void setValueForState(final State s, final double value)
	{
		State simplifiedState = State.translateStrate(s);
		this.simplifiedValueMap[simplifiedState.predatorPoint().getX()][simplifiedState
				.predatorPoint().getY()] = value;

	}

	public void setValueForState(final Point predatorPoint,
			final Point preyPoint, final double value)
	{
		State simplifiedState = new State(predatorPoint, preyPoint);
		this.simplifiedValueMap[simplifiedState.predatorPoint().getX()][simplifiedState
				.predatorPoint().getY()] = value;

	}

}
