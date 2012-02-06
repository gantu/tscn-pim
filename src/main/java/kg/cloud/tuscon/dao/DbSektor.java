package kg.cloud.tuscon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import kg.cloud.tuscon.domain.Sektor;

public class DbSektor extends BaseDb {

	private ArrayList<Sektor> sektors = null;

	public DbSektor() throws Exception {
		super();

	}

	public void execSQLSelectAll() throws SQLException {
		String query = "SELECT id,name FROM sektor;";
		sektors = new ArrayList<Sektor>();
		PreparedStatement stat = dbCon.prepareStatement(query);
		ResultSet result = stat.executeQuery();

		while (result.next()) {
			Sektor s = new Sektor();
			s.setId(result.getInt("id"));
			s.setSektorName(result.getString("name"));
			sektors.add(s);
		}

	}
	public void execSQLInsert(Sektor s) throws SQLException{
		String query="INSERT INTO sektor VALUES(?,?)";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setInt(1,s.getId());
		stat.setString(2, s.getSektorName());
		stat.execute();
	}

	public void execSQLUpdate(Sektor s) throws SQLException{
		String query="UPDATE sektor SET name=? WHERE id=?";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setString(1, s.getSektorName());
		stat.setInt(2,s.getId());
		stat.executeUpdate();
	}
	
	public void execSQLDelete(Sektor s) throws SQLException{
		String query="DELETE sektor FROM sektor WHERE id=?";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setInt(1, s.getId());
		stat.execute();
	}
	public ArrayList<Sektor> getSekotrs() {
		return sektors;
	}

}
