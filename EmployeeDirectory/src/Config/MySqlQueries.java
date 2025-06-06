package Config;

import java.sql.*;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import Model.AddAdminModel;

public class MySqlQueries {
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

	public static void addCoboBox(String tableName, String columnName, JComboBox<String> comboBox) {
		String sql = "select " + columnName + " from " + tableName;
		try {
			PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			comboBox.removeAllItems();
			comboBox.addItem("-Select-");
			while (rs.next()) {
				String value = rs.getString(columnName);

				comboBox.addItem(value);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static String getDepName(String depId) throws SQLException {
		String depName = null;
		String sql = "select * from empdirectory.department where dep_id = ?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, depId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			depName = rs.getString(2);
		}
		return depName;
	}

	public static String getPostName(String postId) throws SQLException {
		String postName = null;
		String sql = "select * from empdirectory.position where post_id=?";
		PreparedStatement ps = (PreparedStatement) con.prepareStatement(sql);
		ps.setString(1, postId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			postName = rs.getString(2);
		}
		return postName;
	}
}
