package autonomousagents.test;

import java.awt.Color;
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
	private static final int NUMBER_OF_EPISODES = 10000;

	// private static final double alpha = 0.1d;

	public static void test()
	{

		double alpha = 0.1;

		XYSeriesCollection dataset = new XYSeriesCollection();

		// dataset.addSeries(averageSteps);
		// dataset.addSeries(averageLastSteps);
		dataset.addSeries(generateSeries(0.5, 0.1));
		dataset.addSeries(generateSeries(0.5, 0.5));
		dataset.addSeries(generateSeries(0.5, 0.7));
		dataset.addSeries(generateSeries(0.5, 0.9));
		// dataset.addSeries(generateSeries(0.5, 0.1));

		ApplicationFrame frame = new ApplicationFrame("Q-Learning with e-Greedy" + ", epsilon=" + Constants.EPSILON
				+ " alpha=" + alpha + " gamma=" + Constants.GAMMA);

		NumberAxis xax = new NumberAxis("Steps");
		NumberAxis yax = new NumberAxis(" Episodes");
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

		double average = 0;
		double averageLastProcent = 0;

		XYSeries steps = new XYSeries("Q-learning with " + predatorPolicy + " Alpha:" + alpha + " Gamma:" + gamma);
		// XYSeries averageSteps = new XYSeries("Avg. all steps");
		// XYSeries averageLastSteps = new XYSeries("Avg. over last 100 steps");
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

				a.setActionValue(a.getActionValue() + alpha
						* (reward + gamma * maximisation(predatorPolicy.actionsForState(sPrime)) - a.getActionValue()));

				s = sPrime;
			} while (!s.isTerminal());

			average += counter;
			averageLastProcent += counter;
			int episodeStep = 100;
			if (i % episodeStep == 0 || i + 1 == NUMBER_OF_EPISODES)
			{
				steps.add(i, averageLastProcent / episodeStep);
				averageLastProcent = 0;

				// averageSteps.add(i, average / (i + 1));
			}

			// steps.add(i, average / (i + 1));
			// steps.add(i, counter);
			// steps.add(i, counter);

		}

		return steps;
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
