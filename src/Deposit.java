import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;

public class Deposit extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Deposit frame = new Deposit("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Deposit(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterAmountTo = new JLabel("Enter amount to deposit:");
		lblEnterAmountTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterAmountTo.setBounds(140, 85, 150, 14);
		contentPane.add(lblEnterAmountTo);
		
		textField = new JTextField();
		textField.setBounds(170, 110, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(170, 141, 86, 23);
		contentPane.add(btnDeposit);
		btnDeposit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","password");
					Statement stmt=con.createStatement();
					stmt.execute("UPDATE balance SET balance=balance+"+textField.getText()+" WHERE email = '"+ email +"'");
					stmt.close();
					con.close();
				} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
					
				}
				Deposit.this.dispose();
				new Homepage(email);
			}
		});
		
		this.setVisible(true);
	}
}
