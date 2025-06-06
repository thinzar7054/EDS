package Controller;

import java.sql.*;
import java.util.*;
import Config.DBConfig;
import Model.EmployeeDetailsModel;
import Model.DepartmentModel;
import Model.PositionModel;

public class EmployeeDAO {
	private Connection con;

	public EmployeeDAO() {
		try {
			this.con = DBConfig.getInstance().getConnection();
			System.out.println("Database connection successful: " + (con != null && !con.isClosed()));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to establish database connection", e);
		}
	}

	public List<EmployeeDetailsModel> getAllEmployeesWithDetails() {
		List<EmployeeDetailsModel> employees = new ArrayList<>();
		String sql = "SELECT e.*, d.depName, p.postName " + "FROM employee e "
				+ "LEFT JOIN department d ON e.dep_id = d.dep_id "
				+ "LEFT JOIN position p ON e.post_id = p.post_id  "; 

		try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				EmployeeDetailsModel emp = new EmployeeDetailsModel();
				emp.setEmp_id(rs.getString("emp_id")); 
				emp.setEmpName(rs.getString("empName")); 
				emp.setEmail(rs.getString("email"));
				emp.setPhone(rs.getString("phone"));
				emp.setHiringDate(rs.getString("hiringDate")); 
				emp.setActive(rs.getBoolean("isActive")); 
				emp.setAgreement(rs.getBoolean("isAgreement")); 
				emp.setManager(rs.getBoolean("isManager")); 
				emp.setDepartment(rs.getString("depName"));
				emp.setJobTitle(rs.getString("postName"));

				employees.add(emp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("SQL Error: " + e.getMessage()); 
		}
		return employees;
	}

	public List<DepartmentModel> getAllDepartments() {
		List<DepartmentModel> departments = new ArrayList<>();
		String sql = "SELECT * FROM department";

		try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				DepartmentModel dept = new DepartmentModel();
				dept.setDep_id(rs.getString("dep_id"));
				dept.setDepName(rs.getString("depName"));
				departments.add(dept);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return departments;
	}

	public List<PositionModel> getAllPositions() {
		List<PositionModel> positions = new ArrayList<>();
		String sql = "SELECT * FROM postition";

		try (PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			while (rs.next()) {
				PositionModel pos = new PositionModel();
				pos.setPost_id(rs.getString("post_id"));
				pos.setPositionName(rs.getString("postName"));
				positions.add(pos);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return positions;
	}

	public boolean addEmployee(EmployeeDetailsModel employee) {
		String sql = "INSERT INTO employee (emp_id, empName, email, phone, hiringDate, "
				+ "isActive, isAgreement, isManager, dep_id, post_id) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, employee.getEmp_id());
			ps.setString(2, employee.getEmpName());
			ps.setString(3, employee.getEmail());
			ps.setString(4, employee.getPhone());
			ps.setString(5, employee.getHiringDate());
			ps.setBoolean(6, employee.isActive());
			ps.setBoolean(7, employee.isAgreement());
			ps.setBoolean(8, employee.isManager());
			ps.setString(9, getDepartmentIdByName(employee.getDepartment()));
			ps.setString(10, getPositionIdByName(employee.getJobTitle()));

			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private String getDepartmentIdByName(String depName) throws SQLException {
		String sql = "SELECT dep_id FROM department WHERE depName = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, depName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("dep_id");
			}
		}
		return null;
	}

	private String getPositionIdByName(String positionName) throws SQLException {
		String sql = "SELECT post_id FROM position WHERE postName = ?";
		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, positionName);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getString("post_id");
			}
		}
		return null;
	}

	public boolean deleteEmployee(String employeeId) throws SQLException {
		String sql = "DELETE FROM employee WHERE emp_id = ?";

		try (PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, employeeId);
			int affectedRows = ps.executeUpdate();
			return affectedRows > 0;
		}
	}

	public void close() {
		DBConfig.getInstance().closeConnection();
	}
}