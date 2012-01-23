package kg.cloud.tuscon.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class BaseDb {
	protected String query;
	protected Connection dbCon;
	protected DataSource pool;

	/* Creates a new instance of BaseDb */

	public BaseDb() throws Exception {
		Context env = null;
		try {
			env = (Context) new InitialContext().lookup("java:comp/env");
			pool = (DataSource) env.lookup("jdbc/iaauDB");
			if (pool == null) {
				throw new Exception("'IAAUDB' is unknown DataSource");
			}
		} catch (NamingException ne) {
			throw new Exception("...BaseDB... " + ne.getMessage());
		}
	}

	public boolean connect() throws ClassNotFoundException, SQLException,
			IOException {
		Class.forName("com.mysql.jdbc.Driver");
		dbCon = pool.getConnection();

		return true;
	}

	public Connection getConnection() throws ClassNotFoundException,
			SQLException, IOException {
		Class.forName("com.mysql.jdbc.Driver");
		dbCon = pool.getConnection();

		return dbCon;
	}

	public void close() throws SQLException {
		dbCon.close();
	}

}
