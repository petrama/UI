package her.fer.zemris.ui.lab3;

import java.io.IOException;


public class Main {
	public static void main(String[] args) throws IOException {
		DataModel model=new DataModel("InsVars", "InsPrems");
		System.out.println("BAZA ZNANJA:");
		model.print();
		ExpertSystem es=new ExpertSystem(model);
		es.prove("Instrument");
		
		

	}

}
