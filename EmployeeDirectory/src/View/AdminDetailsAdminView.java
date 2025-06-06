package View;

import javax.swing.*;

import Config.Checking;
import Config.MySqlQueries;
import Controller.AdminDetailsController;
import Model.AdminModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AdminDetailsAdminView extends JPanel {

	private static final long serialVersionUID = 2L;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhoneNumber;
	private JTextField txtJobTitle;
	private JDateChooser dateChooser;
	private AdminNavBar parentFrame;
	private JCheckBox chkIsActive, chkIsAgreement, chkIsManager;
	private JComboBox cboDepartment;
	private AdminModel currentModel;

	public AdminDetailsAdminView(AdminNavBar parentFrame) {
		this.parentFrame = parentFrame;
		setLayout(null);

		JLabel lblHeader = new JLabel("Admin Details");
		lblHeader.setBounds(60, 10, 300, 30);
		lblHeader.setFont(new Font("Arial", Font.BOLD, 18));
		add(lblHeader);

		setPreferredSize(new Dimension(669, 686));
		setBackground(new Color(245, 245, 245));

		JPanel panel = new JPanel();
		panel.setBounds(58, 43, 567, 594);
		add(panel);
		panel.setLayout(null);

		JLabel lblName = new JLabel("<html>Name <span style='color:red;'>*</span></html>");
		lblName.setFont(new Font("Arial", Font.PLAIN, 14));
		lblName.setBounds(31, 10, 150, 25);
		panel.add(lblName);

		txtName = new JTextField();
		txtName.setBounds(31, 35, 508, 25);
		txtName.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(txtName);
		txtName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char typedChar = e.getKeyChar();
				if (Character.isDigit(typedChar)) {
					e.consume();
				}
			}
		});

		JLabel lblPhoneNumber = new JLabel("<html>Phone Number <span style='color:red;'>*</span></html>");
		lblPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPhoneNumber.setBounds(31, 70, 150, 31);
		panel.add(lblPhoneNumber);

		txtPhoneNumber = new JTextField();
		txtPhoneNumber.setBounds(31, 100, 508, 25);
		txtPhoneNumber.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(txtPhoneNumber);
		txtPhoneNumber.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String input = txtPhoneNumber.getText();
				String phoneRegex = "^09\\d{9}$";
				if (input.length() > 11) {
					txtPhoneNumber.setText(input.substring(0, 11));
				}
				if (!input.matches(phoneRegex)) {
					txtPhoneNumber.setBorder(BorderFactory.createLineBorder(Color.red));
				} else {
					txtPhoneNumber.setBorder(UIManager.getBorder("TextField.border"));
				}
			}
		});

		JLabel lblEmail = new JLabel("<html>Email <span style='color:red;'>*</span></html>");
		lblEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		lblEmail.setBounds(31, 135, 150, 31);
		panel.add(lblEmail);

		txtEmail = new JTextField();
		txtEmail.setBounds(31, 165, 508, 25);
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
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

		JLabel lblHiringDate = new JLabel("<html>Hiring Date <span style='color:red;'>*</span></html>");
		lblHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
		lblHiringDate.setBounds(31, 200, 150, 31);
		panel.add(lblHiringDate);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(31, 230, 508, 25);
		dateChooser.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(dateChooser);

		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		dateChooser.setMaxSelectableDate(currentDate);

		JLabel lblIsManager = new JLabel("Is Manager");
		lblIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		lblIsManager.setBounds(31, 265, 100, 25);
		panel.add(lblIsManager);

		chkIsManager = new JCheckBox();
		chkIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsManager.setBounds(31, 291, 25, 25);
		panel.add(chkIsManager);
		chkIsManager.setBackground(Color.WHITE);
		chkIsManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkIsManager.isSelected()) {
					txtJobTitle.setText("Manager");
				}
			}
		});

		JLabel lblIsActive = new JLabel("Is Active");
		lblIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		lblIsActive.setBounds(31, 316, 73, 31);
		panel.add(lblIsActive);

		chkIsActive = new JCheckBox();
		chkIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsActive.setBounds(31, 342, 25, 25);
		panel.add(chkIsActive);
		chkIsActive.setBackground(Color.WHITE);

		JLabel lblIsAgreement = new JLabel("Is Agreement");
		lblIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		lblIsAgreement.setBounds(31, 372, 100, 25);
		panel.add(lblIsAgreement);

		chkIsAgreement = new JCheckBox();
		chkIsAgreement.setBounds(31, 398, 25, 25);
		txtName.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(chkIsAgreement);
		chkIsAgreement.setBackground(Color.WHITE);

		JLabel lblDepartment = new JLabel("Department");
		lblDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		lblDepartment.setBounds(31, 429, 150, 31);
		panel.add(lblDepartment);

		cboDepartment = new JComboBox();
		cboDepartment.setBackground(new Color(255, 255, 255));
		cboDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		cboDepartment.setBounds(31, 459, 508, 25);
		panel.add(cboDepartment);
		fillCombo();

		JLabel lblJobTitle = new JLabel("Job Title");
		lblJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		lblJobTitle.setBounds(31, 494, 150, 25);
		panel.add(lblJobTitle);

		txtJobTitle = new JTextField();
		txtJobTitle.setBounds(31, 518, 508, 25);
		txtJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(txtJobTitle);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setBackground(Color.RED);
		btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSave.setBounds(31, 563, 73, 24);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!validateFields()) {
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(AdminDetailsAdminView.this,
						"Do you want to save this employee?", "Confirm Save", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					saveAdminDetails();
				}
			}
		});
		panel.add(btnSave);

		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setBackground(Color.RED);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
		btnBack.setBounds(119, 563, 73, 24);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "AdminDashboard");
				parentFrame.setActiveNav("Admin Dashboard");
			}
		});
		panel.add(btnBack);
	}

	public void fillCombo() {
		MySqlQueries.addCoboBox("department", "depName", cboDepartment);

	}

	public void loadAdminData(AdminModel employee) {
		this.currentModel = employee;
		txtName.setText(employee.getName());
		txtEmail.setText(employee.getEmail());
		txtPhoneNumber.setText(employee.getPhone());
		String hiringDateStr = employee.getHiringDate();
		String department = employee.getDepartment();
		if (hiringDateStr != null && !hiringDateStr.isEmpty()) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date hiringDate = sdf.parse(hiringDateStr);
				dateChooser.setDate(hiringDate);
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Invalid hiring date format", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			dateChooser.setDate(null);
		}
		chkIsActive.setSelected(employee.isActive());
		chkIsAgreement.setSelected(employee.isAgreement());
		chkIsManager.setSelected(employee.isManager());
		cboDepartment.setSelectedItem(department);
		txtJobTitle.setText(employee.getJobTitle());
	}

	private boolean validateFields() {
		String name = txtName.getText().trim();
		String phone = txtPhoneNumber.getText().trim();
		String email = txtEmail.getText().trim();
		String department = (String) cboDepartment.getSelectedItem();
		String jobTitle = txtJobTitle.getText().trim();

		if (name.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Name is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			txtName.requestFocus();
			return false;
		}
		if (!Checking.IsValidName(name)) {
			JOptionPane.showMessageDialog(this, "Numbers and special characters are not allowed.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			txtName.requestFocus();
			return false;
		}

		if (phone.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Phone number is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			txtPhoneNumber.requestFocus();
			return false;
		}

		if (!phone.matches("^09\\d{9}$")) {
			JOptionPane.showMessageDialog(this, "Phone number is required. Must start with 09 and be 11 digits.",
					"Validation Failed", JOptionPane.INFORMATION_MESSAGE);
			txtPhoneNumber.requestFocus();
			return false;
		}

		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Email is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			txtEmail.requestFocus();
			return false;
		}

		if (!Checking.IsEmailFormat(email)) {
			JOptionPane.showMessageDialog(this, "Email Format is wronged.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			txtEmail.requestFocus();
			return false;
		}
		if (dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Hiring Date is required.", "Validation Failed ",
					JOptionPane.INFORMATION_MESSAGE);
			dateChooser.requestFocus();
			return false;
		}

		if (jobTitle.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Job title is required.", "Validation Failed ",
					JOptionPane.INFORMATION_MESSAGE);
			txtJobTitle.requestFocus();
			return false;
		}

		if (!jobTitle.equalsIgnoreCase("Manager")) {
			JOptionPane.showMessageDialog(this, "Job title must be 'Manager'.", "Validation Failed ",
					JOptionPane.INFORMATION_MESSAGE);
			txtJobTitle.requestFocus();
			return false;
		}

		if (!chkIsActive.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please check the 'Is Active' checkbox.", "Validation Failed ",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (!chkIsManager.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please check the 'Is Manager' checkbox.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (!chkIsAgreement.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please check the 'Is Agreement' checkbox.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (department.isEmpty() || !Checking.IsValidName(department)) {
			JOptionPane.showMessageDialog(this, "Department is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			cboDepartment.requestFocus();
			return false;
		}

		return true;
	}

	private void saveAdminDetails() {
		if (!validateFields()) {
			return;
		}

		if (currentModel == null) {
			JOptionPane.showMessageDialog(this, "No employee data loaded.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (txtName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPhoneNumber.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Name, Email, and Phone are required.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		Date hiringDate = dateChooser.getDate();
		if (hiringDate == null) {
			JOptionPane.showMessageDialog(this, "Hiring date is required.", "Validation Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String hiringDateStr = dateFormat.format(hiringDate);

		currentModel.setName(txtName.getText());
		currentModel.setEmail(txtEmail.getText());
		currentModel.setPhone(txtPhoneNumber.getText());
		currentModel.setHiringDate(hiringDateStr);
		currentModel.setActive(chkIsActive.isSelected());
		currentModel.setAgreement(chkIsAgreement.isSelected());
		currentModel.setManager(chkIsManager.isSelected());
		currentModel.setDepartment((String) cboDepartment.getSelectedItem());
		currentModel.setJobTitle(txtJobTitle.getText());

		AdminDetailsController controller = new AdminDetailsController(currentModel);
		boolean isSaved = controller.saveAdminDetails();

		if (isSaved) {
			JOptionPane.showMessageDialog(this, "Admin data saved successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			parentFrame.refreshAdminDashboard();
			switchToAdminDashboard();

		} else {
			JOptionPane.showMessageDialog(this, "Failed to save Admin data.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void switchToAdminDashboard() {
		CardLayout cardLayout = (CardLayout) parentFrame.getContentPanel().getLayout();
		cardLayout.show(parentFrame.getContentPanel(), "AdminDashboard");
	}

	public void loadAdminDetail(String name) {
		AdminDetailsController controller = new AdminDetailsController(null);
		AdminModel model = controller.fetchAdminByName(name);
		if (model != null) {
			currentModel = model;
			txtName.setText(model.getName());
			txtEmail.setText(model.getEmail());
			txtPhoneNumber.setText(model.getPhone());

			if (dateChooser != null) {

				String hiringDateStr = model.getHiringDate();
				if (hiringDateStr != null && !hiringDateStr.isEmpty()) {
					try {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						Date hiringDate = dateFormat.parse(hiringDateStr);
						dateChooser.setDate(hiringDate);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(this, "Error parsing the hiring date.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Date chooser is not initialized.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

			chkIsActive.setSelected(model.isActive());
			chkIsAgreement.setSelected(model.isAgreement());
			chkIsManager.setSelected(model.isManager());
			cboDepartment.setSelectedItem(model.getDepartment());
			txtJobTitle.setText(model.getJobTitle());
		} else {
			JOptionPane.showMessageDialog(this, "Admin not found!");
		}
	}
}
