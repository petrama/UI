package hr.fer.zemris.ui.lab2.prover;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Prover {
	
	private Prover() {
		// TODO Auto-generated constructor stub
	}
	
	
	public static boolean rezolucijaOpovrgavanjem(Klauzula zakljucak,List<Klauzula> premise){
		//List<Klauzula> sve=new ArrayList<Klauzula>(premise);
		
//		for (Izraz iz : zakljucak.getIzrazi()) {//negacija zakljucka
//			iz.setValue(!iz.isValue());
//			
//			sve.add(new Klauzula(iz));
//			
//		}
//		return dokazi(sve);
	
		List<Klauzula> suportSet=new ArrayList<Klauzula>();
		for (Izraz iz : zakljucak.getIzrazi()) {//negacija zakljucka
		iz.setValue(!iz.isValue());
			
			suportSet.add(new Klauzula(iz));
		}
			int i=-1;
			pojednostavi(premise);
			Integer last=0;
			do{
				
			
			boolean kombiniranoUIteraciji=false;	
			++i;
			Klauzula radna=suportSet.get(i);
		
			for(int j=i-1;j>=0;--j){
			
				System.out.println(j);
				
				
				Klauzula druga=suportSet.get(j);
				Klauzula kombinirana=radna.dajTrecu(druga);
				if(kombinirana!=null){
					if(!premise.contains(kombinirana)){
						if(kombinirana.getIzrazi().isEmpty()){
							//nasli smo null, uspjeli smo kombinirati i dobiti nil
							return true;
						}else{
							
							suportSet.add(kombinirana);
							
						}
			}
				}}
			for(Klauzula druga:premise){
				Klauzula kombinirana=radna.dajTrecu(druga);
				if(kombinirana!=null){
					if(!premise.contains(kombinirana)){
						if(kombinirana.getIzrazi().isEmpty()){
							//nasli smo null, uspjeli smo kombinirati i dobiti nil
							return true;
						}else{
							
							suportSet.add(kombinirana);
							kombiniranoUIteraciji=true;
							
						}
					}
				}
			}
			if(kombiniranoUIteraciji){
				//pojednostavi(suportSet);
			pojednostavi2(suportSet, last);
			}
		}while(i<suportSet.size()-1);
	return false;
	}
		
	
	
	public static boolean dokazi(List<Klauzula> sve){
		for(int i=0;i<sve.size();i++)sve.get(i).setIndex(i);//postavljam redne brojeve
		
			//List<Klauzula> kopija=new ArrayList<Klauzula>(sve);
			pr:
				do{
					//System.out.println("Sve klauzule nakon do: "+sve);
					pojednostavi(sve);
					//System.err.println("Sve klauzule nakon po: "+sve);
				for(int i=sve.size()-1;i>=0;i--){
				Klauzula radna=sve.get(i);
				for(int j=radna.getIndex()-1;j>=0;j--){
					Klauzula druga=sve.get(j);
					Klauzula kombinirana=radna.dajTrecu(druga);
					//System.out.println("Kombiniram: "+radna+"  i  :"+druga+" a rezultat je "+kombinirana);
					if(kombinirana!=null){
						if(!sve.contains(kombinirana)){
							if(kombinirana.getIzrazi().isEmpty()){
								//nasli smo null, uspjeli smo kombinirati i dobiti nil
								return true;
							}else{
								kombinirana.setIndex(sve.size()-1);
								radna.setIndex(j);
								sve.add(kombinirana);
								continue pr;
							}
						}
					}
				}
			}
				break;
				}while(true);
			
	
		return false;
	}
	
	public static void pojednostavi(List<Klauzula> sve){
			for (int i=0;i<sve.size()-1;i++ ){
			Klauzula prva=sve.get(i);
			for (int j=i+1;j<sve.size();j++) {
				Klauzula druga=sve.get(j);
				if( prva.jePodskup(druga)){
					sve.remove(j);
				
				}
			}
		}
		
	
	}
	
	public static void pojednostavi2(List<Klauzula> sve,Integer last){
		for (int i=last+1;i<sve.size()-1;i++ ){
			Klauzula prva=sve.get(i);
			for (int j=0;j<sve.size();j++) {
				if(i==j)continue;
				Klauzula druga=sve.get(j);
				if( prva.jePodskup(druga)){
					sve.remove(j);
				
				}
			}
			last=sve.size()-1;
	}
	}
	
}
