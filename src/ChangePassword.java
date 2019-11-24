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
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JDialog;

public class ChangePassword extends JFrame {

	private JPanel contentPane;
	private JTextField textOld;
	private JTextField textNew;
	private JTextField textConf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChangePassword frame = new ChangePassword("");
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
	public ChangePassword(String email) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOldPassword = new JLabel("Old password:");
		lblOldPassword.setBounds(100, 50, 97, 14);
		contentPane.add(lblOldPassword);
		
		JLabel lblNewPassword = new JLabel("New password:");
		lblNewPassword.setBounds(100, 100, 97, 14);
		contentPane.add(lblNewPassword);
		
		JLabel lblNewLabel = new JLabel("Confirm password:");
		lblNewLabel.setBounds(100, 150, 97, 14);
		contentPane.add(lblNewLabel);
		
		textOld = new JTextField();
		textOld.setBounds(246, 47, 123, 20);
		contentPane.add(textOld);
		textOld.setColumns(10);
		
		textNew = new JTextField();
		textNew.setBounds(246, 97, 123, 20);
		contentPane.add(textNew);
		textNew.setColumns(10);
		
		textConf = new JTextField();
		textConf.setBounds(246, 147, 123, 20);
		contentPane.add(textConf);
		textConf.setColumns(10);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(177, 200, 89, 23);
		contentPane.add(btnSave);
		btnSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver").newInstance(); 
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","password");
					Statement stmt=con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT password FROM account WHERE email = '"+ email +"'");
					rs.next();
					String temp = rs.getString("password");
					
					if(!textOld.getText().equals(temp)) {
						JDialog d = new JDialog(ChangePassword.this);
						JLabel l = new JLabel("Incorrect old password"); 
						d.getContentPane().add(l);
						d.setSize(500, 100); 
						d.setVisible(true); 
					} else if(!textNew.getText().equals(textConf.getText())) {
						JDialog d = new JDialog(ChangePassword.this);
						JLabel l = new JLabel("Password does not match"); 
						d.getContentPane().add(l);
						d.setSize(500, 100); 
						d.setVisible(true); 
					} else {
						stmt.execute("UPDATE account SET password='"+textNew.getText()+"' WHERE email='"+email+"'");
						ChangePassword.this.dispose();
						new Homepage(email);
					}
					stmt.close();
					con.close();
				} catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
					System.out.print(ex);
				}
			}
		});
		this.setVisible(true);
	}

}
