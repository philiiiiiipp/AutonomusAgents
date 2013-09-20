package autonomousagents.world;

/* The State class encodes the positions of the 
 * Predator and Prey inside the grid and presents methods to reduce 
 * the state space as much as possible
 */
public class State
{
	private Point predatorPoint;
	private Point preyPoint;
	private double value;

	// method that reduces the size of the state space
	private void mapToSimplifiedState()
	{
		int xdistance = Math.abs(this.predatorPoint.getX()
				- this.preyPoint.getX());
		int ydistance = Math.abs(this.predatorPoint.getY()
				- this.preyPoint.getY());

		xdistance = Math.min(xdistance, 11 - xdistance);
		ydistance = Math.min(ydistance, 11 - ydistance);
		this.predatorPoint = new Point(xdistance, ydistance);
		this.preyPoint = new Point(0, 0);
	}

	// constructor where we initialize the state with the position of the
	// Predator and the Prey
	public State(final Point predatorPoint, final Point preyPoint)
	{
		this.predatorPoint = predatorPoint;
		this.preyPoint = preyPoint;
		this.value = 0;
		this.mapToSimplifiedState();
	}

	// Method that associates the value of a state with the value of another
	// state in order to reduce the state space
	public static State translateState(final State s)
	{
		int xdistance = Math.abs(s.predatorPoint.getX() - s.preyPoint.getX());
		int ydistance = Math.abs(s.predatorPoint.getY() - s.preyPoint.getY());

		xdistance = Math.min(xdistance, 11 - xdistance);
		ydistance = Math.min(ydistance, 11 - ydistance);

		return new State(new Point(xdistance, ydistance), new Point(0, 0));

	}

	// returns the Predator location on the grid
	public Point predatorPoint()
	{
		return this.predatorPoint;
	}

	// returns the Prey location on the grid
	public Point preyPoint()
	{
		return this.preyPoint;
	}

	// check if a state is a terminal state, which occurs when the
	// Predator is located in the same position as the Prey
	public boolean isTerminal()
	{
		return this.predatorPoint.equals(this.preyPoint);
	}

	// method that returns the value of a state
	public double getValue()
	{
		return this.value;
	}

	// method that sets the value of a state
	public void setValue(final double value)
	{
		this.value = value;
	}

	@Override
	// method that checks if two States are equal
	public boolean equals(final Object object)
	{
		if (object instanceof State)
		{
			return object.hashCode() == this.hashCode();
		}

		return false;
	}

	@Override
	// Method that computes the hash code of a State so that both positions
	// of Prey and Predator are encoded
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
