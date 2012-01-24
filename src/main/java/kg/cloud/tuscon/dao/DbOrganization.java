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
			o.setId(result.getInt("id"));
			o.setOrgName(result.getString("name"));
			organizations.add(o);
		}
	}
	
	public ArrayList<Organization> getOrganizations(){
		return organizations;
	}
	
}
