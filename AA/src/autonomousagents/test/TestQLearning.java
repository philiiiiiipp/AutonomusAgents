package autonomousagents.test;

import java.text.DecimalFormat;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.SoftmaxPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Constants;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestQLearning
{
	private static final int NUMBER_OF_EPISODES = 10000;
	private static final double alpha = 0.1d;

	public static void test()
	{

		double average = 0;
		double averageLastProcent = 0;
		// Policy predatorPolicy = new EGreedyPolicy();
		Policy predatorPolicy = new SoftmaxPolicy();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();

		XYSeries steps = new XYSeries("steps");
		XYSeries averageSteps = new XYSeries("Avg. all steps");
		XYSeries averageLastSteps = new XYSeries("Avg. over last 100 steps");
		for (int i = 0; i < NUMBER_OF_EPISODES; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPoly);

			e.addAgent(predator);
			e.addAgent(prey);

			State s = e.getState();

			// Repeat for each step of the episode
			int counter = 0;
			do
			{
				counter++;
				Action a = predatorPolicy.nextProbabilisticActionForState(s);
				a.apply(predator);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				if (!e.getState().isTerminal())
				{
					preyPoly.nextProbabilisticActionForState(e.getState()).apply(prey);
				}

				State sPrime = e.getState();

				a.setActionValue(a.getActionValue()
						+ alpha
						* (reward + Constants.GAMMA * maximisation(predatorPolicy.actionsForState(sPrime)) - a
								.getActionValue()));

				s = sPrime;
			} while (!s.isTerminal());

			average += counter;
			averageLastProcent += counter;
			if (i % 100 == 0 || i + 1 == NUMBER_OF_EPISODES)
			{
				averageLastSteps.add(i, averageLastProcent / 100);
				averageLastProcent = 0;

				averageSteps.add(i, average / (i + 1));
			}

			steps.add(i, counter);

		}
		XYSeriesCollection dataset = new XYSeriesCollection();

		dataset.addSeries(averageSteps);
		dataset.addSeries(averageLastSteps);
		dataset.addSeries(steps);

		ApplicationFrame frame = new ApplicationFrame("Q-Learning with " + predatorPolicy + ", epsilon="
				+ Constants.EPSILON + " alpha=" + alpha + " gamma=" + Constants.GAMMA + " Temp:"
				+ SoftmaxPolicy.TEMPERATURE);

		NumberAxis xax = new NumberAxis("Steps");
		NumberAxis yax = new NumberAxis("Episodes");
		XYSplineRenderer a = new XYSplineRenderer();
		a.setBaseShapesVisible(false);
		XYPlot xyplot = new XYPlot(dataset, xax, yax, a);

		JFreeChart chart = new JFreeChart(xyplot);

		ChartPanel chartPanel = new ChartPanel(chart);
		frame.setContentPane(chartPanel);
		frame.pack();
		frame.setVisible(true);
	}

	private static double maximisation(final List<Action> actionList)
	{
		double highestActionValue = 0;
		for (Action a : actionList)
		{
			if (a.getActionValue() > highestActionValue)
				highestActionValue = a.getActionValue();
		}

		return highestActionValue;
	}

	private static void printAction(final Policy predatorPolicy)
	{

		DecimalFormat df = new DecimalFormat("#.000000");

		State s = new State(new Point(5, 4), new Point(5, 5));

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

				// if (s.isTerminal())
				// {
				// System.out.print(df.format(0.0) + ".\t");
				// continue;
				// }
				Action a = predatorPolicy.actionWithHighestValue(s);
				System.out.print(+xPred + ":" + yPred + " " + df.format(a.getActionValue()) + a + "\t");
			}
			System.out.println(" | ");
		}
		System.out.println("---------------------------");
	}

	private static void printTable(final Policy predatorPolicy)
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
}
