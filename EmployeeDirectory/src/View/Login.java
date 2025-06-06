package View;

import java.awt.EventQueue;
import java.awt.Image;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Config.DBConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUserName;
	private JPasswordField txtPassword;
	private JLabel lblMessage;
	private JPanel passwordPanel;
	private JButton btnShowHidePassword;
	private boolean passwordVisible = false;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 651, 476);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		ImageIcon icon = new ImageIcon("C:\\OJT\\image\\icon.jfif");
		Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(img);

		JLabel lblImage = new JLabel(scaledIcon);
		lblImage.setBounds(259, 20, 100, 100);
		contentPane.add(lblImage);

		JLabel lblEmployeeDirectory = new JLabel("EmployeeDirectory");
		lblEmployeeDirectory.setFont(new Font("Arial", Font.BOLD, 22));
		lblEmployeeDirectory.setBounds(217, 136, 276, 21);
		contentPane.add(lblEmployeeDirectory);

		JLabel lbUserName = new JLabel("<html>UserName: <span style='color:red;'>*</span></html>");
		lbUserName.setFont(new Font("Arial", Font.PLAIN, 16));
		lbUserName.setBounds(102, 185, 90, 28);
		contentPane.add(lbUserName);

		txtUserName = new JTextField();
		txtUserName.setFont(new Font("Arial", Font.PLAIN, 16));
		txtUserName.setBounds(226, 185, 176, 28);
		contentPane.add(txtUserName);
		txtUserName.setColumns(10);
		txtUserName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char typedChar = e.getKeyChar();

				if (Character.isDigit(typedChar)) {
					e.consume();
					lblMessage.setText("Username cannot contain digits.");
				} else if (!Character.isLetter(typedChar) && typedChar != ' ') {
					e.consume();
					lblMessage.setText("Username cannot contain special characters.");
				} else {
					lblMessage.setText("");
				}
			}
		});

		JLabel lblPassword = new JLabel("<html>Password: <span style='color:red;'>*</span></html>");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPassword.setBounds(102, 249, 90, 28);
		contentPane.add(lblPassword);

		passwordPanel = new JPanel();
		passwordPanel.setBounds(226, 249, 176, 28);
		passwordPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		passwordPanel.setLayout(new BorderLayout(0, 0));

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		txtPassword.setColumns(10);

		txtPassword.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		passwordPanel.add(txtPassword, BorderLayout.CENTER);

		btnShowHidePassword = new JButton(new ImageIcon("C:\\OJT\\image\\eye_close.png"));
		btnShowHidePassword.setForeground(new Color(255, 255, 255));

		ImageIcon eyeIcon = new ImageIcon("C:\\25212\\OJT\\eye_close.png");
		Image img1 = eyeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		btnShowHidePassword.setIcon(new ImageIcon(img1));

		btnShowHidePassword.setPreferredSize(new Dimension(30, 30));

		btnShowHidePassword.setContentAreaFilled(false);
		btnShowHidePassword.setBorderPainted(false);
		btnShowHidePassword.setOpaque(false);

		passwordPanel.add(btnShowHidePassword, BorderLayout.EAST);

		contentPane.add(passwordPanel);

		btnShowHidePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (passwordVisible) {

					txtPassword.setEchoChar('*');
					passwordVisible = false;

					ImageIcon closedEyeIcon = new ImageIcon("C:\\OJT\\image\\eye_close.png");
					Image imgClosed = closedEyeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					closedEyeIcon = new ImageIcon(imgClosed);
					btnShowHidePassword.setIcon(closedEyeIcon);
				} else {

					txtPassword.setEchoChar((char) 0);
					passwordVisible = true;

					ImageIcon openEyeIcon = new ImageIcon("C:\\OJT\\image\\eye.png");
					Image imgOpen = openEyeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					openEyeIcon = new ImageIcon(imgOpen);
					btnShowHidePassword.setIcon(openEyeIcon);
				}
			}
		});

		JButton btnLogin = new JButton("Login");
		btnLogin.setForeground(new Color(255, 255, 255));
		btnLogin.setBackground(Color.RED);
		btnLogin.setFont(new Font("Arial", Font.BOLD, 16));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = txtUserName.getText().trim();

				String password = new String(txtPassword.getPassword()).trim();

				lblMessage.setText("");

				if (username.isEmpty() && password.isEmpty()) {
					lblMessage.setText("Please enter username and password!");
					return;
				}

				if (!username.isEmpty() && password.isEmpty()) {
					lblMessage.setText("Password is required!");
					return;
				}
				if (username.isEmpty() && !password.isEmpty()) {
					lblMessage.setText("Username is required!");
					return;
				}

				try {
					DBConfig db = new DBConfig();
					Connection conn = db.getConnection();

					String adminSQL = "SELECT * FROM empdirectory.admin WHERE admName = ? AND password = ?";
					PreparedStatement adminStmt = conn.prepareStatement(adminSQL);
					adminStmt.setString(1, username);
					adminStmt.setString(2, password);
					ResultSet adminRs = adminStmt.executeQuery();

					if (adminRs.next()) {
						lblMessage.setText("Admin Login Successful!");
						new AdminNavBar().setVisible(true);
						dispose();
						return;
					}

					String employeeSQL = "SELECT * FROM empdirectory.employee WHERE empName = ? AND password = ?";
					PreparedStatement employeeStmt = conn.prepareStatement(employeeSQL);
					employeeStmt.setString(1, username);
					employeeStmt.setString(2, password);
					ResultSet employeeRs = employeeStmt.executeQuery();

					if (employeeRs.next()) {
						lblMessage.setText("Employee Login Successful!");
						new EmployeeNavBar().setVisible(true);
						dispose();
						return;
					}

					String checkAdminSQL = "SELECT * FROM empdirectory.admin WHERE admName = ?";
					PreparedStatement checkAdminStmt = conn.prepareStatement(checkAdminSQL);
					checkAdminStmt.setString(1, username);
					ResultSet checkAdminRs = checkAdminStmt.executeQuery();

					String checkEmployeeSQL = "SELECT * FROM empdirectory.employee WHERE empName = ?";
					PreparedStatement checkEmployeeStmt = conn.prepareStatement(checkEmployeeSQL);
					checkEmployeeStmt.setString(1, username);
					ResultSet checkEmployeeRs = checkEmployeeStmt.executeQuery();

					if (!checkAdminRs.next() && !checkEmployeeRs.next()) {
						lblMessage.setText("Invalid username and password!");
					} else {
						lblMessage.setText("Invalid username and password!!");
					}

				} catch (SQLException ex) {
					ex.printStackTrace();
					lblMessage.setText("Database connection error!");
				}
			}
		});

		btnLogin.setBounds(268, 354, 91, 28);
		contentPane.add(btnLogin);

		lblMessage = new JLabel("");
		lblMessage.setBounds(226, 304, 372, 28);
		contentPane.add(lblMessage);

		txtUserName.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				lblMessage.setText("");
			}
		});

		txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				lblMessage.setText("");
			}
		});
	}
}