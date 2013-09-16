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

	public float transitioinProbability(final Point currentPrey,
			final Point nextPrey, final Point currentPredator)
	{
		if (currentPrey.equals(nextPrey))
		{
			return 8 / 10;
		} else if (nextPrey.equals(currentPredator))
		{
			return 0;
		} else
		{
			State trialState = new State();
			Agent predator = new Predator(currentPredator, trialState);
			Agent prey = new Prey(currentPrey, trialState);
			trialState.addAgent(predator);
			trialState.addAgent(prey);
			return (2 / 10) * (1 / this.state.getNeighbors(this).size());
		}
	}
}