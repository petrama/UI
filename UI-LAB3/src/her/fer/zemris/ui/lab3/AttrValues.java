package her.fer.zemris.ui.lab3;

import java.util.HashSet;
import java.util.Set;

public class AttrValues {
	private String name;
	private Set<String> values;

	public AttrValues(String name, Set<String> values) {
		super();
		this.name = name;
		this.values = values;
	}

	public String getName() {
		return name;
	}

	public Set<String> getValues() {
		return values;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
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
		AttrValues other = (AttrValues) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return name+"="+values;
	}
	
	public static AttrValues parseAttrValues(String s){
		String[] leftRight=s.trim().split("=");
		String attribute=leftRight[0].trim();
		//System.out.println(attribute);
		Set<String> vals=new HashSet<String>();
		for (String string : leftRight[1].trim().split("\\|")) {
			vals.add(string.trim());
				
		}
		return new AttrValues(attribute, vals);
	}

}
