package hr.fer.zemris.ui;

import hr.fer.zemris.ui.astar.AStarOne;
import hr.fer.zemris.ui.data.DataMatrix;
import hr.fer.zemris.ui.data.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static List<Node> open = new ArrayList<Node>();

	public static void main(String[] args) throws IOException {
		DataMatrix data = new DataMatrix("dat3.txt");

		Node testEndNode = new Node(null);
		testEndNode.setX(11);
		testEndNode.setY(5);

		SearchAlgorithm.DATA = data;

		SearchAlgorithm alg = new AStarOne();

		Node r = alg.search();
		alg.showResult(r);

	}

}
