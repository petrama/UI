import java.util.ArrayList;
import java.util.List;


public class ManhattanDistance {

	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	
	
	public ManhattanDistance(int x1, int y1, int x2, int y2) {
		super();
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}



//	public Cell uniformCostSearch(Cell s0,Cell goal){
//		List<Cell> open=new ArrayList<Cell>();
//		List<Cell> closed=new ArrayList<Cell>();
//		open.add(s0);
//		while(!open.isEmpty()){
//		Cell n=open.get(0);
//		open.remove(0);
//		if(goal.equals(n)) return n;
//		closed.add(n);
//		System.out.println(open);
//		System.err.println(n);
//		
//		for(Cell c : n.expand(x1, y1, x2, y2) ){
//			if(!closed.contains(c)){
//				for(int i=0;i<open.size();i++){
//					if(open.get(i).compare(open.get(i), c)>=0){
//						open.add(i, c);
//						break;
//					}
//				}
//				open.add(c);
//			}
//		}
//	
//		
//		
//		}
//		return null;
//	}
	
	public Cell uniformCostSearch(Cell s0,Cell goal){
		List<Cell> open=new ArrayList<Cell>();
		List<Cell> closed=new ArrayList<Cell>();
		open.add(s0);
		while(!open.isEmpty()){
			System.out.println(open);
		Cell n=open.get(0);
		open.remove(0);
		if(goal.equals(n)) return n;
		closed.add(n);
	
		System.err.println(n);
		
		for(Cell c : n.expand(x1, y1, x2, y2) ){
			if(!closed.contains(c)){
				open.add(c);
			}
		}
		open.sort(s0);
		
		
		}
		return null;
	}


	
	
}
