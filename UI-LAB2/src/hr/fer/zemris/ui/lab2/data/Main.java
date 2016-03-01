package hr.fer.zemris.ui.lab2.data;

import java.io.IOException;

import hr.fer.zemris.ui.lab2.Picard;
import hr.fer.zemris.ui.lab2.pomoc.Matrica;

public class Main {
	public static void main(String[] args) throws IOException {
		Matrica mat=new Matrica("mapa1.txt");
		Picard picard=new Picard(mat);
		long start = System.currentTimeMillis();;
	   picard.spasiSe();
	    long end = System.currentTimeMillis();;

	    System.err.println((end - start) + " ms");
	}

}
