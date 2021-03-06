package com.alelma.dao;

import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

/*
This is an example of how to implement and use DataStore with Apache DbUtils.
Web-site: http://sqldalmaker.sourceforge.net
Contact: sqldalmaker@gmail.com
Copy-paste this code to your project and change it for your needs.
*/
public class DataStoreManager {

	private Connection connection;

	public void open() throws Exception {

		// Or add JDBC driver to the project build-path:
		// Class.forName("org.h2.Driver").newInstance();
		InitialContext cxt = new InitialContext();
		DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/postgres");

		if (ds == null) {
			throw new Exception("Veri Tabanı Bağlantısı başarısız!");
		}

		connection = ds.getConnection();
		connection.setAutoCommit(false);
	}

	public void commit() throws Exception {
		connection.commit();
	}

	public void rollback() throws Exception {
		connection.rollback();
	}

	public void close() throws Exception {

		// connection.setAutoCommit(true) is required for
		// org.apache.derby.jdbc.ClientDriver to prevent
		// java.sql.SQLException: Cannot close a connection while a transaction
		// is still active.

		connection.setAutoCommit(true);

		connection.close();
	}

	private final MyDataStore ds = new MyDataStore();

	// //////////////////////////////////////////////////
	//
	// MyDataStore is hidden, use factory method pattern:

	// public OrderDao createOrderDao() {
	// return new OrderDao(ds);
	// }

	public ArizaDao createArizaDao() {
		return new ArizaDao(ds);
	}

	private class MyDataStore extends DataStore {

		protected final QueryRunner2 queryRunner;

		public MyDataStore() {
			queryRunner = new QueryRunner2(false /* pmdKnownBroken */);
		}

		private boolean isStringValue(Class<?> inValueType) {
			// Consider any CharSequence (including StringBuffer and
			// StringBuilder) as a String.
			return (CharSequence.class.isAssignableFrom(inValueType)
					|| StringWriter.class.isAssignableFrom(inValueType));
		}

		private boolean isDateValue(Class<?> inValueType) {
			return (java.util.Date.class.isAssignableFrom(inValueType)
					&& !(java.sql.Date.class.isAssignableFrom(inValueType)
							|| java.sql.Time.class.isAssignableFrom(inValueType)
							|| java.sql.Timestamp.class.isAssignableFrom(inValueType)));
		}

		protected void prepareParams(Object... params) {

			for (int i = 0; i < params.length; i++) {

				if (params[i] != null) {

					if (isStringValue(params[i].getClass())) {
						params[i] = params[i].toString();
					} else if (isDateValue(params[i].getClass())) {
						params[i] = new java.sql.Timestamp(((java.util.Date) params[i]).getTime());
					}
				}
			}
		}

		/**
		 * QueryRunner2 allows to get generated keys after INSERT.
		 * http://stackoverflow.com/questions/8705036/how-to-get-generated-keys-with-commons-dbutils
		 * https://issues.apache.org/jira/browse/DBUTILS-54
		 */
		class QueryRunner2 extends QueryRunner {

			public QueryRunner2(boolean pmdKnownBroken) {
				super(pmdKnownBroken);
			}

			public int insert(String sql, String[] genColNames, Object[] genValues, Object... params)
					throws SQLException {

				PreparedStatement stmt = null;
				int rows = 0;

				try {

					stmt = connection.prepareStatement(sql, genColNames);
					super.fillStatement(stmt, params);
					rows = stmt.executeUpdate();

					doAfterInsert(stmt, genValues);

				} catch (SQLException e) {
					super.rethrow(e, sql, params);

				} finally {
					DbUtils.close(stmt);
				}

				return rows;
			}

			private void doAfterInsert(PreparedStatement stmt, Object[] genValues) throws SQLException {

				ResultSet keys = stmt.getGeneratedKeys();

				if (keys != null) {

					try {

						int i = 0;

						while (keys.next()) {

							// UNCOMMENT THE LINE WHICH IS WORKING WITH YOUR JDBC DRIVER

							// keys.getBigDecimal(1) works with most of tested
							// drivers (except SQLite)

							Object obj = keys.getBigDecimal(1);

							// keys.getObject(1) works with all tested drivers,
							// but it can return Long for Integer column (MySQL)

							// Object obj = keys.getObject(1);

							genValues[i] = obj;

							i++;
						}

					} finally {

						DbUtils.close(keys);
					}
				}
			}
		}

