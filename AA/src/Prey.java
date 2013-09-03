public class Prey extends Agent
{

	public Prey(final int x, final int y)
	{
		super(x, y);
	}

	@Override
	public boolean step()
	{

		double rand = Agent.RAND.nextDouble();
		if (rand > 0.8)
		{

		}
		return false;
	}

}