package autonomousagents.util;

import java.util.HashMap;

import autonomousagents.actions.Action;
import autonomousagents.world.State;

public class QTable
{
	private final HashMap<Trupel<State, Action, Action>, Double> _qMap = new HashMap<Trupel<State, Action, Action>, Double>();

	public Double getQValue(final Trupel<State, Action, Action> trupel)
	{
		if (!this._qMap.containsKey(trupel))
			this._qMap.put(trupel, 1.0);

		return this._qMap.get(trupel);
	}

	public void setQValue(final Trupel<State, Action, Action> trupel, final Double qValue)
	{
		this._qMap.put(trupel, qValue);
	}
}
