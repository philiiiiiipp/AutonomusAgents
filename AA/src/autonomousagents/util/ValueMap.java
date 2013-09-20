package autonomousagents.util;

import autonomousagents.world.Point;
import autonomousagents.world.State;

/**
 * Class used to assign/return the values to/for states
 */
public class ValueMap
{
	private final double[][] simplifiedValueMap = new double[6][6];

	/**
	 * method that returns the value for a State s given the simplified
	 * representation of the size of the state space
	 * 
	 * @param s
	 *            State to which we want to assign a value
	 * @return double
	 */
	public double getValueForState(final State s)
	{
		State simplifiedState = State.translateState(s);
		return this.simplifiedValueMap[simplifiedState.predatorPoint().getX()][simplifiedState.predatorPoint().getY()];
	}

	/**
	 * method that takes as input parameters the location of the Predator and
	 * Prey and returns the value of the current state given the simplified
	 * representation of the size of the state space
	 * 
	 * @param predatorPoint
	 *            Predator
	 * @param preyPoint
	 *            Prey
	 * @return double
	 */
	public double getValueForState(final Point predatorPoint, final Point preyPoint)
	{
		State simplifiedState = new State(predatorPoint, preyPoint);
		return this.simplifiedValueMap[simplifiedState.predatorPoint().getX()][simplifiedState.predatorPoint().getY()];
	}

	/**
	 * method that takes as input parameters a State s and a value and assigns
	 * the respective value to the current State
	 * 
	 * @param s
	 * @param value
	 */
	public void setValueForState(final State s, final double value)
	{
		State simplifiedState = State.translateState(s);
		this.simplifiedValueMap[simplifiedState.predatorPoint().getX()][simplifiedState.predatorPoint().getY()] = value;

	}

	/**
	 * method that takes as input parameters the locations of a Predator and the
	 * Prey as well as a value and assigns the respective value to the current
	 * State
	 * 
	 * @param predatorPoint
	 * @param preyPoint
	 * @param value
	 */
	public void setValueForState(final Point predatorPoint, final Point preyPoint, final double value)
	{
		State simplifiedState = new State(predatorPoint, preyPoint);
		this.simplifiedValueMap[simplifiedState.predatorPoint().getX()][simplifiedState.predatorPoint().getY()] = value;

	}

}
