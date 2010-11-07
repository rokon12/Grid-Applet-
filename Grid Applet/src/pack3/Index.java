package pack3;

public class Index {

	public int	row, col;

	public Index(int row, int col) {
		this.row = row;
		this.col = col;
	}

	@Override
	public String toString() {
		return "(" + row + "," + col + ")";
	}
}
