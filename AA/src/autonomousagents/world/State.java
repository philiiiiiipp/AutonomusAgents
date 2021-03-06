package autonomousagents.world;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.util.GameField;

/**
 * The State class encodes the positions of the Predator and Prey inside the
 * grid and presents methods to reduce the state space as much as possible
 */
public class State
{
	public enum TerminalStates
	{
		NOT_TERMINAL, PREY_WINS, PREDATOR_WINS
	}

	private List<Point> predatorPoints;
	private Point preyPoint;
	private double value;

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
		this.predatorPoints = new ArrayList<Point>();

		this.predatorPoints.add(predatorPoint);
		this.preyPoint = preyPoint;
		this.value = 0;
		this.mapToSimplifiedState();
	}

	public State(final List<Point> predatorPoints, final Point preyPoint)
	{
		this.predatorPoints = predatorPoints;
		this.preyPoint = preyPoint;
		this.value = 0;
		this.mapToSimplifiedState();
	}

	/**
	 * method that reduces the size of the state space
	 */
	private void mapToSimplifiedState()
	{
		List<Point> newPointList = new ArrayList<Point>();
		for (Point p : this.predatorPoints)
		{
			int xdistance = p.getX() - this.preyPoint.getX();
			int ydistance = p.getY() - this.preyPoint.getY();

			xdistance = (xdistance < 0 ? GameField.XMAX + xdistance : xdistance);
			ydistance = (ydistance < 0 ? GameField.YMAX + ydistance : ydistance);

			newPointList.add(new Point(xdistance, ydistance));
		}

		this.predatorPoints = newPointList;
		this.preyPoint = new Point(0, 0);

		// int xSum = 0;
		// int ySum = 0;
		// for (Point p : this.predatorPoints)
		// {
		// xSum += p.getX();
		// ySum += p.getY();
		// }
		//
		// if (xSum < ySum)
		// {
		// newPointList = new ArrayList<Point>();
		// for (Point p : this.predatorPoints)
		// {
		// newPointList.add(new Point(p.getY(), p.getX()));
		// }
		// this.predatorPoints = newPointList;
		// }
	}

	/**
	 * returns the Predator location on the grid
	 * 
	 * @return Point
	 */
	public Point predatorPoint()
	{
		return this.predatorPoints.get(0);
	}

	/**
	 * returns the Predator location on the grid
	 * 
	 * @return Point
	 */
	public List<Point> predatorPoints()
	{
		return this.predatorPoints;
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
		for (int i = 0; i < this.predatorPoints.size(); ++i)
		{
			if (this.predatorPoints.get(i).equals(this.preyPoint))
				return true;

			for (int j = i + 1; j < this.predatorPoints.size(); j++)
			{
				if (this.predatorPoints.get(i).equals(this.predatorPoints.get(j)))
					return true;
			}
		}
		return false;
	}

	/**
	 * check if a state is a terminal state, which occurs when the Predator is
	 * located in the same position as the Prey
	 * 
	 * @return boolean
	 */
	public TerminalStates getTerminalState()
	{
		boolean preyCatched = false;

		for (int i = 0; i < this.predatorPoints.size(); ++i)
		{
			if (this.predatorPoints.get(i).equals(this.preyPoint))
				preyCatched = true;

			for (int j = i + 1; j < this.predatorPoints.size(); j++)
			{
				if (this.predatorPoints.get(i).equals(this.predatorPoints.get(j)))
					return TerminalStates.PREY_WINS;
			}
		}

		if (preyCatched)
			return TerminalStates.PREDATOR_WINS;

		return TerminalStates.NOT_TERMINAL;
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

	/*
	 * Works for up to 4 agents.
	 */
	@Override
	public int hashCode()
	{
		int result = 0;
		int power = 4;

		for (Point p : this.predatorPoints)
		{
			result ^= p.getX();
			result = result << power;
			result ^= p.getY();
			result <<= power;
		}

		result ^= this.preyPoint.getX();
		result = result << power;
		result ^= this.preyPoint.getY();

		return result;
	}

	@Override
	public String toString()
	{
		String result = "";
		for (Point p : this.predatorPoints)
		{
			result += "Predator(" + p.getX() + "," + p.getY() + ") : ";
		}
		return result + "Prey(" + this.preyPoint.getX() + "," + this.preyPoint.getY() + ")";
	}
}
