package autonomousagents.world;

public class State
{
	private final Point predatorPoint;
	private final Point preyPoint;
	private double value;

	public State mapToSimplifiedState()
	{
		int xdistance = Math.abs(this.predatorPoint.getX()
				- this.preyPoint.getX());
		int ydistance = Math.abs(this.predatorPoint.getY()
				- this.preyPoint.getY());

		xdistance = Math.min(xdistance, 11 - xdistance);
		ydistance = Math.min(ydistance, 11 - ydistance);
		// System.out.println(xdistance);
		// System.out.println(ydistance);

		return new State(new Point(xdistance, ydistance), new Point(0, 0));
	}

	public State(final Point predatorPoint, final Point preyPoint)
	{
		this.predatorPoint = predatorPoint;
		this.preyPoint = preyPoint;
		this.value = 0;
	}

	public Point predatorPoint()
	{
		return this.predatorPoint;
	}

	public Point preyPoint()
	{
		return this.preyPoint;
	}

	public boolean isTerminal()
	{
		return this.predatorPoint.equals(this.preyPoint);
	}

	public double getValue()
	{
		return this.value;
	}

	public void setValue(final double value)
	{
		this.value = value;
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
