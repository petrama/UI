package hr.fer.zemris.ui.data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataMatrix {
	private Integer[][] data;
	private int n;
	private Map<String, List<Node>> special = new HashMap<String, List<Node>>();
	private Node startNode;
	private Node endNode;
	private Node ssNode;
	private Node slNode;

	public DataMatrix(String filePath) throws IOException {
		List<String> fileLines = Files
				.readAllLines(new File(filePath).toPath());
		this.n = fileLines.size();
		this.data = new Integer[this.n][this.n];
		for (int i = 0; i < this.n; i++) {
			String[] temp = fileLines.get(i).split(" ");
			for (int j = 0; j < this.n; j++) {
				try {
					this.data[i][j] = Integer.parseInt(temp[j]);
				} catch (NumberFormatException ne) {
					this.data[i][j] = 0;
					addToSpecial(i, j, temp[j]); // add special point P,S,C or T
				}

			}

		}

	}

	private void addToSpecial(int i, int j, String s) {
		Node n = new Node(null);
		n.setX(i);
		n.setY(j);
		if (s.equals("P")) {
			n.setTotalCost(0);
			this.startNode = n;
			return;
		}
		if (s.equals("C")) {
			this.endNode = n;
			return;
		}
		if (s.equals("SS")) {
			this.ssNode = n;
			return;
		}
		if (s.equals("SL")) {
			this.slNode = n;
			return;
		}
		if (special.containsKey(s)) {
			special.get(s).add(n);
		} else {
			List<Node> nl = new ArrayList<Node>();
			nl.add(n);
			special.put(s, nl);
		}

	}

	public int getValue(int i, int j) {
		if (i < 0 || i >= n || j < 0 || j >= n)
			throw new IllegalArgumentException("Index out of, in getValue");
		return data[i][j];
	}

	public int getValue(Node c) {
		return getValue(c.getX(), c.getY());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sb.append(data[i][j] + "  ");
			}
			sb.append("\n");

		}
		return sb.toString();
	}

	public void ispisiVrijednosti() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(getValue(i, j) + "  ");
			}
			System.out.print("\n");

		}
	}

	public Integer[][] getData() {
		return data;
	}

	public void setData(Integer[][] data) {
		this.data = data;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Map<String, List<Node>> getSpecial() {
		return special;
	}

	public void setSpecial(Map<String, List<Node>> special) {
		this.special = special;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}

	public Node getSsNode() {
		return ssNode;
	}

	public void setSsNode(Node ssNode) {
		this.ssNode = ssNode;
	}

	public Node getSlNode() {
		return slNode;
	}

	public void setSlNode(Node slNode) {
		this.slNode = slNode;
	}

}
