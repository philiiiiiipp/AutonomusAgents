package autonomousagents;

import autonomousagents.policy.Policy;

public class Predator extends Agent
{

	// private static final int RADIUS = 5;

	public Predator(final Point p, final Environment environment,
			final Policy policy)
	{
		super(p, environment, policy);
	}

	// comment
	@Override
	public boolean step()
	{
		this.policy.nextProbabalisticActionForState(this.environment.getState()).apply(this);
		return this.environment.isEndState();
		// switch (rand)
		// {
		// case 0:
		// eat = !this.state.isFree(State.north(this.currentPosition));
		// this.currentPosition = State.north(this.currentPosition);
		// break;
		// case 1:
		// eat = !this.state.isFree(State.east(this.currentPosition));
		// this.currentPosition = State.east(this.currentPosition);
		// break;
		// case 2:
		// eat = !this.state.isFree(State.south(this.currentPosition));
		// this.currentPosition = State.south(this.currentPosition);
		// break;
		// case 3:
		// eat = !this.state.isFree(State.west(this.currentPosition));
		// this.currentPosition = State.west(this.currentPosition);
		// break;
		// default:
		// eat = false;
		// break;
		// }
		// System.out.println("Returning: " + eat);
		// System.out.println("Casing: " + rand);
		// return eat;
	}

	@Override
	public boolean canIGoThere(final Point p)
	{
		return true;
	}
}
