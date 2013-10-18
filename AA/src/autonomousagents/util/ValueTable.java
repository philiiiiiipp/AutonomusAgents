package autonomousagents.util;

import java.util.HashMap;

import autonomousagents.world.State;

public class ValueTable
{
	private final HashMap<State, Double> _valueTable = new HashMap<State, Double>();

	public Double getValue(final State state)
	{
		if (!this._valueTable.containsKey(state))
			this._valueTable.put(state, 1.0);

		return this._valueTable.get(state);
	}

	public void setValue(final State state, final Double value)
	{
		this._valueTable.put(state, value);
	}
}
