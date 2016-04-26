package core.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class General {

	public static void check(String... args) {
		int iStart = (int) System.currentTimeMillis();

		ExecutorService es = Executors.newCachedThreadPool();
		for (int i = 0; i < args.length; i++) {
			Map<String, String> confings = getConfigsFromString(args[i]);
			es.execute(new Runnable() {
				protected String sqlDriver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
				protected String dbIpAddress;
				protected String dbPort;
				protected String databaseName;
				protected String databaseUserName;
				protected String databasePassword;
				protected String sqlQuery;
				protected String maxWaitTime;

				private Runnable init(String dbIpAddress, String dbPort, String databaseName, String databaseUserName,
						String databasePassword, String sqlQuery, String maxWaitTime) {
					this.dbIpAddress = dbIpAddress;
					this.dbPort = dbPort;
					this.databaseName = databaseName;
					this.databaseUserName = databaseUserName;
					this.databasePassword = databasePassword;
					this.sqlQuery = sqlQuery;
					this.maxWaitTime = maxWaitTime;
					return this;
				}

				public void run() {
					this.getData();
				}

				public void getData() {
					System.out.println("CONNECTION THREAD IP:" + dbIpAddress);
					Connection conn = null;
					String url = "jdbc:sqlserver://" + dbIpAddress + ":" + dbPort + ";DatabaseName=" + databaseName;
					Statement stmt = null;
					ResultSet result = null;
					try {
						Class.forName(sqlDriver).newInstance();
						conn = DriverManager.getConnection(url, databaseUserName, databasePassword);
						stmt = conn.createStatement();
						boolean found = false;
						long startTime = System.currentTimeMillis();
						long maxTime = Long.parseLong(maxWaitTime);
						while (System.currentTimeMillis() - startTime < maxTime) {
							result = stmt.executeQuery(sqlQuery);
							found = result.isBeforeFirst();
							if (found) {
								// System.out.println("FOUND IN IP:" +
								// dbIpAddress);
								// while (result.next()) {
								// ResultSetMetaData rsmd = result
								// .getMetaData();

								// Log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
								// for (int i = 1; i <= rsmd.getColumnCount();
								// i++) {
								// String columName = rsmd.getColumnName(i);
								// System.out.println(columName + " "+
								// result.getString(columName));
								// Log.info(columName + " "+
								// result.getString(columName));
							}
							// Log.info("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
						}
						// break;
						// }else{
						// Thread.sleep(10);
						// }
						// }
						if (!found) {
							// System.out.println("NOT FOUND IN IP:" +
							// dbIpAddress + " AFTER " + maxWaitTime + " ms");
						}
						conn.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}.init(confings.get("dbIpAddress"), confings.get("dbPort"), confings.get("databaseName"),
					confings.get("databaseUserName"), confings.get("databasePassword"), confings.get("sqlQuery"),
					confings.get("maxWaitTime")));
		}
		es.shutdown();
		try {
			es.awaitTermination(1, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int iEnd = (int) System.currentTimeMillis();
		System.out.println(iEnd - iStart);
	}

	public static Map<String, String> getConfigsFromString(String in) {
		String[] configList = in.split("~");
		Map<String, String> confings = new HashMap<String, String>();
		for (String s : configList) {
			int i = s.indexOf("^");
			confings.put(s.substring(0, i), s.substring(i + 1, s.length()));
		}
		return confings;
	}

}