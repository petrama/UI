package hr.fer.zemris.ui.lab2.prover;

public class Izraz {
	private String name;
	private boolean value;
	
	/**
	 * Konstruktor koji stvara novi izraz istog imena kao predani izraz,
	 * koji ima predanu vrijednost true ili false.
	 * @param postojeci Izraz cije se ime kopira
	 * @param novaVrijednost vrijednost na koju ce izraz biti postavljen
	 */
	public Izraz(Izraz postojeci,boolean novaVrijednost){
		name=postojeci.name;
		value=novaVrijednost;
	}
	
	public Izraz(String name, boolean value) {
		super();
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
	
	public boolean givesNil(Izraz other){
		if(this.name.equals(other.getName())&& this.value!=other.value)return true;
		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (value ? 1231 : 1237);
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
		Izraz other = (Izraz) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		
		return name+" : "+value;
	}
	
	

}
