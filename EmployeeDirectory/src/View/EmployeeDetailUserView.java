package View;

import javax.swing.*;

import Config.Checking;
//import Controller.AdminDetailController;
import Controller.EmployeeDetailsController;
import Model.EmployeeDetailsModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class EmployeeDetailUserView extends JPanel {

	private final long serialVersionUID = 1L;
	private JTextField txtName;
	private JTextField txtEmail;
	private JTextField txtPhone;
	private JTextField txtHiringDate;
	private JTextField txtDepartment;
	private JTextField txtJobTitle;
	private EmployeeNavBar parentFrame;
	private JCheckBox chkIsActive, chkIsAgreement, chkIsManager;
	private EmployeeDetailsModel currentModel;

	public EmployeeDetailUserView(EmployeeNavBar parentFrame) {
		this.parentFrame = parentFrame;
		setLayout(null);

		JLabel lblHeader = new JLabel("Employee Details");
		lblHeader.setBounds(38, 10, 300, 30);
		lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
		add(lblHeader);

		setPreferredSize(new Dimension(669, 686));
		setBackground(new Color(245, 245, 245));

		JPanel panel = new JPanel();
		panel.setBounds(58, 43, 567, 594);
		add(panel);
		panel.setLayout(null);

		JLabel lbName = new JLabel("Name");
		lbName.setFont(new Font("Arial", Font.PLAIN, 14));
		lbName.setBounds(31, 10, 73, 20);
		panel.add(lbName);

		txtName = new JTextField();
		txtName.setFont(new Font("Arial", Font.PLAIN, 14));
		txtName.setBackground(Color.WHITE);
		txtName.setBounds(31, 35, 508, 25);
		panel.add(txtName);
		txtName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {

				char typedChar = e.getKeyChar();

				if (Character.isDigit(typedChar)) {
					// Consume the event to prevent the digit from being entered
					e.consume();
				}
			}
		});

		JLabel lbPhone = new JLabel("Phone Number");
		lbPhone.setFont(new Font("Arial", Font.PLAIN, 14));
		lbPhone.setBounds(31, 70, 100, 20);
		panel.add(lbPhone);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPhone.setBackground(Color.WHITE);
		txtPhone.setBounds(31, 100, 508, 25);
		panel.add(txtPhone);

		JLabel lbEmail = new JLabel("<html>Email <span style='color:red;'>*</span></html>");
		lbEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		lbEmail.setBounds(31, 135, 73, 20);
		panel.add(lbEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		txtEmail.setBackground(Color.WHITE);
		txtEmail.setBounds(31, 165, 508, 25);
		panel.add(txtEmail);
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

		JLabel lbHiringDate = new JLabel("Hiring Date");
		lbHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
		lbHiringDate.setBounds(31, 200, 100, 20);
		panel.add(lbHiringDate);

		txtHiringDate = new JTextField();
		txtHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
		txtHiringDate.setBackground(Color.WHITE);
		txtHiringDate.setBounds(31, 230, 508, 25);
		panel.add(txtHiringDate);

		chkIsActive = new JCheckBox();
		chkIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsActive.setBounds(31, 341, 25, 25);
		panel.add(chkIsActive);
		chkIsActive.setBackground(Color.WHITE);

		chkIsAgreement = new JCheckBox();
		chkIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsAgreement.setBounds(31, 398, 25, 25);
		panel.add(chkIsAgreement);
		chkIsAgreement.setBackground(Color.WHITE);

		chkIsManager = new JCheckBox();
		chkIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsManager.setBounds(31, 291, 25, 25);
		panel.add(chkIsManager);
		chkIsManager.setBackground(Color.WHITE);

		JLabel lbDepartment = new JLabel("Department");
		lbDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		lbDepartment.setBounds(31, 429, 100, 20);
		panel.add(lbDepartment);

		txtDepartment = new JTextField();
		txtDepartment.setBackground(Color.WHITE);
		txtDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		txtDepartment.setBounds(31, 459, 508, 25);
		panel.add(txtDepartment);

		JLabel lbJobTitle = new JLabel("Job Title");
		lbJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		lbJobTitle.setBounds(31, 494, 100, 20);
		panel.add(lbJobTitle);

		txtJobTitle = new JTextField();
		txtJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		txtJobTitle.setBackground(Color.WHITE);
		txtJobTitle.setBounds(31, 518, 508, 25);
		panel.add(txtJobTitle);

		JLabel lbIsManager = new JLabel("Is Manager");
		lbIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsManager.setBounds(31, 265, 100, 20);
		panel.add(lbIsManager);

		JLabel lbIsActive = new JLabel("Is Active");
		lbIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsActive.setBounds(31, 322, 73, 13);
		panel.add(lbIsActive);

		JLabel lbIsAgreement = new JLabel("Is Agreement");
		lbIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsAgreement.setBounds(31, 372, 100, 20);
		panel.add(lbIsAgreement);

		JButton btnBack = new JButton("Back");
		btnBack.setBackground(Color.RED);
		btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
		btnBack.setBounds(31, 557, 85, 27);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
				parentFrame.setActiveNav("Employee Dashboard");
			}
		});
		panel.add(btnBack);
		setReadOnly(true);
	}

	public void loadEmployeeData(EmployeeDetailsModel employee) {
		this.currentModel = employee;
		txtName.setText(employee.getEmpName());
		txtEmail.setText(employee.getEmail());
		txtPhone.setText(employee.getPhone());
		txtHiringDate.setText(employee.getHiringDate());
		chkIsActive.setSelected(employee.isActive());
		chkIsAgreement.setSelected(employee.isAgreement());
		chkIsManager.setSelected(employee.isManager());
		txtDepartment.setText(employee.getDepartment());
		txtJobTitle.setText(employee.getJobTitle());
	}

	public void setReadOnly(boolean readOnly) {
		txtName.setEditable(!readOnly);
		txtPhone.setEditable(!readOnly);
		txtEmail.setEditable(!readOnly);
		txtHiringDate.setEditable(!readOnly);
		txtDepartment.setEditable(!readOnly);
		txtJobTitle.setEditable(!readOnly);
		chkIsActive.setEnabled(!readOnly);
		chkIsAgreement.setEnabled(!readOnly);
		chkIsManager.setEnabled(!readOnly);
	}

	public void loadUserDetail(String name) {
		EmployeeDetailsController controller = new EmployeeDetailsController(null);
		EmployeeDetailsModel model = controller.fetchAdminByName(name);
		if (model != null) {
			currentModel = model;
			txtName.setText(model.getEmpName());
			txtEmail.setText(model.getEmail());
			txtPhone.setText(model.getPhone());
			txtHiringDate.setText(model.getHiringDate());
			chkIsActive.setSelected(model.isActive());
			chkIsAgreement.setSelected(model.isAgreement());
			chkIsManager.setSelected(model.isManager());
			txtDepartment.setText(model.getDepartment());
			txtJobTitle.setText(model.getJobTitle());
		} else {
			JOptionPane.showMessageDialog(this, "Employee not found!");
		}
	}

}
