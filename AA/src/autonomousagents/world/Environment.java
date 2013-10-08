package autonomousagents.world;

import java.util.ArrayList;
import java.util.List;

import autonomousagents.agent.Agent;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.util.GameField;

/**
 * The Environment class presents a list of agents and encapsulates methods that
 * allow these agents to change their position inside the environment: move
 * north, south, east, west
 */
public class Environment
{
	private final List<Predator> predators = new ArrayList<Predator>();
	private Prey prey;

	/**
	 * the State is made up of the positions of both Predator and Prey inside
	 * the grid
	 * 
	 * @return
	 */

	public State getState()
	{
		List<Point> points = new ArrayList<Point>();
		for (Agent a : this.predators)
		{
			points.add(a.getPosition());
		}
		return new State(points, this.prey.getPosition());
	}

	/**
	 * method that adds a new agent to the list of agents
	 * 
	 * @param a
	 *            : the agent we want to add
	 */
	public void addPredator(final Predator predator)
	{
		this.predators.add(predator);
	}

	public void addPrey(final Prey prey)
	{
		this.prey = prey;
	}

	/**
	 * method that returns the list of agents found on the grid
	 * 
	 * @return List<Agent>
	 */
	public List<Agent> getAgents()
	{
		List<Agent> agents = new ArrayList<Agent>();
		agents.addAll(this.predators);
		agents.add(this.prey);
		return agents;
	}

	/**
	 * method that checks whether a state is a terminal state, i.e. the
	 * positions of the Predator and Prey are the same
	 * 
	 * @return boolean
	 */
	public boolean isEndState()
	{
		return getState().isTerminal();
	}

	// (0:0) North West
	// (XMAX:0) North East
	// (0:YMAX) South West
	// (XMAX : YMAX) South East

	/**
	 * method that adds (-1, 0) to the coordinates of the current point and
	 * returns a new point moved one position upwards (we consider the grid to
	 * be toroidal)
	 * 
	 * @param pointToTranslate
	 *            the point we want to translate upwards
	 * @return Point
	 */
	public static Point north(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == GameField.YMIN ? GameField.YMAX : pointToTranslate.getY() - 1;
		return new Point(pointToTranslate.getX(), newY);
	}

	/**
	 * method that adds (+1, 0) to the coordinates of the current point and
	 * which returns a new point moved one position downwards (we consider the
	 * grid to be toroidal)
	 * 
	 * @param pointToTranslate
	 *            the point we want to translate downwards
	 * @return
	 */
	public static Point south(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == GameField.YMAX ? GameField.YMIN : pointToTranslate.getY() + 1;
		return new Point(pointToTranslate.getX(), newY);
	}

	/**
	 * method that adds (0, +1) to the coordinates of the current point and
	 * which returns a new point moved one position to the right (we consider
	 * the grid to be toroidal)
	 * 
	 * @param pointToTranslate
	 *            the point we want to translate to the east
	 * @return
	 */
	public static Point east(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == GameField.XMAX ? GameField.XMIN : pointToTranslate.getX() + 1;
		return new Point(newX, pointToTranslate.getY());
	}

	/**
	 * method that adds (0, -1) to the coordinates of the current point and
	 * which returns a new point moved one position to the right (we consider
	 * the grid to be toroidal)
	 * 
	 * @param pointToTranslate
	 *            the point we want to translate to the west
	 * @return
	 */
	public static Point west(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == GameField.XMIN ? GameField.XMAX : pointToTranslate.getX() - 1;
		return new Point(newX, pointToTranslate.getY());
	}
}
