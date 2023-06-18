package payment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class PaymentScreen extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private CardLayout ca;

	/**
	 * Launch the application.
	 */
	public static void init() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PaymentScreen frame = new PaymentScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PaymentScreen() {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(500, 400);
		setTitle("Donate now");

		ca = new CardLayout(0, 0);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(ca);

		int columns = 8;
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);

		JPanel panel1 = new JPanel();
		
		JLabel lbl = new JLabel("Full name:", JLabel.LEFT);
		lbl.setBounds(80, 120, 60, 20);
		panel1.add(lbl);

		JTextField name = new JTextField(columns);
		name.setBounds(120, 120, 110, 20);
		panel1.add(name);
		
		JButton submit = new JButton("Submit");
		panel1.add(submit);
		
		panel1.add(progressBar);
		
		submit.addActionListener((ActionEvent e) -> {
			JOptionPane.showConfirmDialog(null, "Payment created successfully!", "Info", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.INFORMATION_MESSAGE);
			try {
				TransactionProcessorOkHttp.createTransaction(
						"\"\\\"" + name.getText().trim() + "\\\"\""
						);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		contentPane.add("card1", panel1);
		
		JPanel panel2 = new JPanel();

		JButton pay = new JButton("DONATE 10$");
		panel2.add(pay);
		
		JButton pay2 = new JButton("DONATE 20$");
		panel2.add(pay2);
		
		JButton pay3 = new JButton("DONATE 50$");
		panel2.add(pay3);
		
		panel2.add(progressBar);
		
		pay.addActionListener((ActionEvent e) -> {
			progressBar.setValue(0);
			JOptionPane.showConfirmDialog(null, "Payment processed successfully!", "Info", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.INFORMATION_MESSAGE);
			progressBar.setValue(100);
			try {
				PaymentProcessorOkHttp.processPaymentForTen("\"\\\"" + name.getText().trim() + "\\\"\"");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		pay2.addActionListener((ActionEvent e) -> {
			progressBar.setValue(0);
			JOptionPane.showConfirmDialog(null, "Payment processed successfully!", "Info", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.INFORMATION_MESSAGE);
			progressBar.setValue(100);
			try {
				PaymentProcessorOkHttp.processPaymentForTwenty("\"\\\"" + name.getText().trim() + "\\\"\"");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		pay3.addActionListener((ActionEvent e) -> {
			progressBar.setValue(0);
			JOptionPane.showConfirmDialog(null, "Payment processed successfully!", "Info", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.INFORMATION_MESSAGE);
			progressBar.setValue(100);
			try {
				PaymentProcessorOkHttp.processPaymentForFifty("\"\\\"" + name.getText().trim() + "\\\"\"");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		contentPane.add("card2", panel2);
		
		JPanel navigationPanel = new JPanel();

		JButton btnPrevious = new JButton("< PREVIOUS");
		btnPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ca.previous(contentPane);
			}
		});
		navigationPanel.add(btnPrevious);

		JButton btnNext = new JButton("NEXT >");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ca.next(contentPane);
			}
		});
		navigationPanel.add(btnNext);

		add(contentPane);
		add(navigationPanel, BorderLayout.SOUTH);
	}

}