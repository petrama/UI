package hr.fer.zemris.ui;

import hr.fer.zemris.ui.data.Node;

public class StateSpaceSearch extends SearchAlgorithm {

	public StateSpaceSearch() {
		super();
	}

	@Override
	public void doSomething(Node n) {

		for (Node c : expand(n)) {
			if (this.getOpen().contains(c)) {
				for (int i = 0; i < this.getOpen().size(); i++) {
					if (c.equals(getOpen().get(i))
							&& c.getTotalCost() < getOpen().get(i)
									.getTotalCost()) {
						this.getOpen().set(i, c);
					}
				}

			}
			if (!this.getClosed().contains(c) && !this.getOpen().contains(c)) {

				if (this.getOpen().isEmpty()
						|| c.compare(c,
								this.getOpen().get(this.getOpen().size() - 1)) > 0) {
					this.getOpen().add(c);
				} else {
					this.getOpen().add(c);
				}
			}
		}
		this.getOpen().sort(n);
	}

}
