package hr.fer.zemris.ui.lab2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.ui.lab2.data.Polje;
import hr.fer.zemris.ui.lab2.pomoc.Matrica;
import hr.fer.zemris.ui.lab2.prover.Izraz;
import hr.fer.zemris.ui.lab2.prover.Klauzula;
import hr.fer.zemris.ui.lab2.prover.Prover;

public class Picard {

	private Polje trenutno;
	private List<Polje> posjecena;
	private List<Polje> sigurna;
	private List<Polje> nesigurna;
	//private List<Polje> zabranjena;
	private Polje nadjeniTransporter=null;
	private Matrica matrica;
	private List<Klauzula> premise;
	
	Comparator<Polje> comparator=new Comparator<Polje>() {

		@Override
		public int compare(Polje o1, Polje o2) {
			return Integer.compare((o1.getI()+1)*10+o1.getJ(), (o2.getI()+1)*10+o2.getJ());
		}
	};
	
	public Picard(Matrica matrica) {
		this.matrica=matrica;
		posjecena=new ArrayList<Polje>();
		sigurna=new ArrayList<Polje>();
		nesigurna=new ArrayList<Polje>();
		//zabranjena=new ArrayList<Polje>();
		trenutno=matrica.getElementAt(0, 0);
		premise=new ArrayList<Klauzula>();
		provjeriSudbinu();
		
	
	}
	


	public boolean provjeriSudbinu(){
		if(trenutno.getW().isValue()){
			System.out.println("Picard je skoncao u raljama Whumpusa na polju: "+trenutno);
			return true;
		}
		if(trenutno.getP().isValue()){
			System.out.println("Picard je upao u jamu na polju: "+trenutno);
			return true;
		}
		if(trenutno.getT().isValue()){
			System.out.println("Picard je uspio pronaci teleporter na polju: "+trenutno);
			return true;
		}
		return false;
	}
	
	public void spasiSe(){
		do{
		if(provjeriSudbinu())return;//provjeravamo je li s trenutnim poljem stvar gotova
		
		sigurna.sort(comparator);
		nesigurna.sort(comparator);
		
		System.err.println("Trenutno: "+trenutno);
		System.out.println("Sigurna: "+sigurna);
		System.out.println("Nesigurna: "+nesigurna);
		System.out.println("Posjecena: "+posjecena);
		prosiriStanja();
		
		
		posjecena.add(trenutno);
		if(nadjeniTransporter!=null){
			//ako je transporter nadjen gotovi smo
			trenutno=nadjeniTransporter;
	
			continue;
		}
		if(!sigurna.isEmpty()){
			
			trenutno=sigurna.get(0);
			sigurna.remove(0);
			
		}else{
			if(!nesigurna.isEmpty()){
				trenutno=nesigurna.get(0);
				nesigurna.remove(0);
			}else{
				//nakon prosirenja, prazna je lista i sigurnih i nesigurnih stanja
				
				System.out.println("Sva preostala polja vode u sigurnu smrt, Picard ceka Enterprise!");
				System.exit(0);
			}
		}
	
		}while(true);
	}
		
	
	private void prosiriStanja() {
		
		dodajPremise(trenutno);
		for (Polje susjed : trenutno.getSusjedi()) dodajPremise(susjed);
		
		//u premise dodajemo ono što znamo o stanju u kojem se treuntno nalazimo
		dodajUPremise(new Klauzula(trenutno.getS()));
		dodajUPremise(new Klauzula(trenutno.getB()));
		dodajUPremise(new Klauzula(trenutno.getG()));
		
		//s obzirom da stojimo na polju a živi smo i još nismo gotovi
		//smijemo u premise dodati sljedeæe jer sad znamo kakvo je stanje s pitom, whumpusom
		//i teleporterom
		dodajUPremise(new Klauzula(trenutno.getW()));
		dodajUPremise(new Klauzula(trenutno.getP()));
		dodajUPremise(new Klauzula(trenutno.getT()));
		

		
		//pitmo se je li neka od susjednih celija celija s transporterom
		//ali samo ako ova trenutna sjaji
		if(trenutno.getG().isValue()){
		Klauzula upitanZakljucak=null;
		for (Polje susjed : trenutno.getSusjedi()) {
			if(posjecena.contains(susjed)) continue;
			//tvrdimo: na ovom susjedu nalazi se transporter
			upitanZakljucak=new Klauzula(new Izraz(susjed.getT(),true ));
			//System.out.println(upitanZakljucak);
			boolean zakljucakValjan=Prover.rezolucijaOpovrgavanjem(upitanZakljucak, premise);
			if(zakljucakValjan){
				//System.out.println("Na polju "+susjed+" zaista je transporter :/ ");
				nadjeniTransporter=susjed;
				return;
			}
			
		}
		//System.out.println("Ne mogu zakljuèiti da je transporter na nekom konkretnom susjedu!");
		
		}
		/*else{
			for (Polje susjed : trenutno.getSusjedi()) {
				dodajUPremise(new Klauzula(new Izraz(susjed.getT(),false)));
			}
			
			
		}*/
		//ako smo ovdje,nismo zakljucili da je transporter na nekom od susjeda
		
		for(Polje susjed:trenutno.getSusjedi()){
			if(posjecena.contains(susjed)) continue;
			//tvrdimo na ovom susjedu se nalazi pit
			Klauzula upitanZakljucakNePit=new Klauzula(new Izraz(susjed.getP(),false ));
			//tvrdimo na ovom susjedu je Whumpus
			Klauzula upitanZakljucakNeWhumpus=new Klauzula(new Izraz(susjed.getW(),false ));
			//System.out.println("Zakljucak nema whumpusa: "+upitanZakljucakNeWhumpus);
			//System.out.println("Zakljucak nema pita: "+upitanZakljucakNePit);
			boolean zakljucakValjanNePit=Prover.rezolucijaOpovrgavanjem(upitanZakljucakNePit, premise);
			boolean zakljucakValjanNeWhumpus=Prover.rezolucijaOpovrgavanjem(upitanZakljucakNeWhumpus, premise);
			if(zakljucakValjanNePit && zakljucakValjanNeWhumpus){
				if(!sigurna.contains(susjed)){
					sigurna.add(susjed);
					nesigurna.remove(susjed);
					//System.out.println("Polje "+susjed+" je sigurno dodajem znanje o tome!");
					//System.err.println("Prva premisa koju dodajem: "+upitanZakljucakNeWhumpus);
					//System.err.println("Druga premisa koju dodajem: "+upitanZakljucakNePit);
					dodajUPremise(new Klauzula(new Izraz(susjed.getP(),false )));
					dodajUPremise(new Klauzula(new Izraz(susjed.getW(),false )));
				}
				//uspjeli smo dokazati da na ovom polju whumpusa ni pita nema, pa to polje proglasavamo
				//sigurnim poljem
				
			}else{
				//ne znamo ništa o polju, proglašavamo ga nesigurnim
				if(!nesigurna.contains(susjed))nesigurna.add(susjed);
			}
		}
		
		
		
	}



