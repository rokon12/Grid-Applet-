package pack3;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ApplicationApplet extends JApplet {
	private ColorPallete colorPallete;
	private GridPane gridPane;

	private JButton updateBtd;
	private JTextField input;
	private JPanel updatePanal;

	@Override
	public void init() {
		super.init();

		gridPane = new GridPane();
		colorPallete = new ColorPallete(10, gridPane);

		// gridPane.setBorder(BorderFactory.createTitledBorder("Grids"));

		updatePanal = new JPanel();
		updatePanal.setBorder(BorderFactory.createTitledBorder("Update"));

		updateBtd = new JButton("Update Pallate");
		updateBtd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				remove(colorPallete);
				repaint();
				colorPallete = new ColorPallete(Integer.parseInt(input
						.getText()), gridPane);
				add(colorPallete, BorderLayout.NORTH);
				validate();
			}
		});
		updatePanal.add(updateBtd);

		input = new JTextField(5);
		input.setHorizontalAlignment(JTextField.CENTER);
		updatePanal.add(input);

		getContentPane().add(colorPallete, BorderLayout.NORTH);
		getContentPane().add(gridPane);
		getContentPane().add(updatePanal, BorderLayout.SOUTH);
	}
}
