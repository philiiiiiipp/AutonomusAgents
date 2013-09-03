public class Prey extends Agent
{

	public Prey(final Point p)
	{
		super(p);
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

}