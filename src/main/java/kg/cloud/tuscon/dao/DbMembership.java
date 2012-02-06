package kg.cloud.tuscon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kg.cloud.tuscon.domain.Membership;

public class DbMembership extends BaseDb {

	private ArrayList<Membership> memberships=null;

	public DbMembership() throws Exception {
		super();
	}
	
	
	public void execSelectAll() throws SQLException{
		String query="SELECT id,unity_name FROM membership;";
		memberships=new ArrayList<Membership>();
		PreparedStatement stat = dbCon.prepareStatement(query);
		ResultSet result = stat.executeQuery();
		while(result.next()){
			Membership m=new Membership();
			m.setId(result.getString("id"));
			m.setUnityName(result.getString("unity_name"));
			memberships.add(m);
		}
	}
	
	public ArrayList<Membership> getMemberships(){
		return memberships;
	}
	

}
