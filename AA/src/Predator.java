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
		boolean ate = false;

		switch (rand)
		{
		case 0:
			ate = this.state.isFree(State.north(this.currentPosition));
			this.currentPosition = State.north(this.currentPosition);
		case 1:
			ate = this.state.isFree(State.east(this.currentPosition));
			this.currentPosition = State.east(this.currentPosition);
		case 2:
			ate = this.state.isFree(State.south(this.currentPosition));
			this.currentPosition = State.south(this.currentPosition);
		case 3:
			ate = this.state.isFree(State.north(this.currentPosition));
			this.currentPosition = State.north(this.currentPosition);
		}
		return ate;
	}
}
