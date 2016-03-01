package hr.fer.zemris.ui.astar;

import hr.fer.zemris.ui.SearchAlgorithm;
import hr.fer.zemris.ui.data.Node;
import hr.fer.zemris.ui.heuristics.AStarOneHelper;

public class AStarOne extends AStarSearch {
	public AStarOneHelper helper;
	int[][] heuristic;

	public AStarOne() {
		super();
		heuristic = new int[SearchAlgorithm.DATA.getN()][SearchAlgorithm.DATA
				.getN()];
		this.helper = new AStarOneHelper();

		SearchAlgorithm.DATA.getStartNode().setEstimate(
				heuristic(SearchAlgorithm.DATA.getStartNode()));

	}

	@Override
	public int heuristic(Node n) {

		if (heuristic[n.getX()][n.getY()] == 0) {
			heuristic[n.getX()][n.getY()] = helper.search(n,
					SearchAlgorithm.DATA.getEndNode()).getTotalCost();
		}

		return heuristic[n.getX()][n.getY()];

	}

}
