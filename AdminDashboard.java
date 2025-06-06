package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controller.AdminDashboardController;
import Model.AdminModel;

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class AdminDashboard extends JPanel {
    private AdminNavBar parentFrame;
    private static final long serialVersionUID = 1L;
    private JTextField txtSearch;
    private JPanel gridPanel;
    private AdminDashboardController employeeDAO;

    public AdminDashboard(AdminNavBar parentFrame) {
        this.parentFrame = parentFrame;
        setBackground(Color.WHITE);
        setLayout(null);
        this.employeeDAO = new AdminDashboardController();

        initializeUI();
        loadAdmin();
    }

    private void initializeUI() {
        JLabel lblHeader = new JLabel("Admin Dashboard");
        lblHeader.setFont(new Font("Arial", Font.BOLD, 22));
        lblHeader.setForeground(new Color(51, 51, 51));
        lblHeader.setBounds(33, 20, 400, 60);
        add(lblHeader);

        txtSearch = new JTextField();
        txtSearch.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSearch.setForeground(new Color(107, 114, 128));
        txtSearch.setBounds(33, 90, 620, 42);
        add(txtSearch);
        setPlaceholder(txtSearch, "Search By Name or Department");

        JButton btnSearch = new JButton("Search");
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim();
            if (keyword.isEmpty() || keyword.equals("Search By Name or Department")) {
                loadAdmin();
                return;
            }
            searchAdmin(keyword);
        });
        btnSearch.setBackground(new Color(255, 59, 48)); // Red color
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setFont(new Font("Arial", Font.BOLD, 12));
        btnSearch.setFocusPainted(false);
        btnSearch.setBounds(670, 90, 105, 42);
        btnSearch.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnSearch.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnSearch.setOpaque(true);
        btnSearch.setBorderPainted(false);
        add(btnSearch);

        gridPanel = new JPanel();
        gridPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for responsiveness
        gridPanel.setBackground(Color.WHITE);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBounds(23, 150, 760, 500);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane);
    }

    void loadAdmin() {
        gridPanel.removeAll();
        try {
            List<AdminModel> admin = employeeDAO.getAllEmployeesWithDetails();

            if (admin.isEmpty()) {
                JLabel noDataLabel = new JLabel("No admin data found");
                noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                noDataLabel.setForeground(new Color(107, 114, 128));
                GridBagConstraints gbc = createGbc(0, 0);
                gridPanel.add(noDataLabel, gbc);
            } else {
                int index = 0;
                for (AdminModel emp : admin) {
                    addEmployeeCard(emp, index++);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading employee data: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }

    private void addEmployeeCard(AdminModel emp, int index) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(700, 90));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setOpaque(true);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("<html><a href='#' style='color:#333333;text-decoration:none;'>" + emp.getName() + "</a></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        nameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "AdminDetails");
                parentFrame.setActiveNav("Admin Details");
                AdminDetailsAdminView detailsView = (AdminDetailsAdminView) parentFrame.getPanels().get("AdminDetails");
                detailsView.loadAdminData(emp);
            }

            public void mouseEntered(MouseEvent e) {
                nameLabel.setText("<html><a href='#' style='color:#0066cc; text-decoration:underline;'>" + emp.getName() + "</a></html>");
            }

            public void mouseExited(MouseEvent e) {
                nameLabel.setText("<html><a href='#' style='color:#333333;text-decoration:none;'>" + emp.getName() + "</a></html>");
            }
        });

        JLabel detailsLabel = new JLabel(emp.getDepartment());
        detailsLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsLabel.setForeground(new Color(107, 114, 128));
        detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(6));
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
        btnDelete.setBackground(new Color(255, 59, 48));
        btnDelete.setFocusPainted(false);
        btnDelete.setBorderPainted(false);
        btnDelete.setContentAreaFilled(false);
        btnDelete.setOpaque(true);
        btnDelete.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnDelete.setPreferredSize(new Dimension(44, 44));
        btnDelete.addActionListener(e -> deleteAdmin(emp));
        card.add(btnDelete, BorderLayout.EAST);

        GridBagConstraints gbc = createGbc(0, index);
        gridPanel.add(card, gbc);
    }

    private GridBagConstraints createGbc(int x, int y) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    private void searchAdmin(String searchText) {
        try {
            List<AdminModel> allEmployees = employeeDAO.getAllEmployeesWithDetails();
            List<AdminModel> filteredEmployees = new ArrayList<>();

            for (AdminModel emp : allEmployees) {
                if (emp.getName().toLowerCase().contains(searchText.toLowerCase())
                        || emp.getDepartment().toLowerCase().contains(searchText.toLowerCase())) {
                    filteredEmployees.add(emp);
                }
            }

            gridPanel.removeAll();
            if (filteredEmployees.isEmpty()) {
                JLabel noDataLabel = new JLabel("No matching admin found");
                noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                noDataLabel.setForeground(new Color(107, 114, 128));
                GridBagConstraints gbc = createGbc(0, 0);
                gridPanel.add(noDataLabel, gbc);
            } else {
                int index = 0;
                for (AdminModel emp : filteredEmployees) {
                    addEmployeeCard(emp, index++);
                }
            }
            gridPanel.revalidate();
            gridPanel.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching admin data: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAdmin(AdminModel emp) {
        int confirm = JOptionPane.showConfirmDialog(this, "Delete " + emp.getName() + "?", "Confirm Delete",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                if (employeeDAO.deleteAdmin(emp.getAdmId())) {
                    loadAdmin(); // Refresh the list
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to delete admin", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            parentFrame.refreshAdminDashboard();
            parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "AdminDashboard");
            parentFrame.setActiveNav("Admin Dashboard");
            txtSearch.setText("");
        }
    }

    private void setPlaceholder(JTextField txtSearch, String placeholder) {
        txtSearch.setForeground(new Color(156, 163, 175)); // placeholder gray
        txtSearch.setText(placeholder);
        final boolean[] showingPlaceholder = { true };

        txtSearch.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder[0]) {
                    txtSearch.setText("");
                    txtSearch.setForeground(new Color(51, 51, 51)); // dark text
                    showingPlaceholder[0] = false;
                }
            }

            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setForeground(new Color(156, 163, 175));
                    txtSearch.setText(placeholder);
                    showingPlaceholder[0] = true;
                }
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (showingPlaceholder[0]) {
                    txtSearch.setText("");
                    txtSearch.setForeground(new Color(51, 51, 51));
                    showingPlaceholder[0] = false;
                }
            }
        });

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            private void handleChange() {
                String keyword = txtSearch.getText().trim();
                if (keyword.isEmpty() || keyword.equals(placeholder)) {
                    loadAdmin();
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

