package org.db.manager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Query {

	private final static String[] RESULT_QUERY_PREFIX = { "SELECT", "SHOW" };

	private Connection connection;
	protected Statement statement;
	protected ResultSet resultSet;
	protected String sqlQuery;

	protected Query(String connUrl, String username, String passwd,
			String sqlQuery) {
		this.init(connUrl, username, passwd, sqlQuery);
	}

	private void init(String connUrl, String username, String passwd,
			final String sqlQuery) {
		try {
			this.connection = DriverManager.getConnection(connUrl, username,
					passwd);
			this.statement = this.connection.createStatement();
			this.sqlQuery = sqlQuery;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Cannot open connection");
		}
	}

	static Query createQuery(String connUrl, String username, String passwd,
			final String sqlQuery) {
		if (sqlQuery != null) {
			Query query = null;
			if (Util.startsWith(sqlQuery, RESULT_QUERY_PREFIX)) {
				query = new ResultQuery(connUrl, username, passwd, sqlQuery);
			} else {
				query = new UpdateQuery(connUrl, username, passwd, sqlQuery);
			}
			return query;
		} else {
			return null;
		}
	}

	public abstract Result execute();

	public final void close() {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException ex) {
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException ex) {
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException ex) {
			}
		}
	}

}
