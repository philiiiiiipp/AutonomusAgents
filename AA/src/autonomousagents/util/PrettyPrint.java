package autonomousagents.util;

import java.text.DecimalFormat;
import java.util.List;

import autonomousagents.actions.Action;
import autonomousagents.policy.Policy;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class PrettyPrint
{
	public static void printTable(final Policy predatorPolicy)
	{
		for (int yPred = 0; yPred < 11; yPred++)
		{
			System.out.print(" | ");
			for (int xPred = 0; xPred < 11; xPred++)
			{
				State s = new State(new Point(xPred, yPred), new Point(5, 5));

				if (s.isTerminal())
				{
					System.out.print("# ");
					continue;
				}

				Action a = predatorPolicy.actionWithHighestValue(s);
				System.out.print(a + " ");
			}
			System.out.println(" | ");
		}
		System.out.println("---------------------------");
	}

	public static void printAction(final Policy predatorPolicy, State s)
	{

		DecimalFormat df = new DecimalFormat("#.000000");

		for (Action a : predatorPolicy.actionsForState(s))
		{
			System.out.println(a.getActionValue() + " " + a);
		}

		for (int yPred = 0; yPred < 11; yPred++)
		{
			System.out.print(" | ");
			for (int xPred = 0; xPred < 11; xPred++)
			{
				s = new State(new Point(xPred, yPred), new Point(5, 5));

				Action a = predatorPolicy.actionWithHighestValue(s);
				System.out.print(+xPred + ":" + yPred + " " + df.format(a.getActionValue()) + a + "\t");
			}
			System.out.println(" | ");
		}
		System.out.println("---------------------------");
	}

	public static void printEpisode(final List<Pair<State, Action>> episode)
	{
		for (Pair<State, Action> pair : episode)
		{
			System.out.print(pair.getLeft() + " " + pair.getRight() + " ");
		}
		System.out.println();

	}
}
