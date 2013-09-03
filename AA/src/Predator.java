public class Predator extends Agent
{

	private static final int RADIUS = 5;

	public Predator(final Point p, final State s)
	{
		super(p, s);
	}

	@Override
	public boolean step()
	{
		int rand = Agent.RAND.nextInt(RADIUS);
		switch (rand)
		{
		case 0:
			boolean b = this.state.isFree(new Point(0, 0));
			// this.coord_x -= 1;
			// return b;

		default:
			break;
		}
		return false;
	}
}
