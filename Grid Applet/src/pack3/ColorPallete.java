package pack3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ColorPallete extends JPanel implements MouseListener {
	private boolean updated = false;
	ArrayList<Color> color;
	// private GridPanel chooseFor;
	private JLabel selected;
	private int numberOfGrid = 20;
	private GridPane gridPaneFor;

	private int r = 255, g = 0, b = 0;

	public ColorPallete(int numOfColor, GridPane gridFor) {
		this.gridPaneFor = gridFor;
		this.numberOfGrid = numOfColor;
		setBorder(BorderFactory.createTitledBorder("Color Pallates"));

		color = new ArrayList<Color>(numberOfGrid);
		makeColors();
		setLayout(new GridLayout(1, color.size(), 3, 3));

		selected = new JLabel();

		organize();
	}

	public Color getSelectedColor() {
		return selected.getBackground();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.black);
		int min = 10, max = 100;
		float range = (max - min) / (numberOfGrid - 1); // (100-min)/(numberOfGrid-2);
		System.out.println("range: " + range);

		float[] PallateValue = new float[numberOfGrid];
		PallateValue[0] = min;
		PallateValue[numberOfGrid - 1] = max;
		float temp = min;

		for (int i = 1; i < numberOfGrid; i++) {
			temp += range;
			PallateValue[i] = temp;
		}

		for (int i = 0; i < PallateValue.length; i++) {
			System.out.println(PallateValue[i]);
		}
	}

	private void organize() {
		JLabel l = null;
		for (int i = 0; i < color.size(); i++) {
			l = new JLabel(" ");
			l.setOpaque(true);
			l.setBackground(color.get(i));
			add(l);
			l.addMouseListener(this);
		}

		markSelected(l);
	}

	private void markSelected(JLabel label) {
		if (label.equals(selected))
			return;

		selected.setBorder(BorderFactory.createEmptyBorder());

		label.setBorder(BorderFactory.createMatteBorder(10, 0, 10, 0,
				Color.black));
		gridPaneFor.setSelectedColor(label.getBackground());
		selected = label;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			markSelected((JLabel) e.getSource());
		} catch (ClassCastException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		try {
			JLabel enteredIn = (JLabel) e.getSource();

			if (enteredIn.equals(selected))
				return;

			enteredIn.setBorder(BorderFactory.createLoweredBevelBorder());
		} catch (ClassCastException e1) {
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		try {
			JLabel exitedFrom = (JLabel) e.getSource();

			if (exitedFrom.equals(selected))
				return;

			exitedFrom.setBorder(BorderFactory.createEmptyBorder());
		} catch (ClassCastException e1) {
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	public void makeColors() {
		int increment = Math.round(256 * 4 / numberOfGrid);

		System.out.println("icrement: " + increment);

		for (int i = 0; i < numberOfGrid; i++) {
			color.add(i, nextRGB());// = nextRGB();
			System.out.println("r:" + r + " g:" + g + " b:" + b + " --i:" + i);
			if (i <= numberOfGrid / 4) {
				r = 255;
				g += increment;
				b = 0;
			} else if (i > numberOfGrid / 4 && i <= 2 * numberOfGrid / 4) {
				r -= increment;
				g = 255;
				b = 0;
			} else if (i > 2 * numberOfGrid / 4 && i <= 3 * numberOfGrid / 4) {
				r = 0;
				g -= increment;
				b += increment;
			} else if (i > 3 * numberOfGrid / 4 && i <= 4 * numberOfGrid / 4) {
				r += increment;
				g = 0;
				b = 255;
			}

			if (r > 255)
				r = 255;
			if (g > 255)
				g = 255;
			if (b > 255)
				b = 255;

			if (r < 0)
				r = 0;
			if (g < 0)
				g = 0;
			if (b < 0)
				b = 0;
		}
	}

	public Color nextRGB() {
		return new Color(r, g, b);
	}
}
