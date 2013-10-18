package autonomousagents.util;

public class Trupel<L, M, R>
{
	private final L left;
	private final R right;
	private final M middle;

	public Trupel(final L left, final M middle, final R right)
	{
		this.left = left;
		this.middle = middle;
		this.right = right;
	}

	public L getLeft()
	{
		return this.left;
	}

	public R getRight()
	{
		return this.right;
	}

	public M getMiddle()
	{
		return this.middle;
	}

	@Override
	public boolean equals(final Object object)
	{
		if (object instanceof Trupel<?, ?, ?>)
			return object.hashCode() == this.hashCode();

		return false;
	}

	@Override
	public int hashCode()
	{
		String result = "" + this.left.hashCode() + this.middle.toString().hashCode()
				+ this.right.toString().hashCode();
		return result.hashCode();
	}
}
