package View;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Controller.EmployeeDAO;
import Model.EmployeeDetailsModel;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EmpDashboardUserPanel extends JPanel {
    private EmployeeNavBar parentFrame;
    private static final long serialVersionUID = 1L;
    private JTextField txtSearch;
    private JPanel gridPanel;
    private EmployeeDAO employeeDAO;

    public EmpDashboardUserPanel(EmployeeNavBar parentFrame) {
        this.parentFrame = parentFrame;
        setBackground(Color.WHITE);
        setLayout(null);
        this.employeeDAO = new EmployeeDAO();

        initializeUI();
        loadEmployees();
    }

    private void initializeUI() {
        JLabel lblHeader = new JLabel("Employee Dashboard");
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
                loadEmployees();
                return;
            }
            searchEmployees(keyword);
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

        // Create a center panel with FlowLayout to center gridPanel inside JScrollPane
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.Y_AXIS)); // vertical stacking
        gridPanel.setBackground(Color.WHITE);

        centerPanel.add(gridPanel);

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBounds(23, 150, 760, 500);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(scrollPane);
    }

    public void loadEmployees() {
        gridPanel.removeAll();
        try {
            List<EmployeeDetailsModel> employees = employeeDAO.getAllEmployeesWithDetails();

            if (employees.isEmpty()) {
                JLabel noDataLabel = new JLabel("No employee data found");
                noDataLabel.setFont(new Font("Arial", Font.ITALIC, 16));
                noDataLabel.setForeground(new Color(107, 114, 128));
                noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                gridPanel.add(Box.createVerticalGlue());
                gridPanel.add(noDataLabel);
                gridPanel.add(Box.createVerticalGlue());
            } else {
                for (EmployeeDetailsModel emp : employees) {
                    addEmployeeCard(emp);
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
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));
        card.setBackground(Color.WHITE);
        card.setPreferredSize(new Dimension(700, 90));
        card.setMaximumSize(new Dimension(700, 90));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        card.setOpaque(true);
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("<html><a href='#' style='color:#333333;text-decoration:none;'>" + emp.getEmpName() + "</a></html>");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        nameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        nameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                parentFrame.getCardLayout().show(parentFrame.getContentPanel(), "EmployeeDetails");
                parentFrame.setActiveNav("Employee Details");
                EmployeeDetailUserView detailsView = (EmployeeDetailUserView) parentFrame.getPanels().get("EmployeeDetails");
                detailsView.loadEmployeeData(emp);
            }

            public void mouseEntered(MouseEvent e) {
                nameLabel.setText("<html><a href='#' style='color:#0066cc; text-decoration:underline;'>" + emp.getEmpName() + "</a></html>");
            }

            public void mouseExited(MouseEvent e) {
                nameLabel.setText("<html><a href='#' style='color:#333333;text-decoration:none;'>" + emp.getEmpName() + "</a></html>");
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

        gridPanel.add(card);
        gridPanel.add(Box.createVerticalStrut(12));  // spacing between cards
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
                noDataLabel.setForeground(new Color(107, 114, 128));
                noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                gridPanel.add(Box.createVerticalGlue());
                gridPanel.add(noDataLabel);
                gridPanel.add(Box.createVerticalGlue());
            } else {
                for (EmployeeDetailsModel emp : filteredEmployees) {
                    addEmployeeCard(emp);
                }
            }
            gridPanel.revalidate();
            gridPanel.repaint();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error searching employee data: " + ex.getMessage(), "Database Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setPlaceholder(JTextField textField, String placeholder) {
        textField.setForeground(new Color(156, 163, 175)); // placeholder gray
        textField.setText(placeholder);
        final boolean[] showingPlaceholder = { true };

        textField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (showingPlaceholder[0]) {
                    textField.setText("");
                    textField.setForeground(new Color(51, 51, 51)); // dark text
                    showingPlaceholder[0] = false;
                }
            }

            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(new Color(156, 163, 175));
                    textField.setText(placeholder);
                    showingPlaceholder[0] = true;
                }
            }
        });

        textField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (showingPlaceholder[0]) {
                    textField.setText("");
                    textField.setForeground(new Color(51, 51, 51));
                    showingPlaceholder[0] = false;
                }
            }
        });

        textField.getDocument().addDocumentListener(new DocumentListener() {
            private void handleChange() {
                String keyword = textField.getText().trim();
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
