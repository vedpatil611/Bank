import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JDialog;

public class Splash extends JFrame {

	private JPanel contentPane;
	private JTextField textEmail;
	private JTextField textPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Splash frame = new Splash();
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
	public Splash() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textEmail = new JTextField();
		textEmail.setBounds(230, 62, 86, 20);
		contentPane.add(textEmail);
		textEmail.setColumns(10);
		
		textPass = new JTextField();
		textPass.setBounds(230, 93, 86, 20);
		contentPane.add(textPass);
		textPass.setColumns(10);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(115, 65, 69, 14);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(115, 96, 69, 14);
		contentPane.add(lblPassword);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.setBounds(170, 150, 89, 23);
		contentPane.add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.setBounds(170, 184, 89, 23);
		contentPane.add(btnSignUp);
		
		btnSignUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new SignUp().setVisible(true);
				Splash.this.dispose();
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean b = true;
				try {
					Class.forName("com.mysql.cj.jdbc.Driver"); 
					Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/bank","root","password");
					Statement stmt=con.createStatement();
					ResultSet rs = stmt.executeQuery("SELECT password FROM account WHERE email = '"+ textEmail.getText() +"'");
					while(rs.next()) {
						String pass = rs.getString("password");
						if(pass.equals(textPass.getText())) {
							b=false;
							Splash.this.dispose();
							new Homepage(textEmail.getText());
						}
					}
					if(b) {
						JDialog d = new JDialog(Splash.this);
						JLabel l = new JLabel("Incorrect credentials"); 
						d.getContentPane().add(l);
						d.setSize(500, 100); 
						d.setVisible(true); 
					}
				} catch(SQLException | ClassNotFoundException ex) {
					System.out.println("fail: " + ex);
				}
			}
		});
		this.setVisible(true);
	}
}
