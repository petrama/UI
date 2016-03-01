package hr.fer.zemris.ui.astar;

import hr.fer.zemris.ui.SearchAlgorithm;
import hr.fer.zemris.ui.data.Node;

import java.util.List;

public abstract class AStarSearch extends SearchAlgorithm {
	List<Node> expanded;

	public AStarSearch() {

		super();

	}

	@Override
	public void doSomething(Node n) {

		expanded = expand(n);
		for (int i = 0; i < expanded.size(); i++) {
			expanded.get(i).setEstimate(heuristic(expanded.get(i)));
			;
			;
		}

		for (Node neighbour : expanded) {
			if (this.getClosed().contains(neighbour))
				continue;
			if (this.getOpen().contains(neighbour)) {
				for (int i = 0; i < this.getOpen().size(); i++) {
					if (neighbour.equals(getOpen().get(i))
							&& neighbour.getTotalCost() < getOpen().get(i)
									.getTotalCost()) {
						this.getOpen().set(i, neighbour);
					}
				}

			} else {

				this.getOpen().add(neighbour);
			}
		}
		this.getOpen().sort(n);
	}

	public abstract int heuristic(Node n);

}
