import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingConstants;
import javax.swing.JButton;

public class DisplayBalance extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DisplayBalance frame = new DisplayBalance("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DisplayBalance(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBalance = new JLabel("Balance:");
		lblBalance.setHorizontalAlignment(SwingConstants.CENTER);
		lblBalance.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblBalance.setBounds(105, 97, 218, 31);
		contentPane.add(lblBalance);
		
		JButton btnOk = new JButton("OK");
		btnOk.setBounds(172, 155, 89, 23);
		contentPane.add(btnOk);
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","password");
			Statement stmt=con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT balance FROM balance WHERE email = '"+ email +"'");
			while(rs.next()) {
				int bal = rs.getInt("balance");
				lblBalance.setText("Balance: " + bal);
			}
		} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
			
		}

		this.setVisible(true);
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DisplayBalance.this.dispose();
				new Homepage(email);
			}
		});
	}
}
