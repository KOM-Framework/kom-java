package core.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SQLConnect {

	private static String sqlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

	@SuppressWarnings("rawtypes")
	public static ArrayList<Map> executeQuery(String dbServer, String dbPort, String databaseName,
			String databaseUserName, String databasePassword, String sqlQuery) {
		ArrayList<Map> out = new ArrayList<Map>();
		Connection conn = null;
		String url = "jdbc:sqlserver://" + dbServer + ":" + dbPort + ";DatabaseName=" + databaseName;
		Statement stmt = null;
		ResultSet result = null;
		try {
			Class.forName(sqlDriver).newInstance();
			conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
			stmt = conn.createStatement();
			boolean found = false;
			result = stmt.executeQuery(sqlQuery);
			found = result.isBeforeFirst();
			if (found) {
				while (result.next()) {
					Map<String, Object> map = new HashMap<String, Object>();
					ResultSetMetaData rsmd = result.getMetaData();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String columName = rsmd.getColumnName(i);
						map.put(columName, result.getString(columName));
					}
					out.add(map);
				}
			}
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}

	public static int executeInsert(String dbServer, String dbPort, String databaseName, String databaseUserName,
			String databasePassword, String sqlInsert) {
		int newKey = -1;
		Connection conn = null;
		String url = "jdbc:sqlserver://" + dbServer + ":" + dbPort + ";DatabaseName=" + databaseName;
		PreparedStatement pstmt = null;
		ResultSet resultkeys = null;
		try {
			Class.forName(sqlDriver).newInstance();
			conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
			pstmt = conn.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);

			pstmt.executeUpdate();
			resultkeys = pstmt.getGeneratedKeys();

			if (resultkeys.next())
				newKey = resultkeys.getInt(1);

			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newKey;
	}

}
