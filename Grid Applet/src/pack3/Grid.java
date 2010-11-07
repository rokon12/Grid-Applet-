package pack3;

import java.awt.Color;
import java.awt.Rectangle;

public class Grid extends Rectangle {
	private Index index;
	private Color color;
	private int value;
	public int bottom, right; 

	public Grid(Index index, int value) {
		super(GridPane.gridX, GridPane.gridYs[index.row], GridPane.gridWidth,
				GridPane.gridHeight);

		this.index = index;
		this.value = value;
		color = Color.RED;
		
		right = x + width;
		bottom = y + height;
	}

	public Index getIndex() {
		return index;
	}
	
	public int row() {
		return index.row;
	}
	
	public int column(){
		return index.col;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public int getValue() {
		return value;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Grid) {
			return this.index == ((Grid) obj).index;
		}
		return false;
	}
}
