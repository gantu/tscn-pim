package kg.cloud.tuscon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import kg.cloud.tuscon.domain.Sektor;


public class DbSektor extends BaseDb {

	private ArrayList<Sektor> sektors=null;
	public DbSektor() throws Exception {
		super();

	}
	
	
	public void execSQLSelectAll() throws SQLException{
		String query="SELECT id,name FROM sektor;";
		sektors=new ArrayList<Sektor>();
		PreparedStatement stat = dbCon.prepareStatement(query);
		ResultSet result = stat.executeQuery();
		
		while(result.next()){
			Sektor s=new Sektor();
			s.setId(result.getInt("id"));
			s.setSektorName(result.getString("name"));
			sektors.add(s);
		}
		
		
	}
	
	public ArrayList<Sektor> getSekotrs(){
		return sektors;
	} 

}
