package hr.fer.zemris.ui.lab2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.ui.lab2.prover.Izraz;
import hr.fer.zemris.ui.lab2.prover.Klauzula;
import hr.fer.zemris.ui.lab2.prover.Prover;

public class Picard {

	private Polje trenutno;
	private List<Polje> posjecena;
	private List<Polje> sigurna;
	private List<Polje> nesigurna;
	private List<Polje> zabranjena;
	private Polje nadjeniTransporter = null;
	private Matrica matrica;
	private List<Klauzula> premise;

	Comparator<Polje> comparator = new Comparator<Polje>() {

		@Override
		public int compare(Polje o1, Polje o2) {
			return Integer.compare((o1.getI() + 1) * 10 + o1.getJ(),
					(o2.getI() + 1) * 10 + o2.getJ());
		}
	};

	public Picard(Matrica matrica) {
		this.matrica = matrica;
		posjecena = new ArrayList<Polje>();
		sigurna = new ArrayList<Polje>();
		nesigurna = new ArrayList<Polje>();
		trenutno = matrica.getElementAt(0, 0);
		premise = new ArrayList<Klauzula>();
		zabranjena = new ArrayList<Polje>();
		for (int i = 0; i < this.matrica.getN(); i++) {
			for (int j = 0; j < this.matrica.getN(); j++) {
				dodajPremise(matrica.getElementAt(i, j));
			}

		}
		provjeriSudbinu();

	}

	public boolean provjeriSudbinu() {
		if (trenutno.getW().getValue()) {
			System.out
					.println("Picard je skoncao u raljama Whumpusa na polju: "
							+ trenutno);
			return true;
		}
		if (trenutno.getP().getValue()) {
			System.out.println("Picard je upao u jamu na polju: " + trenutno);
			return true;
		}
		if (trenutno.getT().getValue()) {
			System.out.println("Picard je uspio pronaci teleporter na polju: "
					+ trenutno);
			return true;
		}
		return false;
	}

	public void spasiSe() {
		do {
			if (provjeriSudbinu())
				return;// provjeravamo je li s trenutnim poljem stvar gotova

			System.out.println("Trenutno: " + trenutno);
			System.out.println("************");
			System.out.println("Sigurna: " + sigurna);
			System.out.println("Nesigurna: " + nesigurna);
			System.out.println("Posjecena: " + posjecena);
			System.out.println("Zabranjena: " + zabranjena);
			// System.out.println(trenutno);
			prosiriZnanje();
			sigurna.sort(comparator);
			nesigurna.sort(comparator);
			posjecena.add(trenutno);
			if (nadjeniTransporter != null) {
				trenutno = nadjeniTransporter;
				continue;
			}
			if (!sigurna.isEmpty()) {

				trenutno = sigurna.get(0);
				sigurna.remove(0);

			} else {
				if (!nesigurna.isEmpty()) {
					trenutno = nesigurna.get(0);
					nesigurna.remove(0);
				} else {
					// nakon prosirenja, prazna je lista i sigurnih i nesigurnih
					// stanja

					System.out
							.println("Sva preostala polja vode u sigurnu smrt, Picard ceka Enterprise!");
					System.exit(0);
				}
			}

		} while (true);
	}

