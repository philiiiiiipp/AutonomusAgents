package autonomousagents;

public class Predator extends Agent
{

	private static final int RADIUS = 5;

	public Predator(final Point p, final State s)
	{
		super(p, s);
	}

	// comment
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
			break;
		case 1:
			ate = this.state.isFree(State.east(this.currentPosition));
			this.currentPosition = State.east(this.currentPosition);
			break;
		case 2:
			ate = this.state.isFree(State.south(this.currentPosition));
			this.currentPosition = State.south(this.currentPosition);
			break;
		case 3:
			ate = this.state.isFree(State.west(this.currentPosition));
			this.currentPosition = State.west(this.currentPosition);
			break;
		default:
			break;
		}
		return !ate;
	}

	@Override
	public boolean canIGoThere(final Point p)
	{
		return true;
	}
}
