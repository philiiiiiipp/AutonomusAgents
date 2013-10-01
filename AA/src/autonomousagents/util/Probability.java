package autonomousagents.util;

public class Probability<T>
{
	private final T object;
	private final double probability;

	public Probability(final T object, final double probability)
	{
		this.object = object;
		this.probability = probability;
	}

	public T getObject()
	{
		return object;
	}

	public double getProbability()
	{
		return probability;
	}

}
