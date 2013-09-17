package autonomousagents;

import java.util.List;

public class State
{
	private final List<Point> points;
	public static final int XMAX = 10;
	public static final int YMAX = 10;
	public static final int XMIN = 0;
	public static final int YMIN = 0;

	public State(final List<Point> points)
	{
		this.points = points;
	}

	// public State(final State otherState)
	// {
	// this.agents = new ArrayList<Agent>();
	// for (Agent a : otherState.agents)
	// {
	// if (a instanceof Prey)
	// {
	// this.agents.add(new Prey(a.getPosition(), this));
	// } else
	// {
	// this.agents.add(new Predator(a.getPosition(), this));
	// }
	// }
	// }

	// public void addAgent(final Agent a)
	// {
	// this.agents.add(a);
	// }
	//
	// public ArrayList<Agent> getAgents()
	// {
	// return this.agents;
	// }
	//
	// public boolean isFree(final Point p)
	// {
	// for (Agent a : this.agents)
	// {
	// if (a.isPresent(p))
	// {
	// return false;
	// }
	// }
	// return true;
	// }

	// public ArrayList<Point> getNeighbors(final Agent a)
	// {
	// ArrayList<Point> neighbors = new ArrayList<Point>();
	//
	// if (a.canIGoThere(north(a.currentPosition)))
	// {
	// neighbors.add(north(a.currentPosition));
	// }
	//
	// if (a.canIGoThere(south(a.currentPosition)))
	// {
	// neighbors.add(south(a.currentPosition));
	// }
	//
	// if (a.canIGoThere(west(a.currentPosition)))
	// {
	// neighbors.add(west(a.currentPosition));
	// }
	//
	// if (a.canIGoThere(east(a.currentPosition)))
	// {
	// neighbors.add(east(a.currentPosition));
	// }
	// return neighbors;
	// }

	// public void pprint()
	// {
	// for (int i = XMIN; i <= XMAX; i++)
	// {
	// String acc = "";
	// for (int j = YMIN; j <= YMAX; j++)
	// {
	// Point p = new Point(i, j);
	// boolean agentWasPresent = false;
	// for (Agent a : this.agents)
	// {
	// if (a.isPresent(p))
	// {
	// agentWasPresent = true;
	// if (a instanceof Prey)
	// {
	// acc = acc.concat("-");
	// } else
	// {
	// acc = acc.concat("+");
	// }
	// break;
	// }
	// }
	// if (!agentWasPresent)
	// {
	// acc = acc.concat("o");
	// }
	// acc = acc.concat(" ");
	// }
	// System.out.println(acc);
	// }
	// }

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
		for (Point p : this.points)
		{
			result ^= p.getX();
			result = result << power;
			result ^= p.getY();
			result <<= power;
		}
		result >>= power;

		return result;
	}
	// /**
	// * Calculates, which moves the prey can possibly do, excluding the STAY
	// * move.
	// *
	// * @param preyPosition
	// * @param predPosition
	// * @return
	// */
	// public static List<Point> preyCanMoveTo(final Point preyPosition,
	// final Point predPosition)
	// {
	// List<Point> resultList = new ArrayList<Point>();
	//
	// Point testPoint = State.north(preyPosition);
	// if (!testPoint.equals(predPosition))
	// resultList.add(testPoint);
	//
	// testPoint = State.east(preyPosition);
	// if (!testPoint.equals(predPosition))
	// resultList.add(testPoint);
	//
	// testPoint = State.south(preyPosition);
	// if (!testPoint.equals(predPosition))
	// resultList.add(testPoint);
	//
	// testPoint = State.west(preyPosition);
	// if (!testPoint.equals(predPosition))
	// resultList.add(testPoint);
	//
	// return resultList;
	// }
}
