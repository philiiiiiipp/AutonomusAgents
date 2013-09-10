package autonomousagents;

import java.util.ArrayList;

public class Prey extends Agent
{

	public Prey(final Point p, final State s)
	{
		super(p, s);
	}

	@Override
	public boolean step()
	{

		double rand = Agent.RAND.nextDouble();
		if (rand > 0.8)
		{
			ArrayList<Point> neighbors = this.state.getNeighbors(this);
			int numberOfPossibleDestinations = neighbors.size();
			int nb = Agent.RAND.nextInt(numberOfPossibleDestinations);
			this.currentPosition = neighbors.get(nb);
		}

		// this.currentPosition.pPrint();

		return false;
	}

	@Override
	public boolean canIGoThere(final Point possibleDestination)
	{
		return this.state.isFree(possibleDestination);
	}
}