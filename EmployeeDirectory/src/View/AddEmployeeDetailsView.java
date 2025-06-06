package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import com.toedter.calendar.JDateChooser;

import Config.Checking;
import Config.MySqlQueries;
import Controller.AddEmployeeDetailsController;
import Controller.AddEmployeeDetailsController;
import Model.AddEmployeeDetailsModel;

public class AddEmployeeDetailsView extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean passwordVisible = false;
	private static JTextField txtName;
	private static JTextField txtPhoneNumber;
	private static JTextField txtEmail;
	private JPasswordField txtPassword;
	private JComboBox<String> cboDepartment;
	private JComboBox<String> cboJobTitle;

	private JCheckBox chkIsActive;
	private JCheckBox chkIsAgreement;
	private JCheckBox chkIsManager;
	private JDateChooser dateChooser;
	private AdminNavBar parentFrame;
	private String employeeId;
	private JLabel lblEyeIcon;
	private boolean isPasswordVisible = false;
	private JPanel passwordPanel;
	private JButton btnShowHidePassword;

	public AddEmployeeDetailsView(AdminNavBar parentFrame) {
		this.parentFrame = parentFrame;
		setLayout(null);

		JLabel lblHeader = new JLabel("Add Employee Details");
		lblHeader.setBounds(68, 11, 300, 30);
		lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
		add(lblHeader);

		setPreferredSize(new Dimension(1000, 753));
		setBackground(new Color(245, 245, 245));

		JPanel panel = new JPanel();
		panel.setBounds(68, 54, 657, 611);
		add(panel);
		panel.setLayout(null);

		employeeId = generateNextId();
		System.out.println("Generated Employee ID: " + employeeId); // debug

		JLabel lblName = new JLabel("<html>Name <span style='color:red;'>*</span></html>");
		lblName.setFont(new Font("Arial", Font.PLAIN, 14));
		lblName.setBounds(10, 10, 54, 13);
		panel.add(lblName);

		txtName = new JTextField();
		txtName.setFont(new Font("Arial", Font.PLAIN, 14));
		txtName.setBounds(10, 29, 590, 20);
		panel.add(txtName);
		txtName.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String input = txtName.getText();
				if (!input.matches("[a-zA-Z\\s]*")) {
					txtName.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					txtName.setBorder(UIManager.getBorder("TextField.border"));
				}
			}
		});

		JLabel lblEmail = new JLabel("<html>Email <span style='color:red;'>*</span></html>");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		lblEmail.setBounds(10, 112, 100, 20);
		panel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		txtEmail.setBounds(10, 138, 590, 20);
		panel.add(txtEmail);

		txtEmail.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String emailInput = txtEmail.getText();
				if (!Checking.IsEmailFormat(emailInput)) {
					txtEmail.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					txtEmail.setBorder(UIManager.getBorder("TextField.border"));
				}
			}
		});

		JLabel lblPhone = new JLabel("<html>Phone Number <span style='color:red;'>*</span></html>");
		lblPhone.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPhone.setBounds(10, 60, 127, 20);
		panel.add(lblPhone);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPhoneNumber.setBounds(10, 91, 590, 20);
		panel.add(txtPhoneNumber);

		txtPhoneNumber.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String input = txtPhoneNumber.getText();
				String phoneRegex = "^09\\d{9}$";
				if (!input.matches(phoneRegex)) {
					txtPhoneNumber.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {
					txtPhoneNumber.setBorder(UIManager.getBorder("TextField.border"));
				}
			}
		});

		JLabel lblHiringDate = new JLabel("<html>Hiring Date <span style='color:red;'>*</span></html>");
		lblHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
		lblHiringDate.setBounds(10, 159, 100, 20);
		panel.add(lblHiringDate);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(10, 189, 590, 20);
		panel.add(dateChooser);

		Calendar calendar = Calendar.getInstance();
		dateChooser.setMaxSelectableDate(calendar.getTime());

		JLabel lblIsActive = new JLabel("Is Active");
		lblIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		lblIsActive.setBounds(10, 273, 100, 20);
		panel.add(lblIsActive);

		chkIsActive = new JCheckBox();
		chkIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsActive.setBounds(10, 300, 25, 25);
		panel.add(chkIsActive);
		chkIsActive.setBackground(Color.WHITE);

		JLabel lblIsAgreement = new JLabel("Is Agreement");
		lblIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		lblIsAgreement.setBounds(10, 322, 100, 20);
		panel.add(lblIsAgreement);

		chkIsAgreement = new JCheckBox();
		chkIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsAgreement.setBounds(10, 347, 25, 25);
		panel.add(chkIsAgreement);
		chkIsAgreement.setBackground(Color.WHITE);

		JLabel lblIsManager = new JLabel("Is Manager");
		lblIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		lblIsManager.setBounds(10, 220, 100, 20);
		panel.add(lblIsManager);

		chkIsManager = new JCheckBox();
		chkIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsManager.setBounds(10, 241, 25, 25);
		panel.add(chkIsManager);
		chkIsManager.setBackground(Color.WHITE);
		chkIsManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkIsManager.isSelected()) {
					// When the "Is Manager" checkbox is selected, set the ComboBox to "Manager"
					cboJobTitle.setSelectedItem("Manager");
				} else {
					// When the "Is Manager" checkbox is deselected, clear the ComboBox or set to
					// default
					cboJobTitle.setSelectedItem(null); // or set to a default value like ""
				}
			}
		});

		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDepartment.setBounds(10, 371, 100, 20);
		panel.add(lblDepartment);

		cboDepartment = new JComboBox<>();
		cboDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		cboDepartment.setBackground(new Color(255, 255, 255));
		cboDepartment.setBounds(10, 397, 590, 20);
		panel.add(cboDepartment);

		JLabel lblJobTitle = new JLabel("Job Title");
		lblJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		lblJobTitle.setBounds(10, 427, 100, 20);
		panel.add(lblJobTitle);

		cboJobTitle = new JComboBox<>();
		cboJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		cboJobTitle.setBackground(new Color(255, 255, 255));
		cboJobTitle.setBounds(10, 452, 590, 20);
		panel.add(cboJobTitle);

		cboJobTitle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Check if the selected item is "Manager"
				if (cboJobTitle.getSelectedItem() != null && cboJobTitle.getSelectedItem().equals("Manager")) {
					chkIsManager.setSelected(true);
				} else {
					chkIsManager.setSelected(false);
				}
			}
		});

		JLabel lblPassword = new JLabel("<html>Password <span style='color:red;'>*</span></html>");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPassword.setBounds(10, 482, 100, 20);
		panel.add(lblPassword);

		passwordPanel = new JPanel();
		passwordPanel.setBounds(10, 510, 590, 20); // Adjusting position to make space for the icon
		passwordPanel.setLayout(new BorderLayout(0, 0));
		passwordPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPassword.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Remove border for a cleaner look
		passwordPanel.add(txtPassword, BorderLayout.CENTER);

		btnShowHidePassword = new JButton();

		ImageIcon eyeIconClosed = new ImageIcon("C:\\image\\eye_close.png");
		Image imgClosed = eyeIconClosed.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		btnShowHidePassword.setIcon(new ImageIcon(imgClosed));

		btnShowHidePassword.setPreferredSize(new Dimension(30, 30));
		btnShowHidePassword.setContentAreaFilled(false);
		btnShowHidePassword.setBorderPainted(false);
		btnShowHidePassword.setOpaque(false);

		passwordPanel.add(btnShowHidePassword, BorderLayout.EAST);
		panel.add(passwordPanel);

		btnShowHidePassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (passwordVisible) {
					txtPassword.setEchoChar('*');
					passwordVisible = false;

					ImageIcon closedEyeIcon = new ImageIcon("C:\\image\\eye_close.png");
					Image imgClosed = closedEyeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					closedEyeIcon = new ImageIcon(imgClosed);
					btnShowHidePassword.setIcon(closedEyeIcon);
				} else {
					txtPassword.setEchoChar((char) 0);
					passwordVisible = true;

					ImageIcon openEyeIcon = new ImageIcon("C:\\image\\eye.png");
					Image imgOpen = openEyeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					openEyeIcon = new ImageIcon(imgOpen);
					btnShowHidePassword.setIcon(openEyeIcon);
				}
			}
		});

		txtPassword.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent e) {
				btnShowHidePassword.setVisible(true);
			}
		});

		JButton btnSave = new JButton("Save");
		btnSave.setBackground(new Color(255, 0, 0));
		btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSave.setBounds(10, 555, 79, 25);
		panel.add(btnSave);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
		btnBack.setBackground(new Color(255, 0, 0));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.refreshEmployeeDashboard(); // Refresh before showing
				parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
				parentFrame.setActiveNav("Employee Dashboard");
			}
		});
		btnBack.setBounds(105, 555, 79, 25);
		panel.add(btnBack);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				AddEmployeeDetailsModel pm = new AddEmployeeDetailsModel();
				AddEmployeeDetailsController pc = new AddEmployeeDetailsController();

				if (!validateFields())
					return;

				pm.setEmp_id(employeeId);
				pm.setEmpName(txtName.getText().trim());
				pm.setEmail(txtEmail.getText().trim());
				pm.setPhone(txtPhoneNumber.getText().trim());
				pm.setHiringDate(new java.sql.Date(dateChooser.getDate().getTime()));
				pm.setDep_id((String) cboDepartment.getSelectedItem());
				pm.setPos_id((String) cboJobTitle.getSelectedItem());
				pm.setManager(chkIsManager.isSelected());
				pm.setActive(chkIsActive.isSelected());
				pm.setAgreement(chkIsAgreement.isSelected());
				pm.setPassword(txtPassword.getText().trim());

				if (!Checking.IsValidName(pm.getEmpName()) || !Checking.IsEmailFormat(pm.getEmail())
						|| !Checking.isPhoneNo(pm.getPhone())) {
					JOptionPane.showMessageDialog(null, "Invalid related field", "Invalid", JOptionPane.ERROR_MESSAGE);
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(null, "Do you want to save this employee?", "Confirm Save",
						JOptionPane.YES_NO_OPTION);

				if (confirm != JOptionPane.YES_OPTION) {
					Clear();
					parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
					parentFrame.setActiveNav("Employee Dashboard");
					return;
				}
				try {
					int rs = pc.insert(pm);
					if (rs == 1) {
						JOptionPane.showMessageDialog(null, "Save Successfully", "Successfully",
								JOptionPane.INFORMATION_MESSAGE);
						employeeId = generateNextId();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
				parentFrame.setActiveNav("Employee Dashboard");
				Clear();
			}
		});
		populateComboBoxes();
	}

	private void populateComboBoxes() {
		try {
			AddEmployeeDetailsController pc = new AddEmployeeDetailsController();

			cboDepartment.removeAllItems();
			cboJobTitle.removeAllItems();

			cboDepartment.addItem("Select Department");
			cboJobTitle.addItem("Select Job Title");

			List<String> departments = pc.getDepartments();
			for (String dept : departments) {
				cboDepartment.addItem(dept);
			}

			List<String> jobTitles = pc.getJobTitles();
			for (String job : jobTitles) {
				cboJobTitle.addItem(job);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error fetching combobox data: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fillCombo() {
		MySqlQueries.addCoboBox("department", "depName", cboDepartment);
		MySqlQueries.addCoboBox("position", "postName", cboJobTitle);
	}

	private String generateNextId() {
		try {
			AddEmployeeDetailsController pc = new AddEmployeeDetailsController();
			List<String> existingIds = pc.getAllEmployeeIds();

			int maxNum = 0;
			for (String id : existingIds) {
				if (id != null && id.startsWith("EP-")) {
					String numericPart = id.substring(3);
					if (numericPart.matches("\\d+")) {
						int num = Integer.parseInt(numericPart);
						if (num > maxNum) {
							maxNum = num;
						}
					}
				}
			}
			int nextNum = maxNum + 1;
			return String.format("EP-%04d", nextNum);
		} catch (Exception e) {
			e.printStackTrace();
			return "EP-0001";
		}
	}

	public void Clear() {
		txtName.setText(" ");
		txtPhoneNumber.setText(" ");
		txtEmail.setText(" ");

		dateChooser.setDate(null);
		chkIsManager.setSelected(false);
		chkIsActive.setSelected(false);
		chkIsAgreement.setSelected(false);
		cboDepartment.setSelectedIndex(-1);
		cboJobTitle.setSelectedIndex(-1);
		txtPassword.setText("");

	}

	private boolean validateFields() {
		if (txtName.getText().trim().isEmpty()) {
			showMessage("Name is blank.");
			txtName.requestFocus();
			return false;
		} else if (!Checking.IsValidName(txtName.getText())) {
			showMessage("Please enter a valid name.");
			txtName.requestFocus();
			return false;
		}

		if (txtPhoneNumber.getText().trim().isEmpty()) {
			showMessage("Phone number is blank.");
			txtPhoneNumber.requestFocus();
			return false;
		} else if (!Checking.isPhoneNo(txtPhoneNumber.getText())) {
			showMessage("Invalid phone number. Must be digits and 11 characters.");
			txtPhoneNumber.requestFocus();
			return false;
		}
		if (txtEmail.getText().trim().isEmpty()) {
			showMessage("Email is blank.");
			txtEmail.requestFocus();
			return false;
		} else if (!Checking.IsEmailFormat(txtEmail.getText())) {
			showMessage("Invalid email address.");
			txtEmail.requestFocus();
			return false;
		}

		if (dateChooser.getDate() == null) {
			showMessage("Hiring date is required.");
			dateChooser.requestFocus();
			return false;
		}

		if (cboDepartment.getSelectedItem() == null
				|| cboDepartment.getSelectedItem().toString().equals("Select Department")) {
			showMessage("Please select a department.");
			cboDepartment.requestFocus();
			return false;
		}

		if (cboJobTitle.getSelectedItem() == null
				|| cboJobTitle.getSelectedItem().toString().equals("Select Job Title")) {
			showMessage("Please select a job title.");
			cboJobTitle.requestFocus();
			return false;
		}
		if (txtPassword.getText().trim().isEmpty()) {
			showMessage("Password is blank.");
			txtPassword.requestFocus();
			return false;
		}
		return true;
	}

	private void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Validation", JOptionPane.WARNING_MESSAGE);
	}
}
