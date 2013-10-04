package autonomousagents.util;

public class Pair<T, L>
{
	private T left;
	private L right;

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

	public void setLeft(final T left)
	{
		this.left = left;
	}

	public void setRight(final L right)
	{
		this.right = right;
	}

	@Override
	public boolean equals(final Object object)
	{
		if (object instanceof Pair<?, ?>)
			return object.hashCode() == this.hashCode();

		return false;
	}

	@Override
	public int hashCode()
	{
		String result = this.left.hashCode() + "" + this.right.toString().hashCode();
		return result.hashCode();
	}
}
