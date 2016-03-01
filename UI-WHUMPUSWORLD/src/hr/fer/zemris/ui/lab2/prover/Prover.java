package hr.fer.zemris.ui.lab2.prover;

import java.util.ArrayList;
import java.util.List;

public class Prover {

	private Prover() {
		// TODO Auto-generated constructor stub
	}

	public static boolean rezolucijaOpovrgavanjem(Klauzula zakljucak,
			List<Klauzula> premise) {

		List<Klauzula> suportSet = new ArrayList<Klauzula>();
		for (Izraz iz : zakljucak.getIzrazi()) {// negacija zakljucka
			iz.setValue(!iz.getValue());

			suportSet.add(new Klauzula(iz));
		}
		int i = -1;
		pojednostavi2(premise,-1);
		Integer last = 0;
		do {
						
			boolean kombiniranoUIteraciji = false;
			++i;
			Klauzula radna = suportSet.get(i);

			
			for (int j =0;j<suportSet.size();j++) {
				if(i==j)continue;
				Klauzula druga = suportSet.get(j);
				Klauzula kombinirana = radna.dajTrecu(druga);
				if (kombinirana != null) {
					if (kombinirana.getIzrazi().isEmpty()) {
						return true;
					} else {
						suportSet.add(kombinirana);
						kombiniranoUIteraciji = true;
					}
				}
			}
			for (Klauzula druga : premise) {	
				Klauzula kombinirana = radna.dajTrecu(druga);
				if (kombinirana != null) {
					
						if (kombinirana.getIzrazi().isEmpty()) {
							return true;
						} else {

							suportSet.add(kombinirana);
							kombiniranoUIteraciji = true;

						}
					}
				}
			
			if (kombiniranoUIteraciji) {
				pojednostavi2(suportSet,last);
				last=suportSet.size()-1;
			}
		} while (i < suportSet.size() - 1);
		return false;
	}



	public static void pojednostavi2(List<Klauzula> sve, Integer last) {
	tu:	for (int i = last + 1; i < sve.size(); i++) {
			Klauzula prva = sve.get(i);
			for (int j = 0; j < sve.size(); j++) {
				if (i == j)
					continue;
				Klauzula druga = sve.get(j);
				if (prva.jePodskup(druga)) {
					sve.remove(j);
					continue tu;

				}else{
					if(druga.jePodskup(prva)){
						sve.remove(i);
						continue tu;
					}
				}
			}
			
		}
	}

}
