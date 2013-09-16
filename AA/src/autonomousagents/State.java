package autonomousagents;

import java.util.ArrayList;
import java.util.List;

public class State
{

	private final ArrayList<Agent> agents;
	public static final int XMAX = 10;
	public static final int YMAX = 10;
	public static final int XMIN = 0;
	public static final int YMIN = 0;

	public State()
	{
		this.agents = new ArrayList<Agent>();
	}

	public State(final State otherState)
	{
		this.agents = new ArrayList<Agent>();
		for (Agent a : otherState.agents)
		{
			if (a instanceof Prey)
			{
				this.agents.add(new Prey(a.getPoint(), this));
			} else
			{
				this.agents.add(new Predator(a.getPoint(), this));
			}
		}
	}

	public void addAgent(final Agent a)
	{
		this.agents.add(a);
	}

	public ArrayList<Agent> getAgents()
	{
		return this.agents;
	}

	public boolean isFree(final Point p)
	{
		for (Agent a : this.agents)
		{
			if (a.isPresent(p))
			{
				return false;
			}
		}
		return true;
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
		int newY = pointToTranslate.getY() == YMIN ? YMAX : pointToTranslate
				.getY() - 1;
		Point p = new Point(pointToTranslate.getX(), newY);
		return p;
	}

	public static Point south(final Point pointToTranslate)
	{
		int newY = pointToTranslate.getY() == YMAX ? YMIN : pointToTranslate
				.getY() + 1;
		Point p = new Point(pointToTranslate.getX(), newY);
		return p;
	}

	public static Point east(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == XMAX ? XMIN : pointToTranslate
				.getX() + 1;
		Point p = new Point(newX, pointToTranslate.getY());
		return p;
	}

	public static Point west(final Point pointToTranslate)
	{
		int newX = pointToTranslate.getX() == XMIN ? XMAX : pointToTranslate
				.getX() - 1;
		Point p = new Point(newX, pointToTranslate.getY());
		return p;
	}

	public ArrayList<Point> getNeighbors(final Agent a)
	{
		ArrayList<Point> neighbors = new ArrayList<Point>();

		if (a.canIGoThere(north(a.currentPosition)))
		{
			neighbors.add(north(a.currentPosition));
		}

		if (a.canIGoThere(south(a.currentPosition)))
		{
			neighbors.add(south(a.currentPosition));
		}

		if (a.canIGoThere(west(a.currentPosition)))
		{
			neighbors.add(west(a.currentPosition));
		}

		if (a.canIGoThere(east(a.currentPosition)))
		{
			neighbors.add(east(a.currentPosition));
		}
		return neighbors;
	}

	public void pprint()
	{
		for (int i = XMIN; i <= XMAX; i++)
		{
			String acc = "";
			for (int j = YMIN; j <= YMAX; j++)
			{
				Point p = new Point(i, j);
				boolean agentWasPresent = false;
				for (Agent a : this.agents)
				{
					if (a.isPresent(p))
					{
						agentWasPresent = true;
						if (a instanceof Prey)
						{
							acc = acc.concat("-");
						} else
						{
							acc = acc.concat("+");
						}
						break;
					}
				}
				if (!agentWasPresent)
				{
					acc = acc.concat("o");
				}
				acc = acc.concat(" ");
			}
			System.out.println(acc);
		}
	}

	/**
	 * Calculates, which moves the prey can possibly do, excluding the STAY
	 * move.
	 * 
	 * @param preyPosition
	 * @param predPosition
	 * @return
	 */
	public static List<Point> preyCanMoveTo(final Point preyPosition,
			final Point predPosition)
	{
		List<Point> resultList = new ArrayList<Point>();

		Point testPoint = State.north(preyPosition);
		if (!testPoint.equals(predPosition))
			resultList.add(testPoint);

		testPoint = State.east(preyPosition);
		if (!testPoint.equals(predPosition))
			resultList.add(testPoint);

		testPoint = State.south(preyPosition);
		if (!testPoint.equals(predPosition))
			resultList.add(testPoint);

		testPoint = State.west(preyPosition);
		if (!testPoint.equals(predPosition))
			resultList.add(testPoint);

		return resultList;
	}
}
