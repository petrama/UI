package hr.fer.zemris.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Glavni {
	public static void main(String[] args) throws IOException {
		int n = 6;
		int brojIteracija=10000;
		double p=n * 1.0 / (n*3+1);
		double k=1;
		System.out.println("Broj perceptrona središnjeg sloja n="+n);
		System.out.println("Vjerojatnost mutacije jedne težine pri krizanju: "+p);
		System.out.println("Broj K, devijacija normalne distribucije vjerojatnosti vrijednosti kojom se mutira bit k="+k);
		System.out.println("Broj iteracija="+brojIteracija);
		IspitniSkup isp = IspitniSkup
				.ucitajIzFilea(new File("training-set.txt").toPath());
		List<Mreza> mreze = new ArrayList<Mreza>();
		for (int i = 0; i < 50; i++)
			mreze.add(Mreza.generirajRandom(n));
		GenetskiAlgoritam gen = new GenetskiAlgoritam(mreze, isp, brojIteracija,k, p);
		Mreza m=gen.nauci();
		IspitniSkup testni=IspitniSkup.ucitajIzFilea(new File("test-set.txt").toPath());
		prikaziRezultat(testni, m);
	
		unos(m);
	}
	
	private static void unos(Mreza m) throws IOException {
		System.out.println("Unosite vrijednosti\n");
		BufferedReader bf=new BufferedReader(new InputStreamReader(System.in));
		while(true){
		System.out.println("Unesie neki X=");
		String l=bf.readLine();
	
		try{
		double x=Double.parseDouble(l.trim());
		System.out.println("X= "+x+"  F(X)= "+m.izracunajVrijednostIzlaza(x));
		}catch(NumberFormatException ne){
			System.out.println("Pozdrav!");
			break;
		}
		}
	}

	public static void prikaziRezultat(IspitniSkup testni,Mreza m){
		System.out.println("Greska: "+m.izracunajGlobalError(testni));
	 for(int i=0;i<testni.getN();i++){
		double izl= m.izracunajVrijednostIzlaza(testni.getX(i));
		System.out.print(i+") ");
		 ispisi(testni.getX(i),testni.getFx(i),izl,testni.getFx(i)-izl);
		
	
	 }
		
	}

	private static void ispisi(double x, double fx, double izl, double d) {
		System.out.println("x="+x+" ocekivani f(x)="+fx+" dobiveni f(x)="+izl+"err= "+d);
		
	}
}
