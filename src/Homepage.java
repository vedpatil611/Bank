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
import javax.swing.JButton;
import javax.swing.JLabel;

public class Homepage extends JFrame {

	private JPanel contentPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Homepage frame = new Homepage(" ");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Homepage(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCheckBalance = new JButton("Check balance");
		btnCheckBalance.setBounds(270, 63, 139, 23);
		contentPane.add(btnCheckBalance);
		btnCheckBalance.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new DisplayBalance(email);
				Homepage.this.dispose();
			}
		});
		
		JButton btnTransfer = new JButton("Transfer");
		btnTransfer.setBounds(270, 103, 139, 23);
		contentPane.add(btnTransfer);
		btnTransfer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Homepage.this.dispose();
				new Transfer(email);
			}
		});
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(270, 143, 139, 23);
		contentPane.add(btnWithdraw);
		btnWithdraw.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Homepage.this.dispose();
				new Withdraw(email);
			}
		});
		
		JButton btnDeposit = new JButton("Deposit");
		btnDeposit.setBounds(270, 183, 139, 23);
		contentPane.add(btnDeposit);
		btnDeposit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Homepage.this.dispose();
				new Deposit(email);
			}
		});
		
		JButton btnChangePassword = new JButton("Change password");
		btnChangePassword.setBounds(270, 223, 139, 23);
		contentPane.add(btnChangePassword);
		btnChangePassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Homepage.this.dispose();
				new ChangePassword(email);
			}
		});
		
		JLabel lblWelcome = new JLabel("Welcome");
		lblWelcome.setBounds(31, 67, 187, 14);
		contentPane.add(lblWelcome);
		
		this.setVisible(true);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","password");
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT fname,lname FROM account WHERE email = '"+ email +"'");
			while(rs.next()) {
				String fname = rs.getString("fname");
				String lname = rs.getString("lname");
				lblWelcome.setText("Welocome " + fname + " " + lname);
			}
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			
		}
		this.setVisible(true);
	}
}
