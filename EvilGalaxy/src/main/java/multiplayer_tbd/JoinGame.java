package multiplayer_tbd;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import extras.ConsoleForm;

public class JoinGame extends JFrame {

	private static final long serialVersionUID = 1L;

	public JoinGame() {

		setTitle("Join Game");
		setSize(400, 400);
		setPreferredSize(new Dimension(950, 381));
		// submit button
		final JButton joinbutt = new JButton("Join");
		joinbutt.setBounds(150, 100, 150, 30);
		joinbutt.setFont(joinbutt.getFont().deriveFont(14f));
		final JButton refrbutt = new JButton("Refresh");
		refrbutt.setBounds(150, 140, 150, 30);
		refrbutt.setFont(refrbutt.getFont().deriveFont(14f));
		// enter name label
		final JLabel label = new JLabel();
		label.setText("Enter IP address:");
		label.setBounds(10, 10, 130, 100);
		label.setFont(label.getFont().deriveFont(14f));
		// empty label which will show event after button clicked
		final JLabel label1 = new JLabel();
		label1.setBounds(10, 180, 30000, 100);
		label1.setFont(label1.getFont().deriveFont(14f));
		final JLabel label2 = new JLabel();
		label2.setBounds(10, 160, 200, 100);
		label2.setFont(label2.getFont().deriveFont(14f));
		// textfield to enter name
		final JTextField textfield = new JTextField();
		textfield.setBounds(150, 50, 150, 30);
		textfield.setFont(textfield.getFont().deriveFont(14f));
		// add to frame
		add(label2);
		add(label1);
		add(textfield);
		add(label);
		add(joinbutt);
		add(refrbutt);
		setLayout(null);
		setVisible(true);
		// setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// action listener
		joinbutt.addActionListener(arg0 -> {
			label2.setText("Waiting for connection...");
			label1.setText("Joined at : " + textfield.getText());
		});

		refrbutt.addActionListener(arg0 -> new ConsoleForm().getTextField().setText("refresh"));
	}

	public static void main(String[] args) {
		new JoinGame();

	}
}