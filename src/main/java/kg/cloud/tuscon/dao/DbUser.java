package kg.cloud.tuscon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kg.cloud.tuscon.domain.User;

public class DbUser extends BaseDb {

	private ArrayList<User> userList;

	public DbUser() throws Exception {
		super();
	}
	
	public void execSQLInsert(User u) throws SQLException{
		String query="INSERT INTO users VALUES(?,?);";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setString(1, u.getUsername());
		stat.setString(2, u.getPassword());
		stat.execute();
		insertRole(u,"admin");
	}

	private void insertRole(User u,String role) throws SQLException {
		String query="INSERT INTO user_roles(role_name,username) values(?,?);";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setString(1, role);
		stat.setString(2, u.getUsername());
		stat.execute();
	}

	public void execSQLSSelectAll() throws SQLException {
		String query = "SELECT u.username ,u.password,r.role_name FROM users as u,user_roles as r WHERE u.username=r.username";
		PreparedStatement stat = dbCon.prepareStatement(query);
		ResultSet result = stat.executeQuery();
		userList = new ArrayList<User>();
		while (result.next()) {
			User u = new User();
			u.setUsername(result.getString("u.username"));
			u.setPassword(result.getString("u.password"));
			u.setRoles(result.getString("r.role_name"));
			userList.add(u);
		}
	}

	public void execSQLUpdate(User u) throws SQLException{
		String query="UPDATE users SET password=? WHERE username=?";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setString(1, u.getPassword());
		stat.setString(2, u.getUsername());
		stat.execute();		
	}
	public void execSQLDelete(User u) throws SQLException{
		String query="DELETE users FROM users WHERE username=? and password=?";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setString(1,u.getUsername());
		stat.setString(2, u.getPassword());
		stat.execute();
	}
	public ArrayList<User> getArray() {
		return userList;
	}
}