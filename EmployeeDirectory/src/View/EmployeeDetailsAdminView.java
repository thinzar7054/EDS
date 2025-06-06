package View;

import javax.swing.*;

import Config.Checking;
import Config.MySqlQueries;

import Controller.EmployeeDetailsController;
import Model.EmployeeDetailsModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployeeDetailsAdminView extends JPanel {

	private final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private AdminNavBar parentFrame;
	private String previousJobTitle = "";
	private JComboBox cboDepartment;
	private JComboBox cboJobTitle;
	private JCheckBox chkIsActive, chkIsAgreement, chkIsManager;
	private EmployeeDetailsModel currentModel;
	private JDateChooser dateChooser;

	public EmployeeDetailsAdminView(AdminNavBar parentFrame) {
		this.parentFrame = parentFrame;
		setLayout(null);

		JLabel lblHeader = new JLabel("Employee Details");
		lblHeader.setBounds(56, 10, 300, 30);
		lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
		add(lblHeader);

		setPreferredSize(new Dimension(669, 686));
		setBackground(new Color(245, 245, 245));

		JPanel panel = new JPanel();
		panel.setBounds(58, 43, 567, 594);
		add(panel);
		panel.setLayout(null);

		JLabel lbName = new JLabel("<html>Name <span style='color:red;'>*</span></html>");
		lbName.setBounds(31, 10, 73, 20);
		lbName.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbName);

		txtName = new JTextField();
		txtName.setFont(new Font("Arial", Font.PLAIN, 14));
		txtName.setBounds(31, 35, 508, 25);
		panel.add(txtName);
		txtName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char typedChar = e.getKeyChar();

				if (!Character.isLetter(typedChar) && typedChar != ' ') {
					e.consume();
				}
			}
		});

		JLabel lbPhone = new JLabel("<html>Phone Number <span style='color:red;'>*</span></html>");
		lbPhone.setBounds(31, 70, 135, 20);
		lbPhone.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbPhone);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPhone.setBounds(31, 100, 508, 25);
		panel.add(txtPhone);
		txtPhone.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				String phoneInput = txtPhone.getText();

				if (!Checking.isPhoneNo(phoneInput) || phoneInput.length() != 11) {

					txtPhone.setBorder(BorderFactory.createLineBorder(Color.RED));
				} else {

					txtPhone.setBorder(UIManager.getBorder("TextField.border"));
				}
			}
		});

		JLabel lbEmail = new JLabel("<html>Email <span style='color:red;'>*</span></html>");
		lbEmail.setBounds(31, 135, 73, 20);
		lbEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		txtEmail.setBounds(31, 165, 508, 25);

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

		JLabel lbHiringDate = new JLabel("<html>Hiring Date <span style='color:red;'>*</span></html>");
		lbHiringDate.setBounds(31, 200, 100, 20);
		lbHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbHiringDate);

		chkIsActive = new JCheckBox();
		chkIsActive.setBounds(31, 341, 25, 25);
		chkIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(chkIsActive);
		chkIsActive.setBackground(Color.WHITE);

		chkIsAgreement = new JCheckBox();
		chkIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsAgreement.setBounds(31, 398, 25, 25);
		panel.add(chkIsAgreement);
		chkIsAgreement.setBackground(Color.WHITE);

		chkIsManager = new JCheckBox();
		chkIsManager.setBounds(31, 291, 25, 25);
		chkIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(chkIsManager);
		chkIsManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkIsManager.isSelected()) {

					cboJobTitle.setSelectedItem("Manager");
				} else {

					cboJobTitle.setSelectedItem("-Select-");
				}
			}
		});

		chkIsManager.setBackground(Color.WHITE);

		JLabel lbDepartment = new JLabel("Department");
		lbDepartment.setBounds(31, 429, 100, 20);
		lbDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbDepartment);

		JLabel lbJobTitle = new JLabel("Job Title");
		lbJobTitle.setBounds(31, 494, 100, 20);
		lbJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbJobTitle);

		JButton btnSave = new JButton("Save");
		btnSave.setForeground(new Color(255, 255, 255));
		btnSave.setBounds(31, 563, 73, 24);
		btnSave.setBackground(Color.RED);
		btnSave.setFont(new Font("Arial", Font.PLAIN, 14));

		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (!validateFields()) {
					return;
				}

				int confirm = JOptionPane.showConfirmDialog(EmployeeDetailsAdminView.this,
						"Do you want to save this employee?", "Confirm Save", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					saveEmployeeDetails();
				}
			}
		});
		panel.add(btnSave);

		JLabel lbIsManager = new JLabel("Is Manager");
		lbIsManager.setBounds(31, 265, 100, 20);
		lbIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbIsManager);

		JLabel lbIsActive = new JLabel("Is Active");
		lbIsActive.setBounds(31, 322, 73, 13);
		lbIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbIsActive);

		JLabel lbIsAgreement = new JLabel("Is Agreement");
		lbIsAgreement.setBounds(31, 372, 100, 20);
		lbIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lbIsAgreement);

		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setBounds(116, 563, 73, 24);
		btnBack.setBackground(Color.RED);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.refreshEmployeeDashboard();
				parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
				parentFrame.setActiveNav("Employee Dashboard");
			}
		});
		panel.add(btnBack);

		dateChooser = new JDateChooser();
		dateChooser.setBounds(29, 230, 510, 25);
		panel.add(dateChooser);
		Calendar calendar = Calendar.getInstance();
		Date currentDate = calendar.getTime();
		dateChooser.setMaxSelectableDate(currentDate);

		cboDepartment = new JComboBox();
		cboDepartment.setBackground(new Color(255, 255, 255));
		cboDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		cboDepartment.setBounds(31, 459, 508, 25);
		panel.add(cboDepartment);

		cboJobTitle = new JComboBox();
		cboJobTitle.setBackground(new Color(255, 255, 255));
		cboJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		cboJobTitle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (cboJobTitle.getSelectedItem() != null && cboJobTitle.getSelectedItem().equals("Manager")) {
					chkIsManager.setSelected(true);
				} else {
					chkIsManager.setSelected(false);
				}
			}
		});
		cboJobTitle.setBounds(31, 520, 508, 25);
		panel.add(cboJobTitle);
		fillCombo();

	}

	public void loadEmployeeData(EmployeeDetailsModel employee) {
		this.currentModel = employee;
		txtName.setText(employee.getEmpName());
		txtEmail.setText(employee.getEmail());
		txtPhone.setText(employee.getPhone());

		String hiringDateStr = employee.getHiringDate();
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

		String employeeDepartment = employee.getDepartment();
		String employeeJobTitle = employee.getJobTitle();

		cboDepartment.setSelectedItem(employeeDepartment);
		cboJobTitle.setSelectedItem(employeeJobTitle);
	}

	public void onEmployeeNameClicked(String employeeName) {
		EmployeeDetailsController controller = new EmployeeDetailsController(null);

		EmployeeDetailsModel employee = controller.fetchEmployeeByName(employeeName);

		if (employee != null) {

			loadEmployeeData(employee);
		} else {
			JOptionPane.showMessageDialog(this, "Employee not found!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private boolean validateFields() {
		String name = txtName.getText().trim();
		String phone = txtPhone.getText().trim();
		String email = txtEmail.getText().trim();
		String department = (String) cboDepartment.getSelectedItem();
		String job = (String) cboJobTitle.getSelectedItem();

		if (name.isEmpty() || !name.matches("[a-zA-Z ]+")) {
			JOptionPane.showMessageDialog(this, "Name is required and must not contain digits or special characters.",
					"Validation Failed", JOptionPane.INFORMATION_MESSAGE);
			txtName.requestFocus();
			return false;
		}
		if (phone.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Phone number is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			txtPhone.requestFocus();
			return false;
		} else if (!phone.matches("^09\\d{9}$")) {
			JOptionPane.showMessageDialog(this, "Only 11 numbers are allowed. Must start with 09 and be 11 digits.",
					"Validation Failed", JOptionPane.INFORMATION_MESSAGE);
			txtPhone.requestFocus();
			return false;
		}

		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Email is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			txtEmail.requestFocus();
			return false;
		} else if (!Checking.IsEmailFormat(email)) {
			JOptionPane.showMessageDialog(this, "Email format is wrong.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			txtEmail.requestFocus();
			return false;
		}

		if (dateChooser.getDate() == null) {
			JOptionPane.showMessageDialog(this, "Hiring Date is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			dateChooser.requestFocus();
			return false;
		}
		if (department.isEmpty() || !Checking.IsValidName(department)) {
			JOptionPane.showMessageDialog(this, "Department is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			cboDepartment.requestFocus();
			return false;
		}

		if (job.isEmpty() || !Checking.IsValidName(job)) {
			JOptionPane.showMessageDialog(this, "Job Title is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			cboJobTitle.requestFocus();
			return false;
		}

		return true;
	}

	private void saveEmployeeDetails() {
		if (!validateFields()) {
			return;
		}

		if (currentModel == null) {
			JOptionPane.showMessageDialog(this, "No employee data loaded.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (txtName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtPhone.getText().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Name, Email, and Phone are required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (currentModel == null) {
			JOptionPane.showMessageDialog(this, "No employee data loaded.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		Date hiringDate = dateChooser.getDate();
		if (hiringDate == null) {
			JOptionPane.showMessageDialog(this, "Hiring date is required.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String hiringDateStr = dateFormat.format(hiringDate);

		currentModel.setEmpName(txtName.getText());
		currentModel.setEmail(txtEmail.getText());
		currentModel.setPhone(txtPhone.getText());
		currentModel.setHiringDate(hiringDateStr);
		currentModel.setActive(chkIsActive.isSelected());
		currentModel.setAgreement(chkIsAgreement.isSelected());
		currentModel.setManager(chkIsManager.isSelected());
		currentModel.setDepartment((String) cboDepartment.getSelectedItem());
		currentModel.setJobTitle((String) cboJobTitle.getSelectedItem());

		EmployeeDetailsController controller = new EmployeeDetailsController(currentModel);
		boolean isSaved = controller.saveAdminDetails();

		if (isSaved) {
			JOptionPane.showMessageDialog(this, "Employee data saved successfully!", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			parentFrame.refreshEmployeeDashboard();

			parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
			parentFrame.setActiveNav("Employee Dashboard");

		} else {
			JOptionPane.showMessageDialog(this, "Failed to save employee data.", "Error",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void loadAdminDetail(String name) {
		EmployeeDetailsController controller = new EmployeeDetailsController(null);
		EmployeeDetailsModel model = controller.fetchAdminByName(name);
		if (model != null) {
			currentModel = model;
			txtName.setText(model.getEmpName());
			txtEmail.setText(model.getEmail());
			txtPhone.setText(model.getPhone());
			previousJobTitle = model.getJobTitle();

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
			cboDepartment.setToolTipText(model.getDepartment());

			cboJobTitle.setToolTipText(previousJobTitle);
			if (chkIsManager.isSelected()) {
				cboJobTitle.setEditable(false);
			}
		} else {
			JOptionPane.showMessageDialog(this, "Employee not found!");
		}

	}

	public void fillCombo() {
		MySqlQueries.addCoboBox("department", "depName", cboDepartment);
		MySqlQueries.addCoboBox("position", "postName", cboJobTitle);
	}
}
