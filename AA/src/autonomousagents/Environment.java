package autonomousagents;

import java.util.ArrayList;
import java.util.List;

public class Environment
{
	List<Agent> agents = new ArrayList<Agent>();

	public State getState()
	{
		List<Point> points = new ArrayList<Point>();
		for (Agent a : this.agents)
		{
			points.add(a.getPosition());
		}

		return new State(points);
	}

	public void addAgent(final Agent a)
	{
		this.agents.add(a);
	}

	public List<Agent> getAgents()
	{
		return this.agents;
	}

	public boolean isEndState()
	{
		boolean endState = false;
		Agent agent = null;
		for (Agent a : this.agents)
		{
			if (agent == null)
			{
				agent = a;
				continue;
			}

			endState = endState || agent.getPosition().equals(a.getPosition());
		}

		return endState;
	}

	// (0:0) North West
	// (XMAX:0) North East
	// (0:YMAX) South West
	// (XMAX : YMAX) South East

	/**
	 * 
	 * @param pointToTranslate
	 * @return
	 */
	public static Point north(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == State.YMIN ? State.YMAX
				: pointToTranslate.getY() - 1;
		Point p = new Point(pointToTranslate.getX(), newY);
		return p;
	}

	public static Point south(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == State.YMAX ? State.YMIN
				: pointToTranslate.getY() + 1;
		Point p = new Point(pointToTranslate.getX(), newY);
		return p;
	}

	public static Point east(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == State.XMAX ? State.XMIN
				: pointToTranslate.getX() + 1;
		Point p = new Point(newX, pointToTranslate.getY());
		return p;
	}

	public static Point west(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == State.XMIN ? State.XMAX
				: pointToTranslate.getX() - 1;
		Point p = new Point(newX, pointToTranslate.getY());
		return p;
	}
}
