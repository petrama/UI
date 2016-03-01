package her.fer.zemris.ui.lab3;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;



class Prioritet implements Comparator<Rule>{

	@Override
	public int compare(Rule o1, Rule o2) {
		return Integer.compare(o1.getPriority(), o2.getPriority());
	}
	
}

public class Rule {
	private Set<AttrValues> left;
	private Set<AttrValues> right;
	private int priority;
	public static Prioritet PRIORITY_COMPARATOR=new Prioritet();
	
	public Rule(Set<AttrValues> left, Set<AttrValues> right, int priority) {
		super();
		this.left = left;
		this.right = right;
		this.priority = priority;
	}

	public Set<AttrValues> getLeft() {
		return left;
	}

	public void setLeft(Set<AttrValues> left) {
		this.left = left;
	}

	public Set<AttrValues> getRight() {
		return right;
	}

	public void setRight(Set<AttrValues> right) {
		this.right = right;
	}
	
	
	
	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return priority+") "+left+"----------->"+right;
	}
	
	
}
