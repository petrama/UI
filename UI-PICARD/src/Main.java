import java.io.IOException;


public class Main {

	public static void main(String[] args) throws IOException {
		Matrix mat=new Matrix("dat.txt");
		Cell.MATRIX=mat;
		System.out.println(mat);
		//System.out.println("____________________________");
		//mat.ispisiVrijednosti();
		System.out.println(mat.getSpecial());
		
		Cell neka=new Cell();
		neka.setX(10);
		neka.setY(5);
		neka.setTotalCost(0);
		//System.out.println(neka);
		
		for(Cell celija : neka.expand(0, 0,11,5)){
		System.out.println(celija);
	}
		//System.out.println("____________________________");
		
		ManhattanDistance man=new ManhattanDistance(0, 0, 11, 5);
		Cell cilj=new Cell();
		cilj.setX(11);
		cilj.setY(5);
		//System.out.println(man.uniformCostSearch(neka,cilj ));
		
		

}
}