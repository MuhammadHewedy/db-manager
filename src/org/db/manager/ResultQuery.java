package org.db.manager;

import java.sql.SQLException;

class ResultQuery extends Query {

	protected ResultQuery(String connUrl, String username, String passwd,
			String sqlQuery) {
		super(connUrl, username, passwd, sqlQuery);
	}

	@Override
	public Result execute() {
		try {
			Result result = new Result(Result.INVALID_UPDATE_RESULT,
					this.statement.executeQuery(sqlQuery));
			return result;
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return Result.INVALID_RESULT;
	}

}
