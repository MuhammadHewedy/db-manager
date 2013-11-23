package org.db.manager;

public class Manager {

	private static String CONN_URL;
	private static String USER_NAME;
	private static String PASSWD;

	public static void initJDBCDriver(String clazzName, String connURL,
			String username, String passwd) {
		try {
			Class.forName(clazzName);
			Manager.CONN_URL = connURL;
			Manager.USER_NAME = username;
			Manager.PASSWD = passwd;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public static Query createQuery(final String sqlQuery) {
		return Query.createQuery(CONN_URL, USER_NAME, PASSWD, sqlQuery);
	}
}
