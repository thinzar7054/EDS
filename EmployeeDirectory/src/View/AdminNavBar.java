package View;

import javax.swing.*;

import java.awt.*;

import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;

import java.util.LinkedHashMap;

import java.util.Map;

import java.awt.event.ActionListener;

import java.awt.event.ActionEvent;

public class AdminNavBar extends JFrame {

	private JPanel contentPanel;

	private CardLayout cardLayout;

	private Map<String, JPanel> panels = new LinkedHashMap<>();

	private Map<String, JLabel> navLabels = new LinkedHashMap<>();

	private Color normalColor = Color.BLACK;

	private Color hoverColor = new Color(0, 0, 255); // Blue

	private Color activeColor = new Color(34, 139, 34); // Forest Green

	private JLabel activeLabel = null;

	public AdminNavBar() {

		setTitle("Employee Directory");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setSize(800, 800);

		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel(new BorderLayout());

		JPanel menuBar = createMenuBar();

		mainPanel.add(menuBar, BorderLayout.NORTH);

		cardLayout = new CardLayout();

		contentPanel = new JPanel(cardLayout);

		contentPanel.setBackground(Color.WHITE);

		createPanels();

		for (Map.Entry<String, JPanel> entry : panels.entrySet()) {

			contentPanel.add(entry.getValue(), entry.getKey());

		}

		cardLayout.show(contentPanel, "EmployeeDashboard");

		setActiveNav("Employee Dashboard");

		mainPanel.add(contentPanel, BorderLayout.CENTER);

		getContentPane().add(mainPanel);

	}

	private JPanel createMenuBar() {

		JPanel menuBar = new JPanel(new BorderLayout());

		menuBar.setBackground(new Color(230, 230, 250));

		menuBar.setPreferredSize(new Dimension(getWidth(), 50));

		menuBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 2, -1));
		leftPanel.setOpaque(false);

		ImageIcon homeIcon = new ImageIcon("C:\\OJT\\image\\home.png");

		Image img = homeIcon.getImage().getScaledInstance(35, 30, Image.SCALE_SMOOTH);

		JLabel menuIcon = new JLabel(new ImageIcon(img));

		menuIcon.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		JLabel systemName = new JLabel("Employee Directory");

		systemName.setFont(new Font("Arial", Font.PLAIN, 18));

		systemName.setForeground(Color.BLACK);

		leftPanel.add(menuIcon);

		leftPanel.add(systemName);

		JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 0));

		navPanel.setOpaque(false);

		String[] menuItems = { "Employee Dashboard", "Admin Dashboard", "Add Admin" };

		for (String item : menuItems) {

			JLabel navLabel = createNavLabel(item);

			navLabels.put(item, navLabel);

			navPanel.add(navLabel);

		}

		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

		rightPanel.setOpaque(false);

		JButton logoutButton = new JButton("Logout");

		logoutButton.setFocusPainted(false);

		logoutButton.setForeground(Color.WHITE);

		logoutButton.setBackground(new Color(220, 53, 69)); // Bootstrap Danger color

		logoutButton.setFont(new Font("Arial", Font.PLAIN, 14));

		logoutButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

		logoutButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

		logoutButton.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {

				logoutButton.setBackground(new Color(200, 35, 50));

			}

			public void mouseExited(MouseEvent e) {

				logoutButton.setBackground(new Color(220, 53, 69));

			}

		});

		logoutButton.addActionListener(e -> {

			int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to logout?", "Logout",

					JOptionPane.YES_NO_OPTION);

			if (confirm == JOptionPane.YES_OPTION) {

				new Login().setVisible(true);
				dispose();
				return;
			}

		});

		rightPanel.add(logoutButton);

		menuBar.add(leftPanel, BorderLayout.WEST);

		menuBar.add(navPanel, BorderLayout.CENTER);

		menuBar.add(rightPanel, BorderLayout.EAST);

		return menuBar;

	}

	private JLabel createNavLabel(String name) {

		JLabel label = new JLabel(name);

		label.setFont(new Font("Arial", Font.PLAIN, 14));

		label.setForeground(normalColor);

		label.setCursor(new Cursor(Cursor.HAND_CURSOR));

		label.setOpaque(false);

		label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

		label.addMouseListener(new MouseAdapter() {

			public void mouseEntered(MouseEvent e) {

				if (label != activeLabel) {

					label.setForeground(hoverColor);

				}

			}

			public void mouseExited(MouseEvent e) {

				if (label != activeLabel) {

					label.setForeground(normalColor);

				}

			}

			public void mouseClicked(MouseEvent e) {

				setActiveNav(name);

				cardLayout.show(contentPanel, name.replace(" ", ""));

			}

		});

		return label;

	}

	void setActiveNav(String name) {

		if (activeLabel != null) {

			activeLabel.setForeground(normalColor);

		}

		activeLabel = navLabels.get(name);

		if (activeLabel != null) {

			activeLabel.setForeground(activeColor);

		}

	}

	private void createPanels() {
		panels.put("EmployeeDashboard", new EmpDashboardAdminPanel(this));
		panels.put("EmployeeDetails", new EmployeeDetailsAdminView(this));
		panels.put("AddEmployee", new AddEmployeeDetailsView(this));
		panels.put("AdminDashboard", new AdminDashboard(this));
		panels.put("AdminDetails", new AdminDetailsAdminView(this));
		panels.put("AddAdmin", new AddAdminView(this));

		for (Map.Entry<String, JPanel> entry : panels.entrySet()) {
			contentPanel.add(entry.getValue(), entry.getKey());
		}
	}

	private JPanel panelWithLabel(String text) {

		JPanel panel = new JPanel();

		panel.add(new JLabel(text));

		return panel;

	}

	public JPanel getContentPanel() {
		return contentPanel;
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}

	public Map<String, JPanel> getPanels() {
		return panels;
	}

	public static void main(String[] args) {

		SwingUtilities.invokeLater(() -> {

			AdminNavBar view = new AdminNavBar();

			view.setVisible(true);

		});

	}

	public void refreshEmployeeDashboard() {
		EmpDashboardAdminPanel dashboard = (EmpDashboardAdminPanel) getPanels().get("EmployeeDashboard");
		dashboard.loadEmployees();
	}

	public void refreshAdminDashboard() {
		AdminDashboard dashboard = (AdminDashboard) getPanels().get("AdminDashboard");
		dashboard.loadAdmin();
	}
}
