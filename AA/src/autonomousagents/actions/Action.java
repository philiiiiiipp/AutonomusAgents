package autonomousagents.actions;

import autonomousagents.Agent;
import autonomousagents.Point;

public abstract class Action
{
	private float probability;

	public Action(final float probability)
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

	public float getProbability()
	{
		return this.probability;
	}

	public void setProbability(final float probability)
	{
		this.probability = probability;
	}
}
