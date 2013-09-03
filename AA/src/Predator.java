public class Predator extends Agent
{

	public Predator(final int x, final int y)
	{
		super(x, y);
	}

	@Override
	public boolean step()
	{
		int rand = Agent.RAND.nextInt(5);
		return false;
	}

}
