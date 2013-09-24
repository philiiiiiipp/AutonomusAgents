package autonomousagents.actions;

import autonomousagents.agent.Agent;
import autonomousagents.world.Point;

/**
 * Abstract action that the agent could take
 */
public abstract class Action
{
	private double probability;
	private double actionValue = 15;

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

	public double getActionValue()
	{
		return this.actionValue;
	}

	public void setActionValue(final double actionValue)
	{
		this.actionValue = actionValue;
	}
}
