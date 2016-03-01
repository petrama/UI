package hr.fer.zemris.ui.lab2;

import java.io.IOException;

import hr.fer.zemris.ui.lab2.Picard;


public class Main {
	public static void main(String[] args) throws IOException {
		Matrica mat=new Matrica("wumpus-map-3.txt");
		Picard picard=new Picard(mat);
		long start = System.currentTimeMillis();
		picard.spasiSe();
	    long end = System.currentTimeMillis();;

	    System.out.println((end - start) + " ms");
	}

}
