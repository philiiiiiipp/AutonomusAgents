package autonomousagents.actions;

import autonomousagents.Agent;
import autonomousagents.Point;

public abstract class Action
{
	private double probability;

	public Action(final double probability)
	{
		this.probability = probability;
	}

	/**
	 * Applies the given action action to an agent
	 * 
	 * @param a
	 */
	public abstract void apply(final Agent a);

	/**
	 * Applies this action to a point, and returns a new point with the new
	 * position
	 * 
	 * @param p
	 * @return
	 */
	public abstract Point apply(final Point p);

	public double getProbability()
	{
		return this.probability;
	}

	public void setProbability(final double probability)
	{
		this.probability = probability;
	}
}
