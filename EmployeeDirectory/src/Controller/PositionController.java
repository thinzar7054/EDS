package Controller;

import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Config.DBConfig;
import Model.PositionModel;

public class PositionController {
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

	public int insert(PositionModel pm) {
		int result = 0;
		String sql = "insert into empdirectory.position (post_id,postName) " + "values(?,?)";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, pm.getPost_id());
			ps.setString(2, pm.getPositionName());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Insert Fail,Inter error", "Fail", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}

	public String searchPostName(PositionModel pm) {
		String result = null;
		String sql = "select postName from empdirectory.position where post_id =?";
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, pm.getPost_id());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("postName");
			} else {
				System.out.println("This post is not found");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String searchPostId(PositionModel pm) {
		String result = null;
		String sql = "select post_id from empdirectory.position where postName=?";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, pm.getPositionName());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getString("post_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean isduplicate(PositionModel pm) throws SQLException {
		boolean duplicate = false;
		String sql = "select * from empdirectory.position where postName=?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, pm.getPositionName());
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			duplicate = true;
		} else {
			duplicate = false;
		}
		return duplicate;
	}
}
