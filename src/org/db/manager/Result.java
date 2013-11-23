package org.db.manager;

import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	static final int INVALID_UPDATE_RESULT = -1;

	private int resultCount = INVALID_UPDATE_RESULT;
	private ResultSet resultSet;

	static Result INVALID_RESULT = new Result();

	Result(int resultCount, ResultSet resultSet) {
		super();
		this.resultCount = resultCount;
		this.resultSet = resultSet;
	}

	private Result() {
	}

	public int getResultCount() {
		return resultCount;
	}

	public ResultSet getResultSet() {
		return resultSet;
	}

	public void print(OutputStream out) {
		if (out != null) {
			ResultType resultType = getResultType();
			if (resultType != null) {
				if (resultType == ResultType.UPDATE) {
					writeString(out, this.resultCount + "rows affected.\n");
				} else {
					try {
						writeReusltSet(out);
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			} else {
				writeString(out, "No result, see previous error.\n");
			}
		}
	}

	private void writeString(OutputStream out, String string) {
		try {
			if (out != null && string != null) {
				out.write(string.getBytes("UTF-8"));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void writeReusltSet(OutputStream out) throws SQLException {
		int colsCount = this.resultSet.getMetaData().getColumnCount();

		writeColumnHeaders(out, colsCount);
		writeDashs(out, colsCount);

		while (resultSet.next()) {
			for (int i = 1; i <= colsCount; i++) {
				writeString(out, resultSet.getObject(i) + "|");
			}
			writeString(out, "\n");
		}
		writeString(out, "\n");
	}

	private void writeColumnHeaders(OutputStream out, int colsCount)
			throws SQLException {
		for (int i = 1; i <= colsCount; i++) {
			writeString(out, this.resultSet.getMetaData().getColumnName(i)
					+ "|");
		}
		writeString(out, "\n");
	}

	private void writeDashs(OutputStream out, int colsCount)
			throws SQLException {

		for (int i = 1; i <= colsCount; i++) {
			writeString(
					out,
					Util.dashs(this.resultSet.getMetaData().getColumnName(i)
							.length())
							+ "|");
		}
		writeString(out, "\n");
	}

	public ResultType getResultType() {
		if (resultSet != null) {
			return ResultType.QUERY;
		} else if (resultCount != INVALID_UPDATE_RESULT) {
			return ResultType.UPDATE;
		}
		return null;
	}

	public static enum ResultType {
		QUERY, UPDATE;
	}
}
