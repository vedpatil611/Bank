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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

public class Withdraw extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Withdraw frame = new Withdraw("");
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
	public Withdraw(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterAmountTo = new JLabel("Enter amount to withdraw:");
		lblEnterAmountTo.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterAmountTo.setBounds(150, 91, 150, 14);
		contentPane.add(lblEnterAmountTo);
		
		JButton btnWithdraw = new JButton("Withdraw");
		btnWithdraw.setBounds(180, 155, 90, 23);
		contentPane.add(btnWithdraw);
		btnWithdraw.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","password");
					Statement stmt=con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT balance FROM balance WHERE email = '"+ email +"'");
					rs.next();
					int bal = rs.getInt("balance");
					
					if(bal < Integer.parseInt(textField.getText())) {
						JDialog d = new JDialog(Withdraw.this);
						JLabel l = new JLabel("Amount you are trying to withdraw is greater than available balance"); 
						d.add(l); 
						d.setSize(100, 100); 
						d.setVisible(true); 
					} else {
						stmt.execute("UPDATE balance SET balance=balance-"+textField.getText()+" WHERE email = '"+ email +"'");
						Withdraw.this.dispose();
					}
					stmt.close();
					con.close();
				} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
					
				}
				new Homepage(email);
			}
		});
		
		textField = new JTextField();
		textField.setBounds(180, 116, 90, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		this.setVisible(true);
	}
}
