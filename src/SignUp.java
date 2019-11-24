import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;

public class SignUp extends JFrame {

	private JPanel contentPane;
	private JTextField fname;
	private JTextField lname;
	private JTextField email;
	private JTextField password;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignUp frame = new SignUp();
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
	public SignUp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		fname = new JTextField();
		fname.setBounds(228, 44, 100, 20);
		contentPane.add(fname);
		fname.setColumns(10);
		
		lname = new JTextField();
		lname.setBounds(228, 75, 100, 20);
		contentPane.add(lname);
		lname.setColumns(10);
		
		email = new JTextField();
		email.setBounds(228, 106, 100, 20);
		contentPane.add(email);
		email.setColumns(10);
		
		password = new JTextField();
		password.setBounds(228, 138, 100, 20);
		contentPane.add(password);
		password.setColumns(10);
		
		JLabel lblFirstName = new JLabel("First name");
		lblFirstName.setBounds(77, 47, 80, 14);
		contentPane.add(lblFirstName);
		
		JLabel lblLastName = new JLabel("Last name");
		lblLastName.setBounds(77, 78, 80, 14);
		contentPane.add(lblLastName);
		
		JLabel lblEmailId = new JLabel("Email id");
		lblEmailId.setBounds(77, 109, 80, 14);
		contentPane.add(lblEmailId);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(77, 141, 80, 14);
		contentPane.add(lblPassword);
		
		JButton signup = new JButton("Sign up");
		signup.setBounds(160, 198, 89, 23);
		contentPane.add(signup);
		
		signup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(checkData()) {
					try {
						Class.forName("com.mysql.cj.jdbc.Driver"); 
						Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","password");
						Statement stmt = con.createStatement();
						stmt.execute("INSERT INTO account(fname,lname,email,password) VALUES('"+fname.getText()+"','"+lname.getText()+"','"+email.getText()+"','"+password.getText()+"')");
						stmt.execute("INSERT INTO balance(email,balance) VALUES('" + email.getText() + "', 0)");
						stmt.close();
					} catch(SQLIntegrityConstraintViolationException pke) {
						
					} catch(SQLException | ClassNotFoundException ex) {
						System.out.println("Con fail" + ex);
					}
					SignUp.this.dispose();
					new Splash();
				}
			}
		});
		this.setVisible(true);
	}
	
	public boolean checkData() {
		if(fname.getText().equals("") || lname.getText().equals("") || email.getText().equals("") || password.getText().equals("")) 
			return false;
		return true;
	}
}
