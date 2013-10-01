package autonomousagents.util;

public class Pair<T, L>
{
	private final T left;
	private final L right;

	public Pair(final T left, final L right)
	{
		this.left = left;
		this.right = right;
	}

	public T getLeft()
	{
		return this.left;
	}

	public L getRight()
	{
		return this.right;
	}
}
