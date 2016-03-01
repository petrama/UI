package hr.fer.zemris.ui;

import java.util.Comparator;
import java.util.Random;

public class Mreza {
	static class Compara implements Comparator<Mreza> {
		@Override
		public int compare(Mreza o1, Mreza o2) {
			return -Double.compare(o1.getFitness(), o2.getFitness());
		}
	}

	private double[] sveTezine;
	private int n;
	private double greska = Double.MAX_VALUE;
	private PrijenosnaFunkcija sigmoidalna = new SigmoidalnaFunkcija();

	public Mreza(int n, double[] sveTezine) {
		this.n = n;
		this.sveTezine = sveTezine;
	}

	public double getFitness() {
		return 1.0 / (getGreska() + 1);
	}

	public int getBrojTezina() {
		return sveTezine.length;
	}

	public int getN() {
		return n;
	}

	public double getTezina(int index) {
		return sveTezine[index];
	}

	public void setSveTezine(double[] sveTezine) {
		if (this.sveTezine.length != sveTezine.length)
			System.err.println("Razlicit broj tezina!");
		this.sveTezine = sveTezine;
	}

	public double izracunajGlobalError(IspitniSkup ispitni) {
		double greska = 0;
		for (int i = 0, n = ispitni.getN(); i < n; i++) {
			double res = izracunajVrijednostIzlaza((ispitni.getX(i)));
			double pravi = ispitni.getFx(i);
			greska += (res - pravi) * (res - pravi);
		}
		this.greska = greska/ispitni.getN();
		return greska;
	}

	public double izracunajVrijednostIzlaza(Double x) {
		double zbroj = 0;
		double[] meðuIzlazi = new double[n];
		for (int i = 0; i < n; i++) {
			meðuIzlazi[i] = sigmoidalna.compute(sveTezine[i * 2] * x
					+ sveTezine[i * 2 + 1]);
		}
		int offset = 2 * n;
		int i;
		for (i = 0; i < n; i++) {
			zbroj += meðuIzlazi[i] * sveTezine[offset + i];
		}
		zbroj += sveTezine[offset + i];
		return zbroj;
	}

	public double getGreska() {
		return greska;
	}

	public static Mreza generirajRandom(int n) {
		int ukupanBrojTezina = 2 * n + n + 1;
		double[] sveTezine = new double[ukupanBrojTezina];
		Random randomizer = new Random(System.currentTimeMillis());
		sveTezine = randomizer.doubles(ukupanBrojTezina, -1, 1).toArray();
		return new Mreza(n, sveTezine);
	}

	
}
