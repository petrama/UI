package hr.fer.zemris.ui;

import hr.fer.zemris.ui.data.DataMatrix;
import hr.fer.zemris.ui.data.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class SearchAlgorithm {
	private List<Node> open;
	private List<Node> closed;

	public static DataMatrix DATA;

	public SearchAlgorithm() {
		open = new ArrayList<Node>();
		closed = new ArrayList<Node>();

	}

	public Node search() {

		return search(DATA.getStartNode(), DATA.getEndNode());
	}

	public Node search(Node start, Node end) {
		getClosed().clear();
		getOpen().clear();
		open.add(start);
		while (!open.isEmpty()) {

			Node n = open.get(0);
			// System.out.println(n+" "+n.getEstimate());

			open.remove(n);

			closed.add(n);
			if (end.equals(n))
				return n;

			doSomething(n);

		}
		return null;
	}

	public void showResult(Node result) {
		System.out.println("Minimal cost: " + result.getTotalCost());
		System.out.println("Opened nodes: " + (this.closed.size()));
		System.out.println("Path:");
		List<Node> is = result.getAncestors();
		for (int i = is.size() - 1; i > 0; --i) {
			System.out.println(is.get(i) + " -> ");
		}
		System.out.println(is.get(0));
	}

	public abstract void doSomething(Node n);

	public List<Node> getOpen() {
		return open;
	}

	public void setOpen(List<Node> open) {
		this.open = open;
	}

	public List<Node> getClosed() {
		return closed;
	}

	public void setClosed(List<Node> closed) {
		this.closed = closed;
	}

	public List<Node> expand(Node n) {
		int DATADimension = DATA.getN();
		List<Node> expanded;
		if (n.getY() < DATADimension / 2) {
			expanded = expand(n, 0, 0, DATADimension - 1, DATADimension / 2 - 1);
		} else {
			expanded = expand(n, 0, DATADimension / 2, DATADimension - 1,
					DATADimension - 1);
		}
		if (DATA.getValue(n) == 0) {
			expanded.addAll(getExtraNeighbours(n));
		}
		return expanded;
	}

	public List<Node> expand(Node n, int x1, int y1, int x2, int y2) {

		if (!n.inArea(x1, y1, x2, y2))
			throw new IllegalArgumentException(
					"This point is already not in search area: " + n);
		List<Node> neigh = new ArrayList<Node>(4);
		if (n.getX() - 1 >= x1) {
			Node top = new Node(n);
			top.setX(n.getX() - 1);
			top.setY(n.getY());
			int newcost = getCost(n, top);
			top.setTotalCost(n.getTotalCost() + newcost);
			top.setAlreadyTeleportedOnce(n.isAlreadyTeleportedOnce());
			neigh.add(top);
		}
		if (n.getX() + 1 <= x2) {
			Node right = new Node(n);
			right.setX(n.getX() + 1);
			right.setY(n.getY());
			int newcost = getCost(n, right);
			right.setTotalCost(n.getTotalCost() + newcost);
			right.setAlreadyTeleportedOnce(n.isAlreadyTeleportedOnce());
			neigh.add(right);
		}
		if (n.getY() - 1 >= y1) {
			Node left = new Node(n);
			left.setX(n.getX());
			left.setY(n.getY() - 1);
			int newcost = getCost(n, left);
			left.setTotalCost(n.getTotalCost() + newcost);
			left.setAlreadyTeleportedOnce(n.isAlreadyTeleportedOnce());
			neigh.add(left);
		}
		if (n.getY() + 1 <= y2) {
			Node bottom = new Node(n);
			bottom.setX(n.getX());
			bottom.setY(n.getY() + 1);
			int newcost = getCost(n, bottom);
			bottom.setTotalCost(n.getTotalCost() + newcost);
			bottom.setAlreadyTeleportedOnce(n.isAlreadyTeleportedOnce());
			neigh.add(bottom);
		}

		return neigh;

	}

	protected List<Node> getExtraNeighbours(Node n) {
		List<Node> nl = new ArrayList<Node>();

		if (n.equals(DATA.getSsNode())) {
			Node temp = new Node(n);
			temp.setX(DATA.getSlNode().getX());
			temp.setY(DATA.getSlNode().getY());
			int newcost = manhattanDistance(n, temp) * 3;
			temp.setTotalCost(n.getTotalCost() + newcost);
			temp.setAlreadyTeleportedOnce(n.isAlreadyTeleportedOnce());
			nl.add(temp);
		} else if (n.equals(DATA.getSlNode())) {
			Node temp = new Node(n);
			temp.setX(DATA.getSsNode().getX());
			temp.setY(DATA.getSsNode().getY());
			int newcost = manhattanDistance(n, temp) * 3;
			temp.setTotalCost(n.getTotalCost() + newcost);
			temp.setAlreadyTeleportedOnce(n.isAlreadyTeleportedOnce());
			nl.add(temp);
		} else {

			if (!n.isAlreadyTeleportedOnce())
				nl.addAll(getTeleportNeighbours(n));
		}
		return nl;
	}

	protected List<Node> getTeleportNeighbours(Node n) {
		List<Node> nl = new ArrayList<Node>();
		boolean exists = false;
		for (Map.Entry<String, List<Node>> entry : DATA.getSpecial().entrySet()) {
			if (entry.getValue().contains(n)) {
				for (Node c : entry.getValue()) {
					if (!c.equals(n)) {
						Node temp = new Node(n);
						int newcost = (manhattanDistance(n, c));
						temp.setTotalCost(n.getTotalCost() + newcost);
						temp.setX(c.getX());
						temp.setY(c.getY());
						temp.setAlreadyTeleportedOnce(n
								.isAlreadyTeleportedOnce());
						nl.add(temp);
					} else {
						exists = true;
					}
				}
			}
		}
		return exists ? nl : new ArrayList<Node>();
	}

	/**
	 * Ne uzima u obzir da jedna od toèaka možda nije u prostoru potrage
	 * 
	 * @param other
	 * @return
	 */
	public boolean isNeighbour(Node first, Node other) {
		int deltax = Math.abs(other.getX() - first.getX());
		if (deltax == 1)
			return true;
		int deltay = Math.abs(other.getY() - first.getY());
		if (deltay == 1)
			return true;
		return false;
	}

	/**
	 * Cijena prelaska ove u drugu celiju jednaka je apsolutnoj razlici visina.
	 * Visine sadržane u matrici koja èuva podatke.
	 * 
	 * @param other
	 * @return
	 */
	protected int getCost(Node first, Node other) {
		if (!isNeighbour(first, other)) {
			throw new IllegalArgumentException("Nodes are not neigbours! :"
					+ this + "\t" + other);
		} else {
			return Math.abs(DATA.getValue(other) - DATA.getValue(first));
		}
	}

	public static int manhattanDistance(Node first, Node second) {
		return Math.abs(second.getY() - first.getY())
				+ Math.abs(second.getX() - first.getX());

	}

}
