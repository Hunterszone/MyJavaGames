package payment;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
	public static void main(String[] args) {
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
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 400);
		setTitle("Donate now");

		ca = new CardLayout(0, 0);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(ca);

		int columns = 8;

		JPanel panel1 = new JPanel();
		
		JLabel lbl = new JLabel("Name:", JLabel.LEFT);
		lbl.setBounds(80, 120, 60, 20);
		panel1.add(lbl);

		JTextField name = new JTextField(columns);
		name.setBounds(120, 120, 110, 20);
		panel1.add(name);
		
		JLabel card = new JLabel("Card:", JLabel.LEFT);
		card.setBounds(280,20,60,20);
		panel1.add(card);
		
		JTextField cardNumber = new JTextField(columns);
		cardNumber.setBounds(320,20,110,20);
		panel1.add(cardNumber);
		
		JLabel month = new JLabel("ExpMonth:", JLabel.LEFT);
		month.setBounds(480,20,60,20);
		panel1.add(month);
		
		JTextField expMonth = new JTextField(columns);
		expMonth.setBounds(520,20,110,20);
		panel1.add(expMonth);
		
		JLabel year = new JLabel("ExpYear:", JLabel.LEFT);
		year.setBounds(680,20,60,20);
		panel1.add(year);
		
		JTextField expYear = new JTextField(columns);
		expYear.setBounds(720,20,110,20);
		panel1.add(expYear);
		
		JLabel cvv = new JLabel("CVV:", JLabel.LEFT);
		cvv.setBounds(880,20,60,20);
		panel1.add(cvv);
		
		JTextField cvvText = new JTextField(columns);
		cvvText.setBounds(920,20,110,20);
		panel1.add(cvvText);

		JButton submit = new JButton("Submit");
		panel1.add(submit);
		
		submit.addActionListener((ActionEvent e) -> {
			try {
				TransactionProcessorOkHttp.createTransaction(
						cardNumber.getText().trim(), 
						name.getText().trim(), 
						expMonth.getText().trim(), 
						expYear.getText().trim(), 
						cvvText.getText().trim()
						);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    JOptionPane.showConfirmDialog(null, "Payment has been created.", "Info", JOptionPane.DEFAULT_OPTION, 
		    JOptionPane.INFORMATION_MESSAGE);
		});

		contentPane.add("card1", panel1);
		
		JPanel panel2 = new JPanel();

		JLabel token = new JLabel("Token:", JLabel.LEFT);
		token.setBounds(280,20,60,20);
		panel2.add(token);
		
		JTextField tokenField = new JTextField(columns);
		tokenField.setBounds(320,20,110,20);
		panel2.add(tokenField);
		
		JLabel amount = new JLabel("Amount:", JLabel.LEFT);
		amount.setBounds(480,20,60,20);
		panel2.add(amount);
		
		JTextField amountField = new JTextField(columns);
		amountField.setBounds(520,20,110,20);
		panel2.add(amountField);

		JLabel currency = new JLabel("Currency", JLabel.LEFT);
		currency.setBounds(80, 120, 60, 20);
		panel2.add(currency);

		JTextField currencyField = new JTextField(columns);
		currencyField.setBounds(120, 120, 110, 20);
		panel2.add(currencyField);
		
		JButton pay = new JButton("Pay");
		panel2.add(pay);
		
		pay.addActionListener((ActionEvent e) -> {
			try {
				PaymentProcessorOkHttp.processPayment(
						tokenField.getText().trim(), 
						amountField.getText().trim(), 
						currencyField.getText().trim()
						);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    JOptionPane.showConfirmDialog(null, "Payment has been processed.", "Info", JOptionPane.DEFAULT_OPTION, 
		    JOptionPane.INFORMATION_MESSAGE);
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