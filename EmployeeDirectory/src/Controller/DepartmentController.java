package Controller;

import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Config.DBConfig;
import Model.DepartmentModel;
import Model.PositionModel;

public class DepartmentController {
	public static Connection con = null;
	static {
		DBConfig cls = new DBConfig();
		try {
			con = cls.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Insert Fail,Inter error", "Fail", JOptionPane.ERROR_MESSAGE);
		}

	}

	public int insert(DepartmentModel dm) {
		int result = 0;
		String sql = "insert into empdirectory.department (dep_id,dep_name) " + "values(?,?)";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dm.getDep_id());
			ps.setString(2, dm.getDepName());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Insert Fail,Inter error", "Fail", JOptionPane.ERROR_MESSAGE);
		}
		return result;

	}

	public String searchDepName(DepartmentModel dm) {
		String result = null;
		String sql = "select depName from empdirectory.department where dep_id =?";
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, dm.getDep_id());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("dep_name");
			} else {
				System.out.println("This department is not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String searchDepId(DepartmentModel pm) {
		String result = null;
		String sql = "select dep_id from empdirectory.department where depName=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, pm.getDepName());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("dep_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isduplicate(DepartmentModel pm) throws SQLException {
		boolean duplicate = false;
		String sql = "select * from empdirectory.department where depName=?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, pm.getDepName());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			duplicate = true;
		} else {
			duplicate = false;
		}
		return duplicate;
	}

}