	private void dodajPremise(Polje radno) {
		
				
				dodajSPremisu(radno);
				dodajNotSPremisu(radno);
				dodajBPremisu(radno);
				dodajNotBPremisu(radno);
				dodajGPremisu(radno);
				//dodajNotGPremisu(radno);
				//dodajWPremisu(radno);
				
				
			}
		
		
	



	private void dodajWPremisu(Polje radno) {
		Izraz w=new Izraz(radno.getW(), false);
		for(int i=0;i<matrica.getN();i++){
			for(int j=0;j<matrica.getN();j++){
				Polje d=matrica.getElementAt(i, j);
				if(!d.equals(radno)){
				Izraz drugi=new Izraz(d.getW(),false);
				Klauzula nova=new Klauzula(w,drugi);
				dodajUPremise(nova);
				}
			}}
			}
		
	

	private void dodajNotGPremisu(Polje radno) {
		Izraz g=new Izraz(radno.getG(), true);
		for (Polje susjed : radno.getSusjedi()) {
			Izraz nott=new Izraz(susjed.getT(), false);
			Klauzula nova=new Klauzula(g,nott);
		dodajUPremise(nova);
			}
		
	}

	private void dodajGPremisu(Polje radno) {
		List<Izraz> izrazi=new ArrayList<Izraz>();
		izrazi.add(new Izraz(radno.getG(), false));
		for (Polje susjed : radno.getSusjedi()) {
			izrazi.add(new Izraz(susjed.getT(), true) );
		}
		Klauzula nova=new Klauzula(izrazi);
		//System.out.println("dodajem G premisu za polje "+radno);
		dodajUPremise(nova);
		
	}

	private void dodajNotBPremisu(Polje radno) {
		Izraz s=new Izraz(radno.getB(), true);
		for (Polje susjed : radno.getSusjedi()) {
			Izraz notp=new Izraz(susjed.getP(), false);
		Klauzula nova=new Klauzula(s,notp);
			dodajUPremise(nova);
			}
		
	}

	private void dodajBPremisu(Polje radno) {
		List<Izraz> izrazi=new ArrayList<Izraz>();
		izrazi.add(new Izraz(radno.getB(), false));
		for (Polje susjed : radno.getSusjedi()) {
			izrazi.add(new Izraz(susjed.getP(), true) );
		}
		Klauzula premisa=new Klauzula(izrazi);
		//System.out.println("dodajem B premisu za polje "+radno);
	dodajUPremise(premisa);
		
	}

	private void dodajNotSPremisu(Polje radno) {
		Izraz s=new Izraz(radno.getS(), true);
		for (Polje susjed : radno.getSusjedi()) {
			Izraz notw=new Izraz(susjed.getW(), false);
			Klauzula nova=new Klauzula(s,notw);
			dodajUPremise(nova);
			}
		
	}

	private void dodajSPremisu(Polje radno) {
		List<Izraz> izrazi=new ArrayList<Izraz>();
		izrazi.add(new Izraz(radno.getS(), false));
		for (Polje susjed : radno.getSusjedi()) {
			izrazi.add(new Izraz(susjed.getW(), true) );
		}
		Klauzula premisa=new Klauzula(izrazi);
		//System.out.println("dodajem S premisu za polje "+radno);
		dodajUPremise(premisa);
	}
	
	private void dodajUPremise(Klauzula premisa){
		if(!premise.contains(premisa))premise.add(premisa);
	}
	
	
}
