package autonomousagents.test.a3;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

import scpsolver.problems.LPWizard;
import autonomousagents.actions.Action;
import autonomousagents.agent.Predator;
import autonomousagents.agent.Prey;
import autonomousagents.policy.Policy;
import autonomousagents.policy.predator.SoftPolicy;
import autonomousagents.policy.prey.PreyRandomPolicy;
import autonomousagents.util.Constants;
import autonomousagents.util.JFreeChartHelper;
import autonomousagents.util.PrettyPrint;
import autonomousagents.util.QTable;
import autonomousagents.util.Trupel;
import autonomousagents.util.ValueTable;
import autonomousagents.world.Environment;
import autonomousagents.world.Point;
import autonomousagents.world.State;

public class TestMiniMax
{

	private static final int EPISODE_COUNT = 250000;

	/**
	 * Plot the difference between SARSA, Q-Learning and On-/Off-Policy Monte
	 * Carlo
	 */
	public static void test()
	{
		XYSeriesCollection dataset = new XYSeriesCollection();

		List<Integer> stepList = runMiniMax(new SoftPolicy(), new PreyRandomPolicy(), 2000);
		dataset.addSeries(JFreeChartHelper.createAverageDataseries(stepList, "Predator", 100));

		ApplicationFrame frame = new ApplicationFrame("");

		NumberAxis xax = new NumberAxis("Episodes");
		NumberAxis yax = new NumberAxis("Steps");
		XYSplineRenderer a = new XYSplineRenderer();
		a.setBaseShapesVisible(false);
		a.setSeriesPaint(3, Color.RED);
		XYPlot xyplot = new XYPlot(dataset, xax, yax, a);

		JFreeChart chart = new JFreeChart(xyplot);

		ChartPanel chartPanel = new ChartPanel(chart);
		frame.setContentPane(chartPanel);
		frame.pack();
		frame.setVisible(true);

	}

	private static List<Integer> runMiniMax(final Policy predatorPolicy, final Policy preyPolicy, final int episodeCount)
	{
		List<Integer> resultList = new ArrayList<Integer>();

		QTable predatorQ = new QTable();
		ValueTable predatorV = new ValueTable();

		QTable preyQ = new QTable();
		ValueTable preyV = new ValueTable();

		double gamma = 0.9;
		LPWizard lpw = new LPWizard();

		double alpha = 1;
		double decay = 0.999;
		for (int step = 0; step < episodeCount; step++)
		{

			if (step % 1000 == 0)
				System.out.println(step);

			// Initialise s
			Environment e = new Environment();
			Prey prey = new Prey(new Point(5, 5), e, preyPolicy);
			Predator predator = new Predator(new Point(0, 0), e, predatorPolicy);

			e.addPrey(prey);
			e.addPredator(predator);

			int stepCount = 0;
			State s = null;
			do
			{
				stepCount++;
				s = e.getState();
				Action predatorAction = predatorPolicy.nextProbabilisticActionForState(s);
				predatorAction.apply(predator);

				if (e.isEndState())
				{
					predatorV.setValue(e.getState(), Constants.REWARD);
					break;
				}

				Action preyAction = preyPolicy.nextProbabilisticActionForState(s);
				preyAction.apply(prey);

				Trupel<State, Action, Action> saa = new Trupel<State, Action, Action>(s, predatorAction, preyAction);
				predatorQ.setQValue(saa,
						(1 - alpha) * predatorQ.getQValue(saa) + alpha * (gamma * predatorV.getValue(e.getState())));

				List<Action> predActionList = predatorPolicy.actionsForState(e.getState());

				double min = Double.MAX_VALUE;
				int bestPos = -1;
				for (Action oPrime : preyPolicy.actionsForState(e.getState()))
				{
					double sum = -1;
					int smallBest = -1;
					for (int i = 0; i < predActionList.size(); ++i)
					{
						double qValue = predatorQ.getQValue(new Trupel<State, Action, Action>(s, predActionList.get(i),
								oPrime));

						if (qValue > sum)
						{
							sum = qValue;
							smallBest = i;
						}
					}

					if (min > sum)
					{
						bestPos = smallBest;
						min = sum;
					}
				}

				for (int i = 0; i < predActionList.size(); ++i)
				{
					if (i == bestPos)
					{
						predActionList.get(i).setProbability(1);
					} else
					{
						predActionList.get(i).setProbability(0);
					}
				}

				predatorV.setValue(s, min);

				alpha *= decay;
			} while (!e.getState().isTerminal());

			resultList.add(stepCount);
		}
		int f = 0;
		for (Integer i : resultList)
		{
			f += i;
		}

		PrettyPrint.printTableForProbabilities(predatorPolicy);

		System.out.println(f / episodeCount);
		return resultList;
	}
}

/*
 * 
 * // lpw.setMinProblem(true); int cNum = 0; lpw.addConstraint("c" + cNum++, 1,
 * "=").plus("a0").plus("a1").plus("a2").plus("a3").plus("a4");
 * 
 * for (int i = 0; i < predActionList.size(); i++) { lpw.addConstraint("c" +
 * cNum++, 0, "<=").plus("a" + i); }
 * 
 * double objective = Double.MAX_VALUE; double[] prob = new double[5];
 * 
 * for (Action oPrime : preyPolicy.actionsForState(e.getState())) { for (int i =
 * 0; i < predActionList.size(); ++i) { lpw.plus("a" + i,
 * predatorQ.getQValue(new Trupel<State, Action, Action>(s, predActionList
 * .get(i), oPrime))); }
 * 
 * LPSolution solution = lpw.solve();
 * 
 * if (solution.getObjectiveValue() < objective) { objective =
 * solution.getObjectiveValue(); for (int x = 0; x < 5; x++) { prob[x] =
 * solution.getDouble("a" + x); }
 * 
 * System.out.println(objective); System.out.println(solution); } }
 * 
 * for (int i = 0; i < 5; i++) { predActionList.get(i).setProbability(prob[i]);
 * }
 * 
 * predatorV.setValue(s, objective);
 * 
 * alpha *= decay;
 */
