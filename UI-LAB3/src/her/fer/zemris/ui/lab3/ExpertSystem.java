package her.fer.zemris.ui.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class ExpertSystem {
	private DataModel data;
	private List<AttrValues> ram;
	public Stack<String> stack;
	public Set<String> impossible;

	Map<String, Set<Rule>> conflictedSets;

	public ExpertSystem(DataModel d) {
		this.data = d;
		ram = new ArrayList<AttrValues>();
		stack = new Stack<>();
		conflictedSets = new HashMap<String, Set<Rule>>();
		impossible=new HashSet<String>();
	}

	public void prove(String goalAttribute) {
		
		if (data.getVariables().get(goalAttribute) == null) {
			System.out
					.println("Given atribute does not exist! Expected one of "
							+ data.getVariables().keySet());
			return;
		}

		stack.push(goalAttribute);

		while (!stack.isEmpty()) {

			String tempGoal = stack.peek();

			Set<Rule> tempConflictedSet = getConflictedSet(tempGoal);
			
			if(tempConflictedSet.isEmpty()){
				impossible.add(tempGoal);
				System.err.println("Impossible to infer "+tempGoal);
				stack.pop();
				continue;
				
			}
			

			van: for (Iterator<Rule> iterator = tempConflictedSet.iterator(); iterator
					.hasNext();) {

				ispisiStanje();
				printConflictedSet(tempConflictedSet);
				Rule r = (Rule) iterator.next();
				
				if(impossibleToInfer(r)){
					iterator.remove();
					
					continue;
				}
				tu: do {
				if (allPremisesSatisfied(r)) {
					executeRule(r);
					stack.pop();
					break van;
				} else {
				
						if (somethingConflicting(r)) {
							
							iterator.remove();
							
							continue van;

						} else {
							String newGoal = getMissingAttribute(r);
							if (stack.contains(newGoal)) {
								break van;
							}
							if (newGoal != null) {
								Set<Rule> tempSet = getConflictedSet(newGoal);
								if (tempSet.size() > 0) {
									stack.push(newGoal);
									
									break van;
								} else {
									String inf = askUserAbout(newGoal);
									Set<String> temp = new TreeSet<String>();
									temp.add(inf);
									ram.add(new AttrValues(newGoal, temp));

									ispisiStanje();
									printConflictedSet(tempConflictedSet);
								
									continue tu;
								}
							} else {
								
							}

						}
						break;
					
				
				}
				} while (true);
			}
			

		}
	}

	private boolean impossibleToInfer(Rule r) {
		for(AttrValues at:r.getLeft()){
			if(impossible.contains(at.getName()))return true;
		}
		return false;
	}

	private void printConflictedSet(Set<Rule> tempConflictedSet) {
		System.out.print("Conflicted set: [ ");
	
		for (Rule rule : tempConflictedSet) {
			System.out.print(rule.getPriority() + " ");

		}
		System.out.println("]");
		System.out.println("-----------------------");
		

	}

	private boolean somethingConflicting(Rule r) {
		for (AttrValues attrValues : r.getLeft()) {
			for (AttrValues t : ram) {
				if (attrValues.getName().equals(t.getName())
						&& !attrValues.getValues().equals(t.getValues())) {
					return true;
				}
			}
		}
		return false;
	}

	private Set<Rule> getConflictedSet(String tempGoal) {
		if (!conflictedSets.containsKey(tempGoal)) {
			conflictedSets.put(tempGoal, data.getConfictedSet(tempGoal));
		}
		return conflictedSets.get(tempGoal);
	}

	private String getMissingAttribute(Rule r) {
		for (AttrValues av : r.getLeft()) {
			boolean hasMatching = false;
			for (AttrValues rm : ram) {
				if (rm.getName().equals(av.getName()))
					hasMatching = true;
			}
			if (!hasMatching)
				return av.getName();
		}
		return null;
	}

	private void executeRule(Rule r) {
		System.err.println(r + "fires!");
		ram.addAll(r.getRight());

	}

	private boolean allPremisesSatisfied(Rule r) {
		for (AttrValues av : r.getLeft()) {
			if (!ram.contains(av))
				return false;
		}
		return true;
	}

	private void ispisiStanje() {
		System.out.println("Stack: " + stack);
		System.out.println("Memory: " + ram);
	}

	public String askUserAbout(String attribute) {

		System.out.println("Please provide " + attribute);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String ucitano = "";
		do {

			try {
				ucitano = in.readLine().trim();
			} catch (IOException io) {
				System.err.println("Problem reading line!");
				System.exit(1);
				;
			}
			if (!data.getVariables().get(attribute).contains(ucitano)) {
				System.out.println("Given value is not legal for atribute "
						+ attribute + " Expected value is one from "
						+ data.getVariables().get(attribute));
			} else {
				break;
			}
		} while (true);
		return ucitano;
	}
}
