package Controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import Config.DBConfig;
import Model.AddEmployeeDetailsModel;

public class AddEmployeeDetailsController {
	public static Connection con = null;

	static {
		DBConfig cls = new DBConfig();
		try {
			con = cls.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Database connection error", "Fail", JOptionPane.ERROR_MESSAGE);
		}
	}

	public List<String> getDepartments() throws SQLException {
		List<String> departments = new ArrayList<>();
		String sql = "SELECT depName FROM empdirectory.department";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				departments.add(rs.getString("depName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error fetching departments: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return departments;
	}

	public List<String> getJobTitles() throws SQLException {
		List<String> jobTitles = new ArrayList<>();
		String sql = "SELECT postName FROM empdirectory.position";
		try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				jobTitles.add(rs.getString("postName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error fetching job titles: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		}
		return jobTitles;
	}

	public boolean isDuplicate(AddEmployeeDetailsModel dain) throws SQLException {
		boolean duplicate = false;
		String sql = "SELECT * FROM empdirectory.employee WHERE name = ? AND emp_id != ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, dain.getEmpName());
			ps.setString(2, dain.getEmp_id());
			ResultSet rs = ps.executeQuery();
			duplicate = rs.next();
		}
		return duplicate;
	}

	public int insert(AddEmployeeDetailsModel dain) {
		int result = 0;
		String sql = "INSERT INTO empdirectory.employee "
				+ "(emp_id, empName, phone, email, hiringDate, isManager, isActive, isAgreement, dep_id, post_id, password) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			System.out.println("PostId: " + getPostIdByPostName(dain.getPos_id()));
			System.out.println("DepId: " + getDepIdByDepName(dain.getDep_id()));
			ps.setString(1, dain.getEmp_id()); // Use the generated ID
			ps.setString(2, dain.getEmpName());
			ps.setString(3, dain.getPhone());
			ps.setString(4, dain.getEmail());
			ps.setDate(5, dain.getHiringDate());
			ps.setBoolean(6, dain.isManager());
			ps.setInt(7, dain.isActive() ? 1 : 0);
			ps.setInt(8, dain.isAgreement() ? 1 : 0);
			ps.setString(9, getDepIdByDepName(dain.getDep_id()));
			ps.setString(10, getPostIdByPostName(dain.getPos_id()));
			ps.setString(11, dain.getPassword());

			// Check if the password is not null or empty
//            String password = dain.getPassword();
//            if (password == null || password.isEmpty()) {
//                throw new IllegalArgumentException("Password cannot be null or empty");
//            }
//            ps.setString(11, password); // Set the password

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "Error",
					JOptionPane.ERROR_MESSAGE);
		} catch (IllegalArgumentException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}

	public String getDepIdByDepName(String name) {
		int result = 0;
		String sql = "Select dep_id from department where depName = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("dep_id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String getPostIdByPostName(String name) {
		int result = 0;
		String sql = "Select post_id from position where postName = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("post_id");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "";
	}

	public List<String> getAllEmployeeIds() {
		List<String> employeeIds = new ArrayList<>();
		String query = "SELECT emp_id FROM employee"; // Adjust the table name and column name as necessary
		try (Statement statement = con.createStatement(); ResultSet resultSet = statement.executeQuery(query)) {
			while (resultSet.next()) {
				String empId = resultSet.getString("emp_id"); // Adjust the column name as necessary
				employeeIds.add(empId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return employeeIds;
	}
}
