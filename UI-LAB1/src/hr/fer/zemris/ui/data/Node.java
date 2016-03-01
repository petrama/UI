package hr.fer.zemris.ui.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Node implements Comparator<Node> {
	private int x;
	private int y;
	private int totalCost;
	private int estimate;

	private boolean alreadyTeleportedOnce = false;

	private Node parent;

	public boolean isAlreadyTeleportedOnce() {
		return alreadyTeleportedOnce;
	}

	public void setAlreadyTeleportedOnce(boolean alreadyTeleportedOnce) {
		this.alreadyTeleportedOnce = alreadyTeleportedOnce;
	}

	public Node(Node parent) {
		this.parent = parent;
	}

	public Node getParent() {
		return parent;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTotalCost() {
		return totalCost;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	public int getEstimate() {
		return estimate;
	}

	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + (x + 1) + "," + (y + 1) + ") total cost= " + totalCost
				+ " estimate: " + this.getEstimate();
	}

	public boolean inArea(int x1, int y1, int x2, int y2) {
		if (this.x < x1 || this.x > x2 || this.y < y1 || this.y > y2)
			return false;
		return true;
	}

	@Override
	public int compare(Node o1, Node o2) {
		return Integer.compare(o1.getTotalCost() + o1.getEstimate(),
				o2.getTotalCost() + o2.getEstimate());
	}

	public List<Node> getAncestors() {
		List<Node> anc = new ArrayList<Node>();
		Node t = this;
		do {
			anc.add(t);
			t = t.getParent();
		} while (t != null);
		return anc;
	}

}
