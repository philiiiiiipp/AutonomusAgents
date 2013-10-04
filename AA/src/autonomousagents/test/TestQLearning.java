package autonomousagents.test;

import java.awt.Color;
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

public class TestQLearning
{
	private static final int NUMBER_OF_EPISODES = 2000;

	/**
	 * @deprecated due to our class TestCompareAll
	 */
	@Deprecated
	public static void test()
	{
		double alpha = 0.1;

		XYSeriesCollection dataset = new XYSeriesCollection();

		dataset.addSeries(generateSeries(0.5, 0.1));
		dataset.addSeries(generateSeries(0.5, 0.5));
		dataset.addSeries(generateSeries(0.5, 0.7));
		dataset.addSeries(generateSeries(0.5, 0.9));

		ApplicationFrame frame = new ApplicationFrame("Q-Learning with e-Greedy" + ", epsilon=" + Constants.EPSILON
				+ " alpha=" + alpha + " gamma=" + Constants.GAMMA);

		NumberAxis xax = new NumberAxis("Episodes");
		NumberAxis yax = new NumberAxis("Steps");
		XYSplineRenderer a = new XYSplineRenderer();
		a.setBaseShapesVisible(false);
		a.setSeriesPaint(2, Color.ORANGE);
		a.setSeriesPaint(3, Color.BLACK);
		XYPlot xyplot = new XYPlot(dataset, xax, yax, a);

		JFreeChart chart = new JFreeChart(xyplot);

		ChartPanel chartPanel = new ChartPanel(chart);
		frame.setContentPane(chartPanel);
		frame.pack();
		frame.setVisible(true);
	}

	public static XYSeries generateSeries(final double alpha, final double gamma)
	{
		return generateSeries(alpha, gamma, new EGreedyPolicy());
	}

	public static XYSeries generateSeries(final double alpha, final double gamma, final Policy predatorPolicy)
	{
		PreyRandomPolicy preyPoly = new PreyRandomPolicy();

		double averageLastProcent = 0;

		XYSeries steps = new XYSeries("Q-learning with " + predatorPolicy + " Alpha:" + alpha + " Gamma:" + gamma);
		for (int i = 0; i < Constants.NUMBER_OF_EPISODES; ++i)
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

				a.setActionValue(a.getActionValue() + alpha
						* (reward + gamma * maximisation(predatorPolicy.actionsForState(sPrime)) - a.getActionValue()));

				s = sPrime;
			} while (!s.isTerminal());

			averageLastProcent += counter;

			int episodeStep = 100;
			if (i != 0 && i % episodeStep == 0 || i + 1 == NUMBER_OF_EPISODES)
			{
				steps.add(i, averageLastProcent / episodeStep);
				averageLastProcent = 0;
			}
		}

		return steps;
	}

	/**
	 * Run Q-Learning
	 * 
	 * @param predatorPolicy
	 * @param preyPolicy
	 * @param alpha
	 * @param gamme
	 * @param episodeCount
	 * @return a list of step counts, until the predator catched the prey
	 */
	public static List<Integer> runQLearning(final Policy predatorPolicy, final Policy preyPolicy, final double alpha,
			final double gamma, final int episodeCount)
	{
		List<Integer> stepList = new ArrayList<Integer>();

		for (int i = 0; i < episodeCount; ++i)
		{
			// Initialise s
			Environment e = new Environment();
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);
			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);

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
					preyPolicy.nextProbabilisticActionForState(e.getState()).apply(prey);
				}

				State sPrime = e.getState();

				a.setActionValue(a.getActionValue() + alpha
						* (reward + gamma * maximisation(predatorPolicy.actionsForState(sPrime)) - a.getActionValue()));

				s = sPrime;
			} while (!s.isTerminal());

			stepList.add(counter);
		}

		return stepList;
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
}
