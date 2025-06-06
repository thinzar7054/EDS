package Config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBConfig {
	private static DBConfig instance;
	private final String CONNECTION = "jdbc:mysql://localhost:3306/empdirectory";
	private final String USERNAME = "root";
	private final String PASSWORD = "";
	private Connection con = null;

	public DBConfig() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to load MySQL JDBC driver");
		}
	}

	public static synchronized DBConfig getInstance() {
		if (instance == null) {
			instance = new DBConfig();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		if (con == null || con.isClosed()) {
			con = DriverManager.getConnection(CONNECTION, USERNAME, PASSWORD);
		}
		return con;
	}

	public void closeConnection() {
		try {
			if (con != null && !con.isClosed()) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ResultSet executeQuery(String query) throws SQLException {
		Statement stm = getConnection().createStatement();
		return stm.executeQuery(query);
	}

	public String getPrimaryKey(String field, String table, String prefix) throws SQLException {
		String query = "SELECT " + field + " FROM " + table + " ORDER BY " + field + " DESC LIMIT 1";
		ResultSet rs = executeQuery(query);

		if (rs.next()) {
			String lastId = rs.getString(field);
			int current = Integer.parseInt(lastId.substring(prefix.length())) + 1;
			return String.format("%s%05d", prefix, current);
		}
		return prefix + "00001";
	}

}