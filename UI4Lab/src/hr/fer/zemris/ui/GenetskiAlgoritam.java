package hr.fer.zemris.ui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class GenetskiAlgoritam {
	List<Mreza> mreze;
	IspitniSkup ispitniSkup;
	int brojIteracija;
	double k;
	double p;
	Comparator<Mreza> comparator;
	Random randomizer;
	
	int brojac_0=0;
	int brojac_15=0;

	public GenetskiAlgoritam(List<Mreza> mreze, IspitniSkup ispitniSkup,
			int brojIteracija, double k, double p) {
		super();
		this.mreze = mreze;
		this.ispitniSkup = ispitniSkup;
		this.brojIteracija = brojIteracija;
		this.k = k;
		this.p = p;
		this.comparator = new Mreza.Compara();
		this.randomizer = new Random(System.currentTimeMillis());
	}

	public Mreza nauci() {
		int i = 0;
		double err = evaluiraj();
		while (err > 0) {
			if (i >= brojIteracija)
				break;
			//System.out.println(i);
			//System.out.println(err);
			krizaj();
			err = evaluiraj();
			++i;
		}
		;
		//System.out.println(brojac_0+" broj "+brojac_15);
		return mreze.get(0);
		
	}

	private void krizaj() {
		int brojMreza = mreze.size();
		List<Mreza> noveMreze = new ArrayList<Mreza>(brojMreza);
		noveMreze.add(mreze.get(0));// sacuvam najbolju
		double zbrojFitnessa = 0;
		for (int i = 0; i < brojMreza; i++)
			zbrojFitnessa += mreze.get(i).getFitness();
		for (int i = 1; i < brojMreza; i++) {
			int indeksPrvogRoditelja = odaberiRoditelja(zbrojFitnessa);
			int indeksDrugogRoditelja = odaberiRoditelja(zbrojFitnessa);
			while (indeksDrugogRoditelja == indeksPrvogRoditelja) {
				indeksDrugogRoditelja = odaberiRoditelja(zbrojFitnessa);
			}
			if(indeksPrvogRoditelja==0){
				brojac_0++;
			}else{
				brojac_15++;
			}
		//	System.out.println(indeksDrugogRoditelja+" "+indeksPrvogRoditelja);
			noveMreze.add(krizajDvijeMrezeMutiraj(
					mreze.get(indeksPrvogRoditelja),
					mreze.get(indeksDrugogRoditelja)));
		}
	
		this.mreze = noveMreze;
	}

	private Mreza krizajDvijeMrezeMutiraj(Mreza prva, Mreza druga) {
		double[] aritmSredina = new double[prva.getBrojTezina()];
		for (int i = 0; i < prva.getBrojTezina(); i++) {
			aritmSredina[i] = (prva.getTezina(i) + druga.getTezina(i)) * 0.5;
			double mutirati = randomizer.nextDouble();
			if (mutirati < p) {
				aritmSredina[i] += randomizer.nextGaussian() * k;
			}
		}
		return new Mreza(prva.getN(), aritmSredina);
	}

	private int odaberiRoditelja(double zbrojFitnessa) {
		double slucajni = randomizer.nextDouble() * zbrojFitnessa;
		double pomSuma = 0;
		for (int i = 0; i < mreze.size(); i++) {
			pomSuma += mreze.get(i).getFitness();
			if (slucajni < pomSuma)
				return i;
		}
		return -1;
	}

	private double evaluiraj() {
		for (Mreza mreza : mreze) {
			mreza.izracunajGlobalError(ispitniSkup);
		}
		mreze.sort(comparator);
		return mreze.get(0).getGreska();
	}
}
