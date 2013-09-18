package autonomousagents;


public class State
{
	private final Point predatorPoint;
	private final Point preyPoint;

	public State(final Point predatorPoint, final Point preyPoint)
	{
		this.predatorPoint = predatorPoint;
		this.preyPoint = preyPoint;
	}

	public Point predatorPoint()
	{
		return this.predatorPoint;
	}

	public Point preyPoint()
	{
		return this.preyPoint;
	}

	@Override
	public boolean equals(final Object object)
	{
		if (object instanceof State)
		{
			return object.hashCode() == this.hashCode();
		}

		return false;
	}

	@Override
	public int hashCode()
	{
		int result = 0;
		int power = 4;

		result ^= this.predatorPoint.getX();
		result = result << power;
		result ^= this.predatorPoint.getY();
		result <<= power;
		result ^= this.preyPoint.getX();
		result = result << power;
		result ^= this.preyPoint.getY();

		return result;
	}
}
