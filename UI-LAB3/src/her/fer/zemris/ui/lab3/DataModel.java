package her.fer.zemris.ui.lab3;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



public class DataModel {
	private Map<String, List<String>> variables;
	private Set<Rule> rules;
	
	public DataModel(String fileVars,String filePrem) throws IOException {
		variables=new HashMap<String, List<String>>();
		rules=new TreeSet<>(Rule.PRIORITY_COMPARATOR);
		parseVars(fileVars);
		parsePrems(filePrem);
		//parseBase(fileVars);
		
	}
	
	

	public Map<String, List<String>> getVariables() {
		return variables;
	}



	public void setVariables(Map<String, List<String>> variables) {
		this.variables = variables;
	}



	public Set<Rule> getRules() {
		return rules;
	}



	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}


//	private void parseBase(String file) throws IOException{
//		List<String> lines=	Files.readAllLines(new File(file).toPath());
//		int tempPriority=0;
//		int index=0;
//		while(true){
//			String line=lines.get(index).trim();
//			if(line.equals(""))break;
//			String[] parts=line.trim().split("=");
//			if(parts.length==2){
//				
//				
//					String tempVar=parts[0].trim();
//					List<String>tempValues=new ArrayList<String>();
//					trimAndAdd(tempValues,parts[1].trim().split(","));
//					variables.put(tempVar,tempValues);
//			index++;
//		}}
//		index++;
//		while(true){
//			String line=lines.get(index);
//			if(line.isEmpty())break;
//			if(line.startsWith("IF")){
//				rules.add(new Rule(parseLine(line.substring("IF".length()).trim()), parseLine(lines.get(index+1).trim().substring("THEN".length()).trim()), tempPriority));	
//			
//		}
//			index+3=
//		};
//		for(int i=index+1;i<lines.size()-1;i+=3){
//	
//		
//			String line=lines.get(i).trim();
//			System.err.println(line);
//			if(line.startsWith("IF")){
//				rules.add(new Rule(parseLine(line.substring("IF".length()).trim()), parseLine(lines.get(i+1).trim().substring("THEN".length()).trim()), tempPriority));	
//			
//		}}
//	}

	private void parsePrems(String filePrem) throws IOException {
		List<String> lines=	Files.readAllLines(new File(filePrem).toPath());
		int tempPriority=0;
		String ifPart="";
		String thenPart="";
		boolean lastIfPart=false;

		for	(String line:lines){
			//String line=cleanOfBlanks(linee);
			//System.out.println(line);
			int index=line.indexOf("IF");
		
			if(index!=-1){
				
				
				if(ifPart!=""){
					Rule r=new Rule(parseLine(ifPart), parseLine(thenPart),tempPriority);
					//System.out.println(r);
					rules.add(r);
					ifPart="";thenPart="";
				}
				++tempPriority;
				
				ifPart+=line.substring(index+"IF".length());
				lastIfPart=true;
				
				
			}else{
				if(line.trim().startsWith("THEN")){
					lastIfPart=false;
					thenPart+=line.trim().substring("THEN".length()).trim();
				}else{
					if(lastIfPart){
						ifPart+=line.trim();
					}else{
						thenPart+=line.trim();
					}
				}
				
			};
			
		}
		Rule r=new Rule(parseLine(ifPart), parseLine(thenPart),tempPriority);
		//System.out.println(r);
		rules.add(r);
	}

	private void parseVars(String fileVars) throws IOException {
		List<String> lines=Files.readAllLines(new File(fileVars).toPath());
		String tempVar="";
		List<String> tempValues=new ArrayList<String>();
		for (String line : lines) {
			//String line=cleanOfBlanks(linee);
			String[] parts=line.trim().split("=");
		
			
			if(parts.length==1){
				
				trimAndAdd(tempValues,parts[0].trim().split(","));
			}else{
				if(parts.length==2){
					
					if(tempVar!=""){
						variables.put(tempVar,tempValues);
						//System.out.println(tempVar);
						//System.out.println(tempValues);
					}
						tempVar=parts[0].trim();
						//System.out.println(tempVar);
						tempValues=new ArrayList<String>();
						trimAndAdd(tempValues,parts[1].trim().split(","));
					
					}
				}
			}
		variables.put(tempVar,tempValues);
		
		}
		
	
	
//	private String cleanOfBlanks(String linee) {
//		StringBuilder sb=new StringBuilder();
//		for (int i = 0; i < linee.trim().length(); i++) {
//			if(linee.charAt(i)!=' ' ){
//				sb.append(linee.charAt(i));
//			}
//			
//		}
//		return sb.toString();
//	}



	private void trimAndAdd(List<String> tempValues, String[] elems) {
		for (String s : elems) {
			String ss=s.trim();
			if(!ss.equals(""))tempValues.add(ss);
		}
		
	}
	
	public Set<AttrValues> parseLine(String line){
		Set<AttrValues> terms=new LinkedHashSet<>();
		String[]dijelovi=line.split("&");
		for (String s : dijelovi) {
			terms.add(AttrValues.parseAttrValues(s));
		}
		return terms;
	}

	@Override
	public String toString() {
		return variables.toString()+"\n"+
				rules.toString();
	}
	
	public void print(){
		for(Map.Entry<String, List<String>> pair:variables.entrySet()){
			System.out.println(pair.getKey()+" <=> "+pair.getValue());
		}
		for (Rule rule : rules) {
			System.out.println(rule);
		}
	}
	
	public Set<Rule> getConfictedSet(String attribute){
		Set<Rule> set=new TreeSet<Rule>(Rule.PRIORITY_COMPARATOR);
		for (Rule rule : rules) {
			for(AttrValues av:rule.getRight()){
				if(av.getName().equals(attribute)) set.add(rule);
			}
		}
		return set;
	}


}
