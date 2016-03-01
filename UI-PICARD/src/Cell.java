import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


public class Cell implements Comparator<Cell> {
	private int x;
	private int y;
	private int totalCost;
	public static Matrix MATRIX;
	

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getTotalCost() {
		return totalCost;
	}
	
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * Ne uzima u obzir da jedna od toèaka možda
	 * nije u prostoru potrage
	 * @param other
	 * @return
	 */
	private boolean isNeighbour(Cell other){
		int deltax=Math.abs(other.getX()-this.getX());
		if(deltax==1)return true;
		int deltay=Math.abs(other.getY()-this.getY());
		if(deltay==1)return true;
		return false;
	}
	
	/**
	 * Cijena prelaska ove u drugu celiju jednaka je apsolutnoj razlici visina.
	 * Visine sadržane u matrici koja èuva podatke.
	 * @param other
	 * @return
	 */
	public int getCost(Cell other){
	if(!this.isNeighbour(other)){
		throw new IllegalArgumentException("Cells are not neigbours! :"+this+"\t"+other);
	}else{
		return Math.abs(MATRIX.getValue(other)-MATRIX.getValue(this));
	}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
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
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+") h= "+totalCost;
	}
	
	public boolean inArea(int x1,int y1,int x2,int y2){
	if(this.x<x1 || this.x>x2 || this.y<y1 || this.y>y2)return false;
	return true;
	}
	
	
	public List<Cell> expand(){
		int matrixDimension=MATRIX.getN();
		List<Cell> expanded;
		if(this.y<matrixDimension/2){
			expanded= expand(0,0,matrixDimension-1,matrixDimension/2-1);
		}else{
			expanded=expand(0, matrixDimension/2, matrixDimension-1,matrixDimension-1);
		}
		if(MATRIX.getValue(this)==0){
			expanded.addAll(getExtraNeighbours());
		}
	return expanded;
	}
	
	public List<Cell> expand(int x1,int y1,int x2,int y2){
		if(!this.inArea(x1, y1, x2, y2)) throw new IllegalArgumentException(
				"This point is already not i search area: "+this);
		List<Cell> neigh=new ArrayList<Cell>(4);
		if(this.getX()-1>=x1){
			Cell top=new Cell();
			top.setX(this.getX()-1);
			top.setY(this.getY());
			int newcost=this.getCost(top);
			top.setTotalCost(this.totalCost+newcost);
			neigh.add(top);
		}
		if(this.getX()+1<=x2){
			Cell right=new Cell();
			right.setX(this.getX()+1);
			right.setY(this.getY());
			int newcost=this.getCost(right);
			right.setTotalCost(this.totalCost+newcost);
			neigh.add(right);
		}
		if(this.getY()-1>=y1){
			Cell left=new Cell();
			left.setX(this.getX());
			left.setY(this.getY()-1);
			int newcost=this.getCost(left);
			left.setTotalCost(this.totalCost+newcost);
			neigh.add(left);
		}
		if(this.getY()+1<=y2){
			Cell bottom=new Cell();
			bottom.setX(this.getX());
			bottom.setY(this.getY()+1);
			int newcost=this.getCost(bottom);
			bottom.setTotalCost(this.totalCost+newcost);
			neigh.add(bottom);
		}
		
		
	
		
		return neigh;
		
	}

	private List<Cell> getExtraNeighbours() {
		List<Cell> nl=new ArrayList<Cell>();
		if(this.equals(MATRIX.getSsCell())){
			nl.add(MATRIX.getSlCell());
		}else if(this.equals(MATRIX.getSlCell())) {
			nl.add(MATRIX.getSsCell());
			
		}else{
			for (Map.Entry<String, List<Cell>> entry : MATRIX.getSpecial().entrySet())
			{
			   if(entry.getValue().contains(this)){
				   for(Cell c: entry.getValue()){
					   if(!c.equals(this))nl.add(c);
				   }
			   }
			}
		}
		return nl;
	}

	@Override
	public int compare(Cell o1, Cell o2) {
		return Integer.compare(o1.getTotalCost(), o2.getTotalCost());
	}
	
	
	}
	

