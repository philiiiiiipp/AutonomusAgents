package autonomousagents.world;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.agent.Agent;
import autonomousagents.util.GameField;

/* The Environment class presents a list of agents and encapsulates methods
 * that allow these agents to change their position inside the environment:
 * move north, south, east, west */
public class Environment
{
	List<Agent> agents = new ArrayList<Agent>();

	// the State is made up of the positions of both Predator and Prey inside
	// the grid
	public State getState()
	{
		return new State(this.agents.get(0).getPosition(), this.agents.get(1)
				.getPosition());
	}

	// method that adds a new agent to the list of agents
	public void addAgent(final Agent a)
	{
		this.agents.add(a);
	}

	// method that returns the list of agents found on the grid
	public List<Agent> getAgents()
	{
		return this.agents;
	}

	// method that checks whether a state is a terminal state, i.e. the
	// positions of the Predator and Prey are the same
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
	// method that adds (-1, 0) to the coordinates of the
	// current point and which returns a new point moved one position
	// upwards (we consider the grid to be toroidal)
	public static Point north(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == GameField.YMIN ? GameField.YMAX
				: pointToTranslate.getY() - 1;
		return new Point(pointToTranslate.getX(), newY);
	}

	// method that adds (+1, 0) to the coordinates of the
	// current point and which returns a new point moved one position
	// downwards (we consider the grid to be toroidal)
	public static Point south(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == GameField.YMAX ? GameField.YMIN
				: pointToTranslate.getY() + 1;
		return new Point(pointToTranslate.getX(), newY);
	}

	// method that adds (0, +1) to the coordinates of the
	// current point and which returns a new point moved one position
	// to the right (we consider the grid to be toroidal)
	public static Point east(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == GameField.XMAX ? GameField.XMIN
				: pointToTranslate.getX() + 1;
		return new Point(newX, pointToTranslate.getY());
	}

	// method that adds (0, -1) to the coordinates of the
	// current point and which returns a new point moved one position
	// to the right (we consider the grid to be toroidal)
	public static Point west(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == GameField.XMIN ? GameField.XMAX
				: pointToTranslate.getX() - 1;
		return new Point(newX, pointToTranslate.getY());
	}
}
