public class Predator extends Agent
{

	private static final int RADIUS = 5;

	public Predator(final Point p)
	{
		super(p);
	}

	@Override
	public boolean step()
	{
		int rand = Agent.RAND.nextInt(RADIUS);
		switch (rand)
		{
		case 0:
			// boolean b = this.state.isFree(this.point.g - 1, this.coord_y);
			// this.coord_x -= 1;
			// return b;

		default:
			break;
		}
		return false;
	}
}
