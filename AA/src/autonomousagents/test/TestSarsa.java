package autonomousagents.test;

import java.util.ArrayList;
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
import autonomousagents.policy.predator.EGreedyPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Constants;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestSarsa
{
	private static final int NUMBER_OF_EPISODES = 2000;

	public static void test()
	{
		XYSeriesCollection dataset = new XYSeriesCollection();
		XYSeries s = generateDataSeries(0.1, 0.1);
		dataset.addSeries(s);

		s = generateDataSeries(0.1, 0.2);
		dataset.addSeries(s);

		s = generateDataSeries(0.1, 0.4);
		dataset.addSeries(s);

		ApplicationFrame frame = new ApplicationFrame("Sarsa");

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

	public static XYSeries generateDataSeries(final double alpha, final double gamma)
	{
		double average = 0;
		double averageLastProcent = 0;
		Policy predatorPolicy = new EGreedyPolicy();
		// Policy predatorPolicy = new SoftmaxPolicy();
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();

		XYSeries steps = new XYSeries("Sarsa with Alpha:" + alpha + " Gamma:" + gamma);
		for (int i = 0; i < Constants.NUMBER_OF_EPISODES; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPoly);

			e.addAgent(predator);
			e.addAgent(prey);

			State s = e.getState();
			Action a = predatorPolicy.nextProbabilisticActionForState(s);

			// Repeat for each step of the episode
			int counter = 0;
			do
			{
				counter++;
				a.apply(predator);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				if (!e.getState().isTerminal())
				{
					preyPoly.nextProbabilisticActionForState(e.getState()).apply(prey);
				}

				State sPrime = e.getState();

				Action aPrime = predatorPolicy.nextProbabilisticActionForState(sPrime);

				a.setActionValue(a.getActionValue() + alpha
						* (reward + gamma * aPrime.getActionValue() - a.getActionValue()));

				s = sPrime;
				a = aPrime;
			} while (!s.isTerminal());

			average += counter;
			averageLastProcent += counter;
			int averageStep = 100;

			if (i != 0 && i % averageStep == 0 || i + 1 == NUMBER_OF_EPISODES)
			{
				steps.add(i, averageLastProcent / averageStep);
				averageLastProcent = 0;
			}
		}

		return steps;
	}

	public static List<Integer> runSarsa(final Policy predatorPolicy, final Policy preyPolicy, final double alpha,
			final double gamma, final int episodeCount)
	{
		List<Integer> stepsCount = new ArrayList<Integer>();

		for (int i = 0; i < episodeCount; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);

			e.addAgent(predator);
			e.addAgent(prey);

			State s = e.getState();
			Action a = predatorPolicy.nextProbabilisticActionForState(s);

			// Repeat for each step of the episode
			int counter = 0;
			do
			{
				counter++;
				a.apply(predator);

				// Reward from this action
				double reward = (e.getState().isTerminal() ? Constants.REWARD : 0);

				if (!e.getState().isTerminal())
				{
					preyPolicy.nextProbabilisticActionForState(e.getState()).apply(prey);
				}

				State sPrime = e.getState();

				Action aPrime = predatorPolicy.nextProbabilisticActionForState(sPrime);

				a.setActionValue(a.getActionValue() + alpha
						* (reward + gamma * aPrime.getActionValue() - a.getActionValue()));

				s = sPrime;
				a = aPrime;
			} while (!s.isTerminal());

			stepsCount.add(counter);
		}

		return stepsCount;
	}
}
