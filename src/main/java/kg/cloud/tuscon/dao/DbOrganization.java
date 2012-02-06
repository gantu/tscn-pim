package kg.cloud.tuscon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kg.cloud.tuscon.domain.Organization;

public class DbOrganization extends BaseDb {

	private ArrayList<Organization> organizations=null;
	
	public DbOrganization() throws Exception {
		super();
	}
	
	public void execSelectAll() throws SQLException{
		String query="SELECT id,name FROM organization;";
		organizations=new ArrayList<Organization>();
		PreparedStatement stat = dbCon.prepareStatement(query);
		ResultSet result = stat.executeQuery();
		while(result.next()){
			Organization o=new Organization();
			o.setId(result.getString("id"));
			o.setOrgName(result.getString("name"));
			organizations.add(o);
		}
	}
	
	public void execSQLInsert(Organization org) throws SQLException{
		
		String query="INSERT INTO organization VALUES(?,?);";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setString(1,org.getId());
		stat.setString(2,org.getOrgName());
		stat.execute();
	}
	
	public void execSQLUpdate(Organization org) throws SQLException{
		String query="UPDATE organization SET name=? WHERE id=?";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setString(1,org.getOrgName());
		stat.setString(2,org.getId());
		stat.executeUpdate();
	}
	
	public void execSQLDelete(Organization org) throws SQLException{
		String query="DELETE organization FROM organization WHERE id=?;";
		PreparedStatement stat=dbCon.prepareStatement(query);
		stat.setString(1,org.getId());
		stat.execute();
	}
	public ArrayList<Organization> getOrganizations(){
		return organizations;
	}
	
}
