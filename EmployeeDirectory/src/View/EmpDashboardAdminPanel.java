package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controller.EmployeeDAO;
import Model.EmployeeDetailsModel;
import Model.DepartmentModel;
import Model.PositionModel;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class EmpDashboardAdminPanel extends JPanel {
	private AdminNavBar parentFrame;
	private static final long serialVersionUID = 1L;
	private JTextField txtSearch;
	private JPanel gridPanel;
	private EmployeeDAO employeeDAO;

	public EmpDashboardAdminPanel(AdminNavBar parentFrame) {
		this.parentFrame = parentFrame;
		setBackground(new Color(245, 245, 245));
		setLayout(null);
		this.employeeDAO = new EmployeeDAO();

		initializeUI();
		loadEmployees();
	}

	private void initializeUI() {
		JLabel lblHeader = new JLabel("Employee Dashboard");
		lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
		lblHeader.setBounds(33, 20, 272, 32);
		add(lblHeader);

		txtSearch = new JTextField();
		txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
		txtSearch.setBounds(33, 68, 483, 32);
		add(txtSearch);
		setPlaceholder(txtSearch, "Search by Name or Department");

		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(e -> {
			String keyword = txtSearch.getText().trim();
			if (keyword.isEmpty() || keyword.equals("Search by Name or Department")) {
				loadEmployees();
				return;
			}
			searchEmployees(keyword);
		});
		btnSearch.setBackground(Color.RED);
		btnSearch.setForeground(Color.WHITE);
		btnSearch.setFont(new Font("Arial", Font.BOLD, 14));
		btnSearch.setBounds(551, 67, 91, 32);
		add(btnSearch);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "AddEmployee");
				parentFrame.setActiveNav("Add Employee");
				AddEmployeeDetailsView addEmployeeDetailsView = (AddEmployeeDetailsView) parentFrame.getPanels()
						.get("AddEmployee");
			}
		});
		btnAdd.setBackground(Color.RED);
		btnAdd.setForeground(Color.WHITE);
		btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
		btnAdd.setBounds(652, 67, 91, 32);
		add(btnAdd);

		gridPanel = new JPanel();
		gridPanel.setLayout(new GridLayout(5, 1, 10, 10));
		gridPanel.setBackground(new Color(245, 245, 245));
		gridPanel.setBounds(23, 125, 750, 500);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		add(gridPanel);
	}

	void loadEmployees() {
		gridPanel.removeAll();
		try {
			List<EmployeeDetailsModel> employees = employeeDAO.getAllEmployeesWithDetails();

			if (employees.isEmpty()) {
				JLabel noDataLabel = new JLabel("No employee data found");
				noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
				gridPanel.add(noDataLabel);
			} else {
				int count = Math.min(employees.size(), 5);
				for (int i = 0; i < count; i++) {
					addEmployeeCard(employees.get(i));
				}

				for (int i = count; i < 5; i++) {
					JPanel emptyCard = new JPanel();
					emptyCard.setBackground(new Color(245, 245, 245));
					emptyCard.setPreferredSize(new Dimension(680, 80));
					gridPanel.add(emptyCard);
				}
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error loading employee data: " + ex.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
		gridPanel.revalidate();
		gridPanel.repaint();
	}

	private void addEmployeeCard(EmployeeDetailsModel emp) {
		JPanel card = new JPanel(new BorderLayout());
		card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		card.setBackground(Color.WHITE);
		card.setPreferredSize(new Dimension(680, 80));

		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
		infoPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		infoPanel.setBackground(Color.WHITE);

		JLabel nameLabel = new JLabel(
				"<html><a href='#' style='color:black;text-decoration:none'>" + emp.getEmpName() + "</a></html>");
		nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
		nameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		nameLabel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDetails");
				parentFrame.setActiveNav("Employee Details");
				EmployeeDetailsAdminView detailsView = (EmployeeDetailsAdminView) parentFrame.getPanels()
						.get("EmployeeDetails");
				detailsView.loadEmployeeData(emp);
			}

			public void mouseEntered(MouseEvent e) {
				nameLabel.setText("<html><a href='#' style='color:#0066cc;'>" + emp.getEmpName() + "</a></html>");
			}

			public void mouseExited(MouseEvent e) {
				nameLabel.setText("<html><a href='#' style='color:black;text-decoration:none'>" + emp.getEmpName()
						+ "</a></html>");
			}
		});

		JLabel detailsLabel = new JLabel(emp.getDepartment());
		detailsLabel.setFont(new Font("Arial", Font.PLAIN, 14));
		detailsLabel.setForeground(new Color(100, 100, 100));
		detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

		infoPanel.add(nameLabel);
		infoPanel.add(detailsLabel);
		card.add(infoPanel, BorderLayout.CENTER);
		URL iconURL = getClass().getResource("/image/bin.png");
		ImageIcon icon = null;

		if (iconURL != null) {
			icon = new ImageIcon(iconURL);
		} else {
			System.err.println("Icon not found");
		}

		JButton btnDelete = new JButton(icon);
		btnDelete.setBackground(Color.RED);
		btnDelete.setHorizontalTextPosition(SwingConstants.RIGHT);
		btnDelete.setIconTextGap(10);
		btnDelete.addActionListener(e -> deleteEmployee(emp));
		card.add(btnDelete, BorderLayout.EAST);

		gridPanel.add(card);
	}

	private void searchEmployees(String searchText) {
		try {
			List<EmployeeDetailsModel> allEmployees = employeeDAO.getAllEmployeesWithDetails();
			List<EmployeeDetailsModel> filteredEmployees = new ArrayList<>();

			for (EmployeeDetailsModel emp : allEmployees) {
				if (emp.getEmpName().toLowerCase().contains(searchText.toLowerCase())
						|| emp.getDepartment().toLowerCase().contains(searchText.toLowerCase())) {
					filteredEmployees.add(emp);
				}
			}

			gridPanel.removeAll();
			if (filteredEmployees.isEmpty()) {
				JLabel noDataLabel = new JLabel("No matching employees found");
				noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
				gridPanel.add(noDataLabel);
			} else {
				int count = Math.min(filteredEmployees.size(), 5);
				for (int i = 0; i < count; i++) {
					addEmployeeCard(filteredEmployees.get(i));
				}

				for (int i = count; i < 5; i++) {
					JPanel emptyCard = new JPanel();
					emptyCard.setBackground(new Color(245, 245, 245));
					emptyCard.setPreferredSize(new Dimension(680, 80));
					gridPanel.add(emptyCard);
				}
			}
			gridPanel.revalidate();
			gridPanel.repaint();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Error searching employee data: " + ex.getMessage(), "Database Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void deleteEmployee(EmployeeDetailsModel emp) {
		int confirm = JOptionPane.showConfirmDialog(this, "Delete " + emp.getEmpName() + "?", "Confirm Delete",
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			try {
				if (employeeDAO.deleteEmployee(emp.getEmp_id())) {
					loadEmployees(); // Refresh the list
				} else {
					JOptionPane.showMessageDialog(this, "Failed to delete employee", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		} else {
			parentFrame.refreshEmployeeDashboard();
			parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDashboard");
			parentFrame.setActiveNav("Employee Dashboard");
			txtSearch.setText("");
		}
	}

	private void setPlaceholder(JTextField txtSearch, String placeholder) {
		txtSearch.setForeground(Color.GRAY);
		txtSearch.setText(placeholder);
		final boolean[] showingPlaceholder = { true };

		txtSearch.addFocusListener(new FocusAdapter() {
			public void focusGained(FocusEvent e) {
				if (showingPlaceholder[0]) {
					txtSearch.setText("");
					txtSearch.setForeground(Color.BLACK);
					showingPlaceholder[0] = false;
				}
			}

			public void focusLost(FocusEvent e) {
				if (txtSearch.getText().isEmpty()) {
					txtSearch.setForeground(Color.GRAY);
					txtSearch.setText(placeholder);
					showingPlaceholder[0] = true;
				}
			}
		});

		txtSearch.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (showingPlaceholder[0]) {
					txtSearch.setText("");
					txtSearch.setForeground(Color.BLACK);
					showingPlaceholder[0] = false;
				}
			}
		});

		txtSearch.getDocument().addDocumentListener(new DocumentListener() {
			private void handleChange() {
				String keyword = txtSearch.getText().trim();
				if (keyword.isEmpty() || keyword.equals(placeholder)) {
					loadEmployees();
				}
			}

			public void insertUpdate(DocumentEvent e) {
				handleChange();
			}

			public void removeUpdate(DocumentEvent e) {
				handleChange();
			}

			public void changedUpdate(DocumentEvent e) {
				handleChange();
			}
		});
	}

}