package hr.fer.zemris.ui.lab2;



import java.util.List;

import hr.fer.zemris.ui.lab2.prover.Izraz;

public class Polje {
	private int i;
	private int j;
	
	private Izraz s;
	private Izraz b;
	private Izraz g;
	
	private Izraz w;
	private Izraz p;
	private Izraz t;
	
	
	List<Polje> susjedi;


	public int getI() {
		return i;
	}


	public void setI(int i) {
		this.i = i;
	}


	public int getJ() {
		return j;
	}


	public void setJ(int j) {
		this.j = j;
	}


	public Izraz getS() {
		return s;
	}


	public void setS(Izraz s) {
		this.s = s;
	}


	public Izraz getB() {
		return b;
	}


	public void setB(Izraz b) {
		this.b = b;
	}


	public Izraz getG() {
		return g;
	}


	public void setG(Izraz g) {
		this.g = g;
	}


	public Izraz getW() {
		return w;
	}


	public void setW(Izraz w) {
		this.w = w;
	}


	public Izraz getP() {
		return p;
	}


	public void setP(Izraz p) {
		this.p = p;
	}


	public Izraz getT() {
		return t;
	}


	public void setT(Izraz t) {
		this.t = t;
	}


	public List<Polje> getSusjedi() {
		return susjedi;
	}


	public void setSusjedi(List<Polje> susjedi) {
		this.susjedi = susjedi;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+(i+1)+","+(j+1)+") ";
				//+getS()+" "+getB()+" "+getG()+" "+getW()+" "+getP()+" "+getT();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
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
		Polje other = (Polje) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		return true;
	}

	
}
