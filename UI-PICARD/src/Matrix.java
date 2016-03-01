import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Matrix {

	private Integer[][] data;
	private int n;
	private  Map<String, List<Cell>> special = new HashMap<String, List<Cell>>();
	private Cell startCell;
	private Cell endCell;
	private Cell ssCell;
	private Cell slCell;

	public Matrix(String filePath) throws IOException {
		List<String> fileLines = Files
				.readAllLines(new File(filePath).toPath());
		this.n = fileLines.size();
		this.data = new Integer[this.n][this.n];
		for (int i = 0; i < this.n; i++) {
			String[] temp = fileLines.get(i).split(" ");
			for (int j = 0; j < this.n; j++) {
				try {
					this.data[i][j] = Integer.parseInt(temp[j]);
				} catch (NumberFormatException ne) {
					this.data[i][j] = 0;
					addToSpecial(i, j, temp[j]); // add special point P,S,C or T
				}

			}

		}

	}

	private void addToSpecial(int i, int j, String s) {
		Cell n = new Cell();
		n.setX(i);
		n.setY(j);
		if(s.equals("P")){
			n.setTotalCost(0);
			this.startCell=n;
			return;
		}
		if(s.equals("C")){
			this.endCell=n;
			return;
		}
		if(s.equals("SS")){
			this.ssCell=n;
			return;
		}
		if(s.equals("SL")){
			this.slCell=n;
			return;
		}
		if (special.containsKey(s)) {
			special.get(s).add(n);
		} else {
			List<Cell> nl = new ArrayList<Cell>();
			nl.add(n);
			special.put(s, nl);
		}

	}

	public int getValue(int i, int j) {
		if (i < 0 || i >= n || j < 0 || j >= n)
			throw new IllegalArgumentException("Index out of, in getValue");
		return data[i][j];
	}

	public int getValue(Cell c) {
		return getValue(c.getX(), c.getY());
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				sb.append(data[i][j] + "  ");
			}
			sb.append("\n");

		}
		return sb.toString();
	}

	public void ispisiVrijednosti() {
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(getValue(i, j) + "  ");
			}
			System.out.print("\n");

		}
	}

	public Integer[][] getData() {
		return data;
	}

	public void setData(Integer[][] data) {
		this.data = data;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Map<String, List<Cell>> getSpecial() {
		return special;
	}

	public void setSpecial(Map<String, List<Cell>> special) {
		this.special = special;
	}

	public Cell getStartCell() {
		return startCell;
	}

	public void setStartCell(Cell startCell) {
		this.startCell = startCell;
	}

	public Cell getEndCell() {
		return endCell;
	}

	public void setEndCell(Cell endCell) {
		this.endCell = endCell;
	}

	public Cell getSsCell() {
		return ssCell;
	}

	public void setSsCell(Cell ssCell) {
		this.ssCell = ssCell;
	}

	public Cell getSlCell() {
		return slCell;
	}

	public void setSlCell(Cell slCell) {
		this.slCell = slCell;
	}

	

}
