package autonomousagents;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.util.GameField;

public class Environment
{
	List<Agent> agents = new ArrayList<Agent>();

	public State getState()
	{
		return new State(this.agents.get(0).currentPosition,
				this.agents.get(1).currentPosition);
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
		int newY = pointToTranslate.getY() == GameField.YMIN ? GameField.YMAX
				: pointToTranslate.getY() - 1;
		return new Point(pointToTranslate.getX(), newY);
	}

	public static Point south(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == GameField.YMAX ? GameField.YMIN
				: pointToTranslate.getY() + 1;
		return new Point(pointToTranslate.getX(), newY);
	}

	public static Point east(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == GameField.XMAX ? GameField.XMIN
				: pointToTranslate.getX() + 1;
		return new Point(newX, pointToTranslate.getY());
	}

	public static Point west(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == GameField.XMIN ? GameField.XMAX
				: pointToTranslate.getX() - 1;
		return new Point(newX, pointToTranslate.getY());
	}
}
