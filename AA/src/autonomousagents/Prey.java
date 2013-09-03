package autonomousagents;
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
			// generate neighbors
			// how many are free?
			//

		}
		return false;
	}

	public boolean canIGoThere(final Point possibleDestination)
	{
		return this.state.isFree(possibleDestination);
	}
}