package hr.fer.zemris.ui.lab2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import hr.fer.zemris.ui.lab2.prover.Izraz;

public class Matrica {
	private Polje[][] data;
	private int n;
	
	public Matrica(String filePath) throws IOException {
		List<String> fileLines = Files
				.readAllLines(new File(filePath).toPath());
	  n=(int)Math.sqrt(fileLines.size());
	 data=new Polje[n][n];
	 for (String line : fileLines) {
		parseLine(line);
	}
	 dodajSusjedstva();
	// System.out.println(this);
	}

	private void dodajSusjedstva() {
		for(int i=0;i<n;i++){
			for(int j=0;j<n;j++){
				Polje radno=data[i][j];
				List<Polje> susjedi=new ArrayList<Polje>(4);
				if(i>0)susjedi.add(data[i-1][j]);//lijevi
				if(i<n-1)susjedi.add(data[i+1][j]);//desni
				if(j>0) susjedi.add(data[i][j-1]);//gornji
				if(j<n-1) susjedi.add(data[i][j+1]);//donji
				radno.setSusjedi(susjedi);
				//System.out.println("radno: "+radno+" susjedi "+radno.getSusjedi());
			}
		}
		
		
		
		
	}

	private void parseLine(String line) {
		
	String[] parts=line.trim().split(" ");
    String indeks=parts[0];
    String []ij= indeks.split(",");
    int i=Integer.parseInt(ij[0].substring(1))-1;
    int j=Integer.parseInt(ij[1].substring(0,ij[1].length()-1))-1;
    Polje p=new Polje();
    p.setI(i);p.setJ(j);
    data[i][j]=p;
    String pp=parts[1].split("=")[1];
    boolean value=false;
    if(Integer.parseInt(pp)==1)value=true;
    p.setS(new Izraz("S"+i+""+j, value));
    
    pp=parts[2].split("=")[1];
    value=false;
    if(Integer.parseInt(pp)==1)value=true;
    p.setB(new Izraz("B"+i+""+j, value));
    
    pp=parts[3].split("=")[1];
    value=false;
    if(Integer.parseInt(pp)==1)value=true;
    p.setG(new Izraz("G"+i+""+j, value));
    
    pp=parts[4].split("=")[1];
    value=false;
    if(Integer.parseInt(pp)==1)value=true;
    p.setW(new Izraz("W"+i+""+j, value));
    
    pp=parts[5].split("=")[1];
    value=false;
    if(Integer.parseInt(pp)==1)value=true;
    p.setP(new Izraz("P"+i+""+j, value));
    
    pp=parts[6].split("=")[1];
    value=false;
    if(Integer.parseInt(pp)==1)value=true;
    p.setT(new Izraz("T"+i+""+j, value));
    
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = n-1; i >=0; i--) {
			for (int j = 0; j < n; j++) {
				sb.append(data[j][i] + "  ");
			}
			sb.append("\n");

		}
		return sb.toString();
	}
	
	public Polje getElementAt(int i,int j) {
		return data[i][j];

	}
	
	public int getN(){
		return n;
	}

}
