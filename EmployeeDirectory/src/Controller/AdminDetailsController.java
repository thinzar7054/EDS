package Controller;

import Config.DBConfig;
import Model.AdminModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class AdminDetailsController {

	private AdminModel model;
	private DBConfig db = new DBConfig();

	public AdminDetailsController(AdminModel model) {
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

			String query = "UPDATE admin SET " + "admName = ?, email = ?, phone = ?, hiringDate = ?, "
					+ "isActive = ?, isAgreement = ?, isManager = ?, dep_id = ?, post_id = ? " + "WHERE adm_id = ?";

			PreparedStatement pst = con.prepareStatement(query);
			pst.setString(1, model.getName());
			pst.setString(2, model.getEmail());
			pst.setString(3, model.getPhone());
			pst.setString(4, model.getHiringDate());
			pst.setBoolean(5, model.isActive());
			pst.setBoolean(6, model.isAgreement());
			pst.setBoolean(7, model.isManager());
			pst.setString(8, depId);
			pst.setString(9, postId);
			pst.setString(10, model.getAdmId());
			int rows = pst.executeUpdate();
			return rows > 0;
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
			return false;
		}
	}

	public AdminModel fetchAdminByName(String name) {
		AdminModel loadedAdminModel = null;
		try (Connection con = db.getConnection()) {
			String query = "SELECT a.*, d.depName, p.postName FROM admin a "
					+ "JOIN department d ON a.dep_id = d.dep_id " + "JOIN position p ON a.post_id = p.post_id "
					+ "WHERE a.admName = ?";

			try (PreparedStatement pst = con.prepareStatement(query)) {
				pst.setString(1, name);
				try (ResultSet rs = pst.executeQuery()) {
					if (rs.next()) {
						loadedAdminModel = new AdminModel();
						loadedAdminModel.setAdmId(rs.getString("adm_id"));
						loadedAdminModel.setName(rs.getString("admName"));
						loadedAdminModel.setEmail(rs.getString("email"));
						loadedAdminModel.setPhone(rs.getString("phone"));
						loadedAdminModel.setHiringDate(rs.getString("hiringDate"));
						loadedAdminModel.setActive(rs.getBoolean("isActive"));
						loadedAdminModel.setAgreement(rs.getBoolean("isAgreement"));
						loadedAdminModel.setManager(rs.getBoolean("isManager"));
						loadedAdminModel.setDepartment(rs.getString("depName"));
						loadedAdminModel.setJobTitle(rs.getString("postName"));
					}
				}
			}
		} catch (SQLException ex) {
			JOptionPane.showMessageDialog(null, "Error loading admin: " + ex.getMessage());
		}
		return loadedAdminModel;
	}

	private String getDepartmentIdByName(Connection con, String depName) {
		String depId = null;
		String sql = "SELECT dep_id FROM department WHERE depName = ?";
		try (PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setString(1, depName);
			try (ResultSet rs = pst.executeQuery()) {
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
		String sql = "SELECT post_id FROM position WHERE postName = ?";
		try (PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setString(1, postName);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					jobId = rs.getString("post_id");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return jobId;
	}
}
