package hr.fer.zemris.ui.astar;

import hr.fer.zemris.ui.SearchAlgorithm;
import hr.fer.zemris.ui.data.Node;

public class AStarManhattan extends AStarSearch {

	public AStarManhattan() {
		super();

		SearchAlgorithm.DATA.getStartNode().setEstimate(
				heuristic(SearchAlgorithm.DATA.getStartNode()));
	}

	@Override
	public int heuristic(Node n) {
		return manhattanDistance(n, SearchAlgorithm.DATA.getEndNode());

	}

}
