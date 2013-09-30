package autonomousagents.world;

import autonomousagents.util.GameField;

/**
 * The State class encodes the positions of the Predator and Prey inside the
 * grid and presents methods to reduce the state space as much as possible
 */
public class State
{
	private Point predatorPoint;
	private Point preyPoint;
	private double value;

	/**
	 * method that reduces the size of the state space
	 */
	private void mapToSimplifiedState()
	{
		int xdistance = this.predatorPoint.getX() - this.preyPoint.getX();
		int ydistance = this.predatorPoint.getY() - this.preyPoint.getY();

		xdistance = (xdistance < 0 ? GameField.XMAX + xdistance : xdistance);
		ydistance = (ydistance < 0 ? GameField.YMAX + ydistance : ydistance);

		this.predatorPoint = new Point(xdistance, ydistance);
		this.preyPoint = new Point(0, 0);

		/*
		 * int xdistance = Math.abs(this.predatorPoint.getX() -
		 * this.preyPoint.getX()); int ydistance =
		 * Math.abs(this.predatorPoint.getY() - this.preyPoint.getY());
		 * 
		 * xdistance = Math.min(xdistance, 11 - xdistance); ydistance =
		 * Math.min(ydistance, 11 - ydistance);
		 * 
		 * if (xdistance < ydistance) { int tmp = xdistance; xdistance =
		 * ydistance; ydistance = tmp; }
		 * 
		 * this.predatorPoint = new Point(xdistance, ydistance); this.preyPoint
		 * = new Point(0, 0);
		 */
	}

	/**
	 * constructor where we initialize the state with the position of the
	 * Predator and the Prey
	 * 
	 * @param predatorPoint
	 *            - position of the Predator
	 * @param preyPoint
	 *            - position of the Prey
	 */
	public State(final Point predatorPoint, final Point preyPoint)
	{
		this.predatorPoint = predatorPoint;
		this.preyPoint = preyPoint;
		this.value = 0;
		this.mapToSimplifiedState();
	}

	/**
	 * Method that associates the value of a state with the value of another
	 * state in order to reduce the state space
	 * 
	 * @deprecated because of old way of calculating the minimised statespace
	 * 
	 * @param s
	 *            State to be reduced with its symmetrical state
	 * @return
	 */
	@Deprecated
	public static State translateState(final State s)
	{
		int xdistance = Math.abs(s.predatorPoint.getX() - s.preyPoint.getX());
		int ydistance = Math.abs(s.predatorPoint.getY() - s.preyPoint.getY());

		xdistance = Math.min(xdistance, 11 - xdistance);
		ydistance = Math.min(ydistance, 11 - ydistance);

		return new State(new Point(xdistance, ydistance), new Point(0, 0));
	}

	/**
	 * returns the Predator location on the grid
	 * 
	 * @return Point
	 */
	public Point predatorPoint()
	{
		return this.predatorPoint;
	}

	/**
	 * returns the Prey location on the grid
	 * 
	 * @return Point
	 */
	public Point preyPoint()
	{
		return this.preyPoint;
	}

	/**
	 * check if a state is a terminal state, which occurs when the Predator is
	 * located in the same position as the Prey
	 * 
	 * @return boolean
	 */
	public boolean isTerminal()
	{
		return this.predatorPoint.equals(this.preyPoint);
	}

	/**
	 * method that returns the value of a state
	 * 
	 * @return double
	 */
	public double getValue()
	{
		return this.value;
	}

	/**
	 * method that sets the value of a state
	 * 
	 * @param value
	 *            - the value to set for the current state
	 */
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

	@Override
	public String toString()
	{
		return "Predator(" + this.predatorPoint.getX() + "," + this.predatorPoint.getY() + ") : Prey("
				+ this.preyPoint.getX() + "," + this.preyPoint.getY() + ")";
	}
}
