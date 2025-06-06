package Controller;

import java.sql.*;
import java.util.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Config.DBConfig;
import Model.AddAdminModel;

public class AddAdminController {
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

	public int insert(AddAdminModel am) {
		int result = 0;
		String sql = "insert into empdirectory.admin (adm_id,admName,phone,email,hiringDate,isManager,isActive, isAgreement, dep_id, post_id, password) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ps.setString(1, am.getAdm_id());
			ps.setString(2, am.getAdmName());
			ps.setString(3, am.getPhone());
			ps.setString(4, am.getEmail());
			ps.setString(5, am.getHiringDate());
			ps.setBoolean(6, am.isManager());
			ps.setBoolean(7, am.isActive());
			ps.setBoolean(8, am.isAgreement());
			ps.setString(9, am.getDepId());
			ps.setString(10, am.getPostId());
			ps.setString(11, am.getPassword());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Insert Fail,Inter error", "Fail", JOptionPane.ERROR_MESSAGE);
		}
		return result;
	}
}
