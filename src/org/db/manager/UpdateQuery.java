package org.db.manager;

import java.sql.SQLException;

class UpdateQuery extends Query {

	protected UpdateQuery(String connUrl, String username, String passwd,
			String sqlQuery) {
		super(connUrl, username, passwd, sqlQuery);
	}

	@Override
	public Result execute() {
		try {
			Result result = new Result(this.statement.executeUpdate(sqlQuery),
					null);
			return result;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return Result.INVALID_RESULT;
	}

}
