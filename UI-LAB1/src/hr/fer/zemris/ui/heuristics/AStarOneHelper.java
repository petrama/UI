package hr.fer.zemris.ui.heuristics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.ui.StateSpaceSearch;
import hr.fer.zemris.ui.data.Node;

public class AStarOneHelper extends StateSpaceSearch {

	@Override
	protected List<Node> getTeleportNeighbours(Node n) {
		List<Node> nl = new ArrayList<Node>();
		boolean exists = false;
		for (Map.Entry<String, List<Node>> entry : DATA.getSpecial().entrySet()) {

			for (Node c : entry.getValue()) {
				if (!c.equals(n)) {
					Node temp = new Node(n);
					int newcost = (manhattanDistance(n, c));
					temp.setTotalCost(n.getTotalCost() + newcost);
					temp.setX(c.getX());
					temp.setY(c.getY());
					temp.setAlreadyTeleportedOnce(true);// !!!
					nl.add(temp);
				} else {
					exists = true;
				}
			}

		}

		return exists ? nl : new ArrayList<Node>();
	}

	@Override
	protected int getCost(Node first, Node other) {
		if (!isNeighbour(first, other)) {
			throw new IllegalArgumentException("Nodes are not neigbours! :"
					+ this + "\t" + other);
		} else {
			return 1;
		}
	}

}
