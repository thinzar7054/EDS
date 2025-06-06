package Controller;

import Config.DBConfig;
import Model.EmployeeDetailsModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class EmployeeDetailsController {

	private EmployeeDetailsModel model;
	private DBConfig db = new DBConfig();

	public EmployeeDetailsController(EmployeeDetailsModel model) {
		this.model = model;
	}

	public boolean saveAdminDetails() {
		try (Connection con = db.getConnection()) {

			String depId = getDepartmentIdByName(con, model.getDepartment());
			String postId = getJobIdByName(con, model.getJobTitle());

			if (depId == null || postId == null) {
				JOptionPane.showMessageDialog(null, "Invalid Department or Job Title.");
				return false;
			}

			String query = "UPDATE employee SET " + "empName = ?, email = ?, phone = ?, hiringDate = ?, "
					+ "isActive = ?, isAgreement = ?, isManager = ?, dep_id = ?, post_id = ? " + "WHERE emp_id = ?";

			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, model.getEmpName());
			pst.setString(2, model.getEmail());
			pst.setString(3, model.getPhone());
			pst.setString(4, model.getHiringDate());
			pst.setBoolean(5, model.isActive());
			pst.setBoolean(6, model.isAgreement());
			pst.setBoolean(7, model.isManager());
			pst.setString(8, depId);
			pst.setString(9, postId);
			pst.setString(10, model.getEmp_id());

			int rows = pst.executeUpdate();
			return rows > 0;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
			return false;
		}
	}

	public EmployeeDetailsModel fetchAdminByName(String name) {
		EmployeeDetailsModel loadedModel = null;
		try (Connection con = db.getConnection()) {
			String query = "SELECT a.*, d.depName, p.postName FROM admin a "
					+ "JOIN department d ON a.dep_id = d.dep_id " + "JOIN position p ON a.post_id = p.post_id "
					+ "WHERE a.admName = ?";

			try (PreparedStatement pst = con.prepareStatement(query)) {
				pst.setString(1, name);
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						loadedModel = new EmployeeDetailsModel();
						loadedModel.setEmp_id(rs.getString("emp_id"));
						loadedModel.setEmpName(rs.getString("empName"));
						loadedModel.setEmail(rs.getString("email"));
						loadedModel.setPhone(rs.getString("phone"));
						loadedModel.setHiringDate(rs.getString("hiringDate"));
						loadedModel.setActive(rs.getBoolean("isActive"));
						loadedModel.setAgreement(rs.getBoolean("isAgreement"));
						loadedModel.setManager(rs.getBoolean("isManager"));
						loadedModel.setDepartment(rs.getString("depName"));
						loadedModel.setJobTitle(rs.getString("postName"));
					}
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error loading admin: " + ex.getMessage());
		}
		return loadedModel;
	}

	private String getDepartmentIdByName(Connection con, String depName) {
		String depId = null;
		String sql = "SELECT dep_id FROM department WHERE depName = ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, depName);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					depId = rs.getString("dep_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return depId;
	}

	private String getJobIdByName(Connection con, String postName) {
		String jobId = null;
		String sql = "SELECT post_id FROM position WHERE  postName= ?";
		try (PreparedStatement stmt = con.prepareStatement(sql)) {
			stmt.setString(1, postName);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					jobId = rs.getString("post_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jobId;
	}

	public EmployeeDetailsModel fetchEmployeeByName(String employeeName) {
		EmployeeDetailsModel employee = null;

		String query = "SELECT * FROM employees WHERE empName = ?";

		try (Connection conn = db.getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, employeeName);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				employee = new EmployeeDetailsModel();
				employee.setEmpName(rs.getString("empName"));
				employee.setEmail(rs.getString("email"));
				employee.setPhone(rs.getString("phone"));
				employee.setHiringDate(rs.getString("hiringDate"));
				employee.setActive(rs.getBoolean("isActive"));
				employee.setAgreement(rs.getBoolean("isAgreement"));
				employee.setManager(rs.getBoolean("isManager"));
				employee.setDepartment(rs.getString("department"));
				employee.setJobTitle(rs.getString("jobTitle"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return employee;
	}
}
