package autonomousagents.actions;

import autonomousagents.agent.Agent;
import autonomousagents.util.Constants;
import autonomousagents.world.Point;

/**
 * Abstract action that the agent could take
 */
public abstract class Action
{
	private double probability;
	private double numerator = 0;
	private double denominator = 0;
	private double actionValue = Constants.QValue;

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

	public double getNumerator()
	{
		return this.numerator;
	}

	public void setNumerator(final double numerator)
	{
		this.numerator = numerator;
	}

	public double getDenominator()
	{
		return this.denominator;
	}

	public void setDenominator(final double denominator)
	{
		this.denominator = denominator;
	}

	/**
	 * Are those two actions the same
	 * 
	 * @param other
	 * @return
	 */
	public boolean isEqual(final Action other)
	{
		if (other.getClass() == this.getClass())
			return true;

		return false;
	}
}
