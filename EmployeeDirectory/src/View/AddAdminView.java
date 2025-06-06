package View;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import Config.Checking;
import Controller.AddAdminController;
import Controller.PositionController;
import Model.PositionModel;
import Config.AutoID;
import Config.MySqlQueries;
import Controller.DepartmentController;
import Model.AddAdminModel;
import Model.DepartmentModel;
import com.toedter.calendar.JDateChooser;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddAdminView extends JPanel {

	private static final long serialVersionUID = 1L;
	private boolean passwordVisible = false;
	private static JTextField txtName;
	private static JTextField txtEmail;
	private static JTextField txtPhone;
	private JLabel lbAdminID;
	private JComboBox cboDepartment;
	private JComboBox cboJobTitle;
	private JDateChooser dateChooser;
	private AdminNavBar parentFrame;
	String adminID = null;
	private JPasswordField txtPassword;
	private JLabel lblEyeIcon;
	private boolean isPasswordVisible = false;

	private JCheckBox chkIsActive;

	private JCheckBox chkIsAgreement;

	private JCheckBox chkIsManager;

	private JPanel passwordPanel;

	private JButton btnShowHidePassword;

	private Container contentPane;

	public AddAdminView(AdminNavBar parentFrame) {
		this.parentFrame = parentFrame;
		setLayout(null);

		JLabel lblHeader = new JLabel("Add Admin Details");
		lblHeader.setBounds(58, 11, 300, 30);
		lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
		add(lblHeader);

		setPreferredSize(new Dimension(755, 679));
		setBackground(new Color(245, 245, 245));

		JPanel panel = new JPanel();
		panel.setBounds(58, 53, 657, 611);
		add(panel);
		panel.setLayout(null);

		JLabel lbName = new JLabel("<html>Name <span style='color:red;'>*</span></html>");
		lbName.setFont(new Font("Arial", Font.PLAIN, 14));
		lbName.setBounds(31, 5, 73, 20);
		panel.add(lbName);

		txtName = new JTextField();
		txtName.setFont(new Font("Arial", Font.PLAIN, 14));
		txtName.setBounds(31, 35, 590, 25);
		panel.add(txtName);

		txtName.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent e) {
				char typedChar = e.getKeyChar();

				if (Character.isDigit(typedChar)) {
					e.consume();
				}
			}
		});

		JLabel lbPhone = new JLabel("<html>Phone Number <span style='color:red;'>*</span></html>");
		lbPhone.setFont(new Font("Arial", Font.PLAIN, 14));
		lbPhone.setBounds(31, 70, 135, 20);
		panel.add(lbPhone);

		txtPhone = new JTextField();
		txtPhone.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPhone.setBounds(31, 100, 590, 25);
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
		lbEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		lbEmail.setBounds(31, 135, 73, 20);
		panel.add(lbEmail);

		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
		txtEmail.setBounds(31, 165, 590, 25);
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
		lbHiringDate.setFont(new Font("Arial", Font.PLAIN, 14));
		lbHiringDate.setBounds(31, 200, 100, 20);
		panel.add(lbHiringDate);

		chkIsActive = new JCheckBox();
		chkIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsActive.setBounds(31, 336, 25, 25);
		panel.add(chkIsActive);
		chkIsActive.setBackground(Color.WHITE);

		chkIsAgreement = new JCheckBox();
		chkIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsAgreement.setBounds(31, 393, 25, 25);
		panel.add(chkIsAgreement);
		chkIsAgreement.setBackground(Color.WHITE);

		chkIsManager = new JCheckBox();
		chkIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		chkIsManager.setBounds(31, 286, 25, 25);
		panel.add(chkIsManager);
		chkIsManager.setBackground(Color.WHITE);

		chkIsManager.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chkIsManager.isSelected()) {
					cboJobTitle.setSelectedItem("Manager");
				} else {
					cboJobTitle.setSelectedItem(null);
				}
			}
		});

		JLabel lbDepartment = new JLabel("<html>Department <span style='color:red;'>*</span></html>");
		lbDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		lbDepartment.setBounds(31, 424, 100, 20);
		panel.add(lbDepartment);

		JLabel lbJobTitle = new JLabel("<html>Job Title <span style='color:red;'>*</span></html>");
		lbJobTitle.setFont(new Font("Arial", Font.PLAIN, 14));
		lbJobTitle.setBounds(343, 424, 100, 20);
		panel.add(lbJobTitle);

		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!validateFields())
					return;

				if (lbAdminID.getText().trim().toString().equals("") || txtName.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "There is a blank field!", "Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
					txtName.selectAll();
					return;
				}

				java.util.Date hiringDate = dateChooser.getDate();
				String formattedHiringDate = null;

				if (hiringDate != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					formattedHiringDate = sdf.format(hiringDate);

					java.util.Date currentDate = new java.util.Date();
					if (hiringDate.after(currentDate)) {
						JOptionPane.showMessageDialog(null, "Hiring date cannot be in the future!", "Validation Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a Hiring Date!", "Fail",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				PositionController pc = new PositionController();
				PositionModel pm = new PositionModel();
				pm.setPositionName(cboJobTitle.getSelectedItem().toString());
				String postId = pc.searchPostId(pm);
				if (postId == null || postId.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Invalid Job Title selected!", "Fail",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				DepartmentController dc = new DepartmentController();
				DepartmentModel dm = new DepartmentModel();
				dm.setDepName(cboDepartment.getSelectedItem().toString());
				String depId = dc.searchDepId(dm);
				if (depId == null || depId.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Invalid Department selected!", "Fail",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				AddAdminController ac = new AddAdminController();
				AddAdminModel am = new AddAdminModel();
				am.setAdm_id(lbAdminID.getText().toString());
				am.setAdmName(txtName.getText().toString());
				am.setPhone(txtPhone.getText().toString());
				am.setEmail(txtEmail.getText().toString());
				am.setHiringDate(formattedHiringDate);
				am.setManager(chkIsManager.isSelected());
				am.setActive(chkIsActive.isSelected());
				am.setAgreement(chkIsAgreement.isSelected());
				am.setDepId(depId);
				am.setPostId(postId);
				am.setPassword(txtPassword.getText().toString());

				int confirm = JOptionPane.showConfirmDialog(AddAdminView.this, "Do you want to save this admin?",
						"Confirm Save", JOptionPane.YES_NO_OPTION);

				if (confirm == JOptionPane.YES_OPTION) {
					saveEmployeeDetails(); // Optional if this has logic
				} else if (confirm == JOptionPane.NO_OPTION) {
					parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "AdminDashboard");
					parentFrame.setActiveNav("Admin Dashboard");
					return;
				}

				if (!Checking.IsValidName(am.getAdmName())) {
					JOptionPane.showMessageDialog(null, "Invalid Name!", "Fail", JOptionPane.ERROR_MESSAGE);
					txtName.requestFocus(true);
					txtName.selectAll();
				} else {
					int rs = ac.insert(am);
					if (rs == 1) {
						AutoID();
						JOptionPane.showMessageDialog(null, "Save Successfully", "Success",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}

				parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "AdminDashboard");
				parentFrame.setActiveNav("Admin Dashboard");
				Clear();
			}

			private void saveEmployeeDetails() {
			}
		});

		btnSave.setBackground(Color.RED);
		btnSave.setFont(new Font("Arial", Font.PLAIN, 14));
		btnSave.setBounds(31, 563, 85, 30);
		panel.add(btnSave);

		JLabel lbIsManager = new JLabel("<html>Is Manager <span style='color:red;'>*</span></html>");
		lbIsManager.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsManager.setBounds(31, 260, 100, 20);
		panel.add(lbIsManager);

		JLabel lbIsActive = new JLabel("<html>Is Active <span style='color:red;'>*</span></html>");
		lbIsActive.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsActive.setBounds(31, 317, 73, 13);
		panel.add(lbIsActive);

		JLabel lbIsAgreement = new JLabel("<html>Is Agreement <span style='color:red;'>*</span></html>");
		lbIsAgreement.setFont(new Font("Arial", Font.PLAIN, 14));
		lbIsAgreement.setBounds(31, 367, 100, 20);
		panel.add(lbIsAgreement);

		cboDepartment = new JComboBox();
		cboDepartment.setFont(new Font("Arial", Font.PLAIN, 14));
		cboDepartment.setBounds(31, 454, 303, 21);
		panel.add(cboDepartment);

		cboJobTitle = new JComboBox();
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
		cboJobTitle.setBounds(343, 454, 278, 21);
		panel.add(cboJobTitle);

		JLabel lblPassword = new JLabel("<html>Password <span style='color:red;'>*</span></html>");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		lblPassword.setBounds(31, 485, 100, 25);
		panel.add(lblPassword);

		passwordPanel = new JPanel();
		passwordPanel.setBounds(31, 521, 590, 25); // Adjusting position to make space for the icon
		passwordPanel.setLayout(new BorderLayout(0, 0));
		passwordPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		txtPassword.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); // Remove border for a cleaner look
		passwordPanel.add(txtPassword, BorderLayout.CENTER);

		btnShowHidePassword = new JButton();

		ImageIcon eyeIconClosed = new ImageIcon("C:\\25212\\image\\eye_close.png");
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

					ImageIcon closedEyeIcon = new ImageIcon("C:\\25212\\image\\eye_close.png");
					Image imgClosed = closedEyeIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
					closedEyeIcon = new ImageIcon(imgClosed);
					btnShowHidePassword.setIcon(closedEyeIcon);
				} else {
					txtPassword.setEchoChar((char) 0);
					passwordVisible = true;

					ImageIcon openEyeIcon = new ImageIcon("C:\\25212\\image\\eye.png");
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

		dateChooser = new JDateChooser();
		dateChooser.setBounds(31, 230, 590, 25);
		panel.add(dateChooser);

		Calendar calendar = Calendar.getInstance();
		dateChooser.setMaxSelectableDate(calendar.getTime());

		lbAdminID = new JLabel("");
		lbAdminID.setFont(new Font("Arial", Font.PLAIN, 14));
		lbAdminID.setBounds(637, 11, 78, 25);
		add(lbAdminID);

		AutoID();
		fillCombo();
	}

	protected void dispose() {
	}

	public void Clear() {
		txtName.setText(" ");
		txtPhone.setText(" ");
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
			showMessage("Name is required.");
			txtName.requestFocus();
			return false;
		}

		if (txtPhone.getText().trim().isEmpty()) {
			showMessage("Phone Number is required.");
			txtPhone.requestFocus();
			return false;
		} else if (!Checking.isPhoneNo(txtPhone.getText())) {
			showMessage("Only 11 digits are allowed for the phone number.");
			txtPhone.requestFocus();
			return false;
		}

		if (txtEmail.getText().trim().isEmpty()) {
			showMessage("Email is required.");
			txtEmail.requestFocus();
			return false;
		} else if (!Checking.IsEmailFormat(txtEmail.getText())) {
			showMessage("Invalid email address.");
			txtEmail.requestFocus();
			return false;
		}

		java.util.Date hiringDate = dateChooser.getDate();
		String formattedHiringDate = null;
		if (hiringDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			formattedHiringDate = sdf.format(hiringDate);
		} else {
			JOptionPane.showMessageDialog(null, "Please select a Hiring Date!", "Fail", JOptionPane.ERROR_MESSAGE);
			return false;
		}

		if (!chkIsManager.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please check the 'Is Manager' checkbox.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (!chkIsActive.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please check the 'Is Active' checkbox.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (!chkIsAgreement.isSelected()) {
			JOptionPane.showMessageDialog(this, "Please check the 'Is Agreement' checkbox.", "Validation Failed",
					JOptionPane.INFORMATION_MESSAGE);
			return false;
		}

		if (cboDepartment.getSelectedIndex() == 0) { // Assuming index 0 is the default "Please select" option
			showMessage("Please select a department.");
			cboDepartment.requestFocus();
			return false;
		}

		if (txtPassword.getText().trim().isEmpty()) {
			showMessage("Password is required.");
			txtPassword.requestFocus();
			return false;
		}

		return true;
	}

	public void AutoID() {
		try {
			AutoID idGenerator = new AutoID();
			lbAdminID.setText(idGenerator.getAdminID());
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Error generating admin ID", "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public void fillCombo() {
		MySqlQueries.addCoboBox("department", "depName", cboDepartment);
		MySqlQueries.addCoboBox("position", "postName", cboJobTitle);
	}

	private void showMessage(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Validation", JOptionPane.WARNING_MESSAGE);
	}
}
