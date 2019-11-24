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
import javax.swing.JDialog;

public class Transfer extends JFrame {

	private JPanel contentPane;
	private JTextField textEmail;
	private JTextField textAmount;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Transfer frame = new Transfer("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Transfer(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEnterEmail = new JLabel("Enter email:");
		lblEnterEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterEmail.setBounds(175, 69, 100, 14);
		contentPane.add(lblEnterEmail);
		
		textEmail = new JTextField();
		textEmail.setBounds(123, 94, 200, 20);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		JLabel lblEnterAmount = new JLabel("Enter amount:");
		lblEnterAmount.setHorizontalAlignment(SwingConstants.CENTER);
		lblEnterAmount.setBounds(175, 125, 100, 14);
		contentPane.add(lblEnterAmount);
		
		textAmount = new JTextField();
		textAmount.setBounds(123, 150, 200, 20);
		contentPane.add(textAmount);
		textAmount.setColumns(10);
		
		JButton btnSend = new JButton("Send");
		btnSend.setBounds(175, 194, 100, 23);
		contentPane.add(btnSend);
		btnSend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","password");
					Statement stmt=con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT balance FROM balance WHERE email = '"+ email +"'");
					rs.next();
					int bal = rs.getInt("balance");
					
					if(bal < Integer.parseInt(textAmount.getText())) {
						JDialog d = new JDialog(Transfer.this);
						JLabel l = new JLabel("Amount you are trying to withdraw is greater than available balance"); 
						d.getContentPane().add(l);
						d.setSize(100, 100); 
						d.setVisible(true); 
					} else {
						stmt.execute("START TRANSACTION;");
						stmt.execute("UPDATE balance SET balance=balance-"+textAmount.getText()+" WHERE email = '"+ email +"';");
						stmt.execute("UPDATE balance SET balance=balance+"+textAmount.getText()+" WHERE email='"+textEmail.getText()+"';");
						stmt.execute("COMMIT;");
						Transfer.this.dispose();
					}
					stmt.close();
					con.close();
				} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
					System.out.print(ex);
				}
				new Homepage(email);
			}
		});
		
		this.setVisible(true);
	}
}