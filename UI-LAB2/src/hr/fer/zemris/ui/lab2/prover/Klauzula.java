package hr.fer.zemris.ui.lab2.prover;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Klauzula {
	
	private List<Izraz> izrazi;
	private int index;

	public Klauzula (Izraz ... izrazi){
		this.izrazi=new ArrayList<Izraz>();
		for(Izraz i:izrazi){
			this.izrazi.add(i);
		}
	}
	public Klauzula(List<Izraz> izrazi) {
		super();
		this.izrazi = izrazi;
	}
	
	public boolean jeJednostavna(){
		if(this.getIzrazi().size()==1)return true;
		return false;
	}
	public boolean givesNil(Klauzula other){
	if(this.jeJednostavna() && other.jeJednostavna() && this.getIzrazi().get(0).givesNil(other.getIzrazi().get(0))){
		return true;
	}
	return false;
	}
	 public List<Izraz> getIzrazi() {
		return izrazi;
	}
	 
	public Klauzula dajTrecu(Klauzula druga){
		
//		for(Izraz prvi:this.getIzrazi()){
//			for(Izraz drugi:druga.getIzrazi()){
//				if(prvi.givesNil(drugi)){
//					List<Izraz> novo=new ArrayList<Izraz>();
//					novo.addAll(izrazi);
//					novo.addAll(druga.getIzrazi());
//					novo.remove(prvi);
//					novo.remove(drugi);
//					return new Klauzula(novo);
//				}
//			}
//		}
//		
		
		Klauzula p=this;
		Klauzula o=druga;
		if(this.jeJednostavna()){
		}else{
			if(druga.jeJednostavna()){
			o=this;
			p=druga;
			}else{
				return null;
			}
		}
		List<Izraz> kopija=new ArrayList<Izraz>(o.getIzrazi());
			Izraz jednostavna=p.getIzrazi().get(0);
			for (Izraz izraz :o.getIzrazi()) {
				if(jednostavna.givesNil(izraz)){
					kopija.remove(izraz);
					Klauzula nova=new Klauzula(kopija);
					return nova;
				}
			}
		
		return null;
	}

	public boolean jePodskup(Klauzula druga){
		if(this.equals(druga))return true;
		if(druga.getIzrazi().size()>this.getIzrazi().size()&&druga.getIzrazi().containsAll(this.getIzrazi())) return true;
		return false;
	}
	@Override
	public String toString() {
		
		return izrazi.toString()+"i: "+index;
	}
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((izrazi == null) ? 0 : izrazi.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Klauzula other = (Klauzula) obj;
		if (izrazi == null) {
			if (other.izrazi != null)
				return false;
		} else{
			Set<Izraz> set1 = new HashSet<Izraz>();
			set1.addAll(izrazi);
			Set<Izraz> set2 = new HashSet<Izraz>();
			set2.addAll(other.izrazi);
			return set1.equals(set2);
		}
	return true;
	}
	
}