	private void prosiriZnanje() {

		// s obzirom da stojimo na polju a živi smo i još nismo gotovi
		// smijemo u premise dodati sljedeæe jer sad znamo kakvo je stanje s
		// pitom, whumpusom
		// i teleporterom
		dodajUPremise(new Klauzula(trenutno.getS()));
		dodajUPremise(new Klauzula(trenutno.getB()));
		dodajUPremise(new Klauzula(trenutno.getG()));
		dodajUPremise(new Klauzula(trenutno.getW()));
		dodajUPremise(new Klauzula(trenutno.getP()));
		dodajUPremise(new Klauzula(trenutno.getT()));

//		for (Polje susjed : trenutno.getSusjedi()) {
//			dodajPremise(susjed);
//
//		}
		// pitmo se je li neka od susjednih celija celija s transporterom
		// ali samo ako ova trenutna sjaji
		if (trenutno.getG().getValue()) {
			
			for (Polje susjed : trenutno.getSusjedi()) {
				if (posjecena.contains(susjed))
					continue;
				// tvrdimo: na ovom susjedu nalazi se transporter
				Klauzula upitanZakljucak = new Klauzula(new Izraz(susjed.getT(), true));
				boolean zakljucakValjan = Prover.rezolucijaOpovrgavanjem(
						upitanZakljucak, premise);
				if (zakljucakValjan) {
					
					nadjeniTransporter = susjed;
					return;
				}

			}
		}

		for (Polje susjed : trenutno.getSusjedi()) {
			if (posjecena.contains(susjed)|| zabranjena.contains(susjed))
				continue;

			boolean zakljucakValjanNePit = false;
			if (!trenutno.getB().getValue()) {
				zakljucakValjanNePit = true;
			} else {
				Klauzula upitanZakljucakNePit = new Klauzula(new Izraz(
						susjed.getP(), false));
				zakljucakValjanNePit = Prover.rezolucijaOpovrgavanjem(
						upitanZakljucakNePit, premise);
			}
			if (zakljucakValjanNePit)
				dodajUPremise(new Klauzula(new Izraz(susjed.getP(), false)));

			boolean zakljucakValjanNeWhumpus = false;
			if (!trenutno.getS().getValue()) {
				zakljucakValjanNeWhumpus = true;
			} else {
				Klauzula upitanZakljucakNeWhumpus = new Klauzula(new Izraz(
						susjed.getW(), false));
				zakljucakValjanNeWhumpus = Prover.rezolucijaOpovrgavanjem(
						upitanZakljucakNeWhumpus, premise);
			}
			if (zakljucakValjanNeWhumpus)
				dodajUPremise(new Klauzula(new Izraz(susjed.getW(), false)));

			if (zakljucakValjanNePit && zakljucakValjanNeWhumpus) {
				if (!sigurna.contains(susjed)) {
					sigurna.add(susjed);
					nesigurna.remove(susjed);
					System.out.println("Polje "+susjed+" je sigurno!");
				}
				// uspjeli smo dokazati da na ovom polju whumpusa ni pita nema,
				// pa to polje proglasavamo
				// sigurnim poljem
			} else {
				// ne znamo ništa o polju, proglašavamo ga nesigurnim
				if (!nesigurna.contains(susjed))
					nesigurna.add(susjed);
			}

		}

		
		List<Polje> kopija = new ArrayList<Polje>(nesigurna);
	
		for (Polje polje : kopija) {

			Klauzula upitanZakljucakWhumpus = new Klauzula(new Izraz(
					polje.getW(), true));
			boolean zakljucakWaljanWhumpus = Prover.rezolucijaOpovrgavanjem(
					upitanZakljucakWhumpus, premise);

			if (zakljucakWaljanWhumpus) {
				dodajUPremise(new Klauzula(new Izraz(polje.getW(), true)));
				nesigurna.remove(polje);
				if (!zabranjena.contains(polje))
					zabranjena.add(polje);
				System.out.println("Na polju "+polje+" nalazi se gospon Whumpus!");

			};

			Klauzula upitanZakljucakPit = new Klauzula(new Izraz(polje.getP(),
					true));
			boolean zakljucakValjanPit = Prover.rezolucijaOpovrgavanjem(
					upitanZakljucakPit, premise);
			if (zakljucakValjanPit) {
				dodajUPremise(new Klauzula(new Izraz(polje.getP(), true)));
				nesigurna.remove(polje);
				if (!zabranjena.contains(polje))
					zabranjena.add(polje);
				System.out.println("Na polju "+polje+"nalazi se jama!");

			}
		}
	}

	private void dodajPremise(Polje radno) {
		dodajSPremisu(radno);
		dodajBPremisu(radno);
		dodajGPremisu(radno);
		dodajWPremisu(radno);

	}

	private void dodajWPremisu(Polje radno) {
		Izraz w = new Izraz(radno.getW(), false);
		for (int i = 0; i < matrica.getN(); i++) {
			for (int j = 0; j < matrica.getN(); j++) {
				if (i == radno.getI() && j == radno.getJ())
					continue;
				Polje d = matrica.getElementAt(i, j);
				if (!d.equals(radno)) {
					Izraz drugi = new Izraz(d.getW(), false);
					Klauzula nova = new Klauzula(w, drugi);
					dodajUPremise(nova);
				}
			}
		}
	}

	private void dodajGPremisu(Polje radno) {
		List<Izraz> izrazi = new ArrayList<Izraz>();
		izrazi.add(new Izraz(radno.getG(), false));
		for (Polje susjed : radno.getSusjedi()) {
			izrazi.add(new Izraz(susjed.getT(), true));
		}
		Klauzula nova = new Klauzula(izrazi);
		dodajUPremise(nova);

	}

	private void dodajBPremisu(Polje radno) {
		List<Izraz> izrazi = new ArrayList<Izraz>();
		izrazi.add(new Izraz(radno.getB(), false));
		for (Polje susjed : radno.getSusjedi()) {
			izrazi.add(new Izraz(susjed.getP(), true));
		}
		Klauzula premisa = new Klauzula(izrazi);
		dodajUPremise(premisa);

	}

	private void dodajSPremisu(Polje radno) {
		List<Izraz> izrazi = new ArrayList<Izraz>();
		izrazi.add(new Izraz(radno.getS(), false));
		for (Polje susjed : radno.getSusjedi()) {
			izrazi.add(new Izraz(susjed.getW(), true));
		}
		Klauzula premisa = new Klauzula(izrazi);
		dodajUPremise(premisa);
	}

	private void dodajUPremise(Klauzula premisa) {
		if (!premise.contains(premisa))
			premise.add(premisa);
	}

}
