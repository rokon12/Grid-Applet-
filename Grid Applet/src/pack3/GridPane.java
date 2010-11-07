package pack3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

public class GridPane extends JPanel {
	public static final int ROWS = 28, COLUMNS = 200;
	public static int left = 30, top = 30, increament = 1, gridHeight = 20,
			gridWidth = 6, gridX = left, right, bottom;

	public static int[] gridYs = new int[ROWS];
	private Grid grids[][];
	private Color selectedColor;

	private Cursor defultCursor = new Cursor(Cursor.DEFAULT_CURSOR);
	private Cursor crossHairCursor = new Cursor(Cursor.CROSSHAIR_CURSOR);

	public GridPane() {
		super(new BorderLayout());

		MouseTracker tracker = new MouseTracker();
		add(tracker);

		initializeGrids();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		decorateBorder(g);

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLUMNS; col++) {
				drawGrid(grids[row][col], g);
			}
		}

		g.setColor(Color.BLACK);
		g.drawLine(right, top, right, bottom);
		g.drawLine(left, bottom, right, bottom);
	}

	private void decorateBorder(Graphics g) {
		g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		for (int col = 0; col < COLUMNS; col += 10) {
			g.drawString(col + "", grids[0][col].x, top - 10);
			g.drawLine(grids[0][col].x, top, grids[0][col].x, top - 5);
		}
		g.drawString(COLUMNS + "", right, top - 10);
		g.drawLine(right, top, right, top - 5);

		for (int row = 0; row < ROWS; row += 2) {
			g.drawString(row + "", left - 20, grids[row][0].y);
			g.drawLine(left, grids[row][0].y, left - 5, grids[row][0].y);
		}
		g.drawString(ROWS + "", left - 20, bottom);
		g.drawLine(left, bottom, left - 5, bottom);
	}

	private void drawGrid(Grid grid, Graphics g) {
		g.setColor(Color.BLACK);

		g.drawLine(grid.x, grid.y, grid.x + grid.width, grid.y);
		g.drawLine(grid.x, grid.y, grid.x, grid.y + grid.height);
		g.setColor(grid.getColor());
		g.fillRect(grid.x + 1, grid.y + 1, grid.width, grid.height);
	}

	private void draw(int x, int y, int width, int height) {
		repaint(x, y, width, height);
	}

	public void setSelectedColor(Color selectedColor) {
		this.selectedColor = selectedColor;
	}

	private void initializeGrids() {
		grids = new Grid[ROWS][COLUMNS];
		gridYs[0] = top;
		int[] values = { 5, 10, 25, 25 };

		for (int row = 0; row < ROWS / 2; row++) {
			gridX = left;
			for (int col = 0; col < COLUMNS; col++) {
				grids[row][col] = new Grid(new Index(row, col),
						values[col / 50]);
				gridX += gridWidth;
			}

			gridYs[row + 1] = gridYs[row] + gridHeight;
			gridHeight -= increament;
		}

		for (int row = ROWS / 2; row < ROWS; row++) {
			gridX = left;
			gridYs[row] = gridYs[row - 1] + gridHeight;
			gridHeight += increament;

			for (int col = 0; col < COLUMNS; col++) {
				grids[row][col] = new Grid(new Index(row, col),
						values[col / 50]);
				gridX += gridWidth;
			}
		}

		right = gridX;
		bottom = gridYs[ROWS - 1] + gridHeight;
	}

	// ************************************************************

	/**
	 * This class implements a transparent {@link JPanel} with
	 * {@link MouseListener} and {@link MouseMotionListener}
	 */
	private class MouseTracker extends JPanel implements MouseListener,
			MouseMotionListener {
		private boolean dragging = false;
		private Index gridF, gridT;
		private Grid from, to;

		public MouseTracker() {
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			setOpaque(false);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (!dragging) {
				gridF = new Index(searchIndexY(e.getY()),
						searchIndexX(e.getX()));
				dragging = true;
				from = grids[gridF.row][gridF.col];
			}

			setCursor(defultCursor);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			searchGridIndex(e.getX(), e.getY());
			this.repaint();
			setCursor(crossHairCursor);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (dragging) {
				searchGridIndex(e.getX(), e.getY());

				dragging = false;

				for (int row = from.row(); row <= to.row(); row++) {
					for (int col = from.column(); col <= to.column(); col++) {
						grids[row][col].setColor(selectedColor);
					}
				}

				draw(from.x, from.y, to.right - from.x + 1, to.bottom - from.y
						+ 1);
			}
			setCursor(defultCursor);
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.GREEN);
			if (dragging) {
				g.drawRect(from.x, from.y, to.x - from.x + to.width, to.y
						- from.y + to.height);
			}
		}

		private void searchGridIndex(int x, int y) {
			int iX = searchIndexX(x);
			int iY = searchIndexY(y);

			gridT = new Index(iY, iX);
			to = grids[gridT.row][gridT.col];
			if (gridF.col > gridT.col) {
				from = grids[from.row()][gridT.col];
				to = grids[to.row()][gridF.col];
			}

			if (gridF.row > gridT.row) {
				from = grids[gridT.row][from.column()];
				to = grids[gridF.row][to.column()];
			}
		}

		private int searchIndexY(int y) {
			if (y < top) {
				return 0;
			}

			for (int i = 0; i < gridYs.length; i++) {
				if (y < gridYs[i]) {
					return i - 1;
				}
			}

			return ROWS - 1;
		}

		private int searchIndexX(int x) {
			if (x < left) {
				return 0;
			}

			int iX = (x - left) / gridWidth;

			return iX < COLUMNS ? iX : COLUMNS - 1;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseMoved(MouseEvent e) {
		}
	}
}