		@Override
		public <T> T castGeneratedValue(Class<T> type, Object obj) {

			// YOU CAN CHANGE/SIMPLIFY THIS METHOD IF IT IS POSSIBLE WITH YOUR JDBC DRIVER

			// For many drivers (SQL Server 2008, Derby), keys.getObject(1)
			// returns BigDecimal independently of type of column

			if (obj instanceof BigDecimal) {

				BigDecimal bigDecimal = (BigDecimal) obj;

				if (Byte.class.equals(type)) {

					obj = bigDecimal.byteValueExact();

				} else if (Float.class.equals(type)) {
					// there is no 'exact' version
					obj = bigDecimal.floatValue();

				} else if (Double.class.equals(type)) {
					// there is no 'exact' version
					obj = bigDecimal.doubleValue();

				} else if (Integer.class.equals(type)) {

					obj = bigDecimal.intValueExact();

				} else if (Long.class.equals(type)) {

					obj = bigDecimal.longValueExact();

				} else if (BigInteger.class.equals(type)) {

					obj = bigDecimal.toBigIntegerExact();

				} else if (BigDecimal.class.equals(type)) {

					obj = bigDecimal;

				} else if (Object.class.equals(type)) {

					obj = bigDecimal;

				} else {

					throw new ClassCastException("Unexpected class '" + type.getName() + "'");
				}
			}

			// cast:

			// Throws:
			// ClassCastException - if the object is not null and is not
			// assignable
			// to the type T.

			return type.cast(obj);
		}

		@Override
		public int insert(String sql, String[] genColNames, Object[] genValues, Object... params) throws SQLException {

			prepareParams(params);

			return queryRunner.insert(sql, genColNames, genValues, params);
		}

		@Override
		public int execDML(String sql, Object... params) throws Exception {

			prepareParams(params);

			return queryRunner.update(connection, sql, params);
		}

		@Override
		public <T> T query(final Class<T> type, String sql, Object... params) throws Exception {

			ResultSetHandler<T> h = new ResultSetHandler<T>() {

				@Override
				public T handle(final ResultSet rs) throws SQLException {

					if (!rs.next()) {
						return null;
					}

					// the first column is 1
					T res = type.cast(rs.getObject(1));

					if (rs.next()) {
						throw new SQLException("More than 1 row available");
					}

					return res;
				}
			};

			prepareParams(params);

			return queryRunner.query(connection, sql, h, params);
		}

		@Override
		public <T> List<T> queryList(final Class<T> type, String sql, Object... params) throws Exception {

			final ArrayList<T> res = new ArrayList<T>();

			ResultSetHandler<Void> h = new ResultSetHandler<Void>() {

				@Override
				public Void handle(final ResultSet rs) throws SQLException {

					while (rs.next()) {
						// the first column is 1
						T t = type.cast(rs.getObject(1));
						res.add(t);
					}

					return null;
				}
			};

			prepareParams(params);

			queryRunner.query(connection, sql, h, params);

			return res;
		}

		@Override
		public <T> T queryDto(String sql, final RowHandler<T> rowHandler, Object... params) throws Exception {

			ResultSetHandler<T> h = new ResultSetHandler<T>() {

				@Override
				public T handle(final ResultSet rs) throws SQLException {

					if (!rs.next()) {
						return null;
					}

					RowData vr = new RowData() {

						public <V> V getValue(Class<V> type, String columnLabel) throws Exception {

							return type.cast(rs.getObject(columnLabel));
						}
					};

					T res;

					try {
						res = rowHandler.handleRow(vr);
					} catch (Exception e) {
						throw new SQLException(e);
					}

					if (rs.next()) {
						throw new SQLException("More than 1 row available");
					}

					return res;
				}
			};

			prepareParams(params);

			return queryRunner.query(connection, sql, h, params);
		}

		@Override
		public <T> List<T> queryDtoList(String sql, final RowHandler<T> rowHandler, Object... params) throws Exception {

			final ArrayList<T> res = new ArrayList<T>();

			ResultSetHandler<Void> h = new ResultSetHandler<Void>() {

				@Override
				public Void handle(final ResultSet rs) throws SQLException {

					RowData vr = new RowData() {

						public <V> V getValue(Class<V> type, String columnLabel) throws Exception {

							return type.cast(rs.getObject(columnLabel));
						}
					};

					while (rs.next()) {

						try {
							T t = rowHandler.handleRow(vr);
							res.add(t);
						} catch (Exception e) {
							throw new SQLException(e);
						}
					}

					return null;
				}
			};

			prepareParams(params);

			queryRunner.query(connection, sql, h, params);

			return res;
		}
	}
}