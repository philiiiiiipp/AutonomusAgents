package autonomousagents.actions;

import autonomousagents.Agent;

public abstract class Action
{
	private float probability;

	public Action(final float probability)
	{
		this.probability = probability;
	}

	public abstract void apply(final Agent a);

	public float getProbability()
	{
		return this.probability;
	}

	public void setProbability(final float probability)
	{
		this.probability = probability;
	}
}
