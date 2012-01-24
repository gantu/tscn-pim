package kg.cloud.tuscon.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kg.cloud.tuscon.domain.Person;

public class DbPerson extends BaseDb {

	private ArrayList<Person> persons = null;

	public DbPerson() throws Exception {
		super();

	}

	public void execSQLSelectAll() throws SQLException {
		String query = "SELECT p.id, p.first_name,p.last_name,p.gender,p.dob,"
				+ "p.company,p.email,p.second_email,p.phone,mobile,p.fax,p.street,p.postal_code,"
				+ "p.company_type,p.sektor,p.website,p.foundation,m.unity_name,o.name,"
				+ "p.common FROM person_info as p left join membership as m on p.membership_id=m.id left join organization as o" +
				" on p.organization_id=o.id;";
		persons = new ArrayList<Person>();
		PreparedStatement stat = dbCon.prepareStatement(query);
		ResultSet result = stat.executeQuery();
		while (result.next()) {
			Person p=new Person();
			p.setId(new Integer(result.getInt("p.id")));
			p.setFirstName(result.getString("first_name"));
			p.setLastName(result.getString("p.last_name"));
			p.setGender(result.getString("gender"));
			p.setDob(result.getDate("p.dob"));
			p.setCompany(result.getString("p.company"));
			p.setEmail(result.getString("p.email"));
			p.setSecondaryEmail(result.getString("p.second_email"));
			p.setPhoneNumber(result.getString("p.phone"));
			p.setMobilePhoneNumber(result.getString("p.mobile"));
			p.setFaxNumber(result.getString("p.fax"));
			p.setStreetAddress(result.getString("p.street"));
			p.setPostalCode(result.getInt("p.postal_code"));
		    p.setCompanyType(result.getString("p.company_type"));
		    p.setSektor(result.getString("p.sektor"));
		    p.setOrganization(result.getString("o.name"));
		    p.setWebsiteUrl(result.getString("p.website"));
		    p.setFoundation(result	.getString("p.foundation"));
		    p.setMembership(result.getString("m.unity_name"));
		    p.setCommon(result.getString("common"));
		    persons.add(p);
		}
		
		
		

	}

	public void execSQLUpdate(Person p) throws SQLException {
		String query = "UPDATE person_info SET first_name=?,last_name=?,gender=?,dob=?,"
				+ "email=?,second_email=?,phone=?,mobile=?,fax=?,street=?,postal_code=?,"
				+ "city=?,company_type=?,sektor=?,website=?,foundation=?,membership=?,"
				+ "common=? WHERE id=?";
		PreparedStatement stat = dbCon.prepareStatement(query);
		stat.setString(1, p.getFirstName());
		stat.setString(2, p.getLastName());
		stat.setString(3, p.getGender());
		//stat.setString(4, p.getDob());
		stat.setString(5, p.getEmail());
		stat.setString(6, p.getSecondaryEmail());
		stat.setString(7, p.getPhoneNumber());
		stat.setString(8, p.getMobilePhoneNumber());
		stat.setString(9, p.getMobilePhoneNumber());
		stat.setString(10, p.getStreetAddress());
		stat.setInt(11, p.getPostalCode());
	
		stat.setString(13, p.getCompanyType());
		stat.setString(14, p.getSektor());
		stat.setString(15, p.getWebsiteUrl());
		stat.setString(16, p.getFoundation());
		stat.setString(17, p.getMembership());
		stat.setString(18, p.getCommon());
		stat.setInt(19, p.getId());
		stat.executeUpdate();

	}

	public void execSQLInsert(Person p) throws SQLException {
		String query = "INSERT INTO person_info VALUES('',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		PreparedStatement stat = dbCon.prepareStatement(query);
		stat.setString(1, p.getFirstName());
		stat.setString(2, p.getLastName());
		stat.setString(3, p.getGender());
		//stat.setString(4, p.getDob());
		stat.setString(5, p.getEmail());
		stat.setString(6, p.getSecondaryEmail());
		stat.setString(7, p.getPhoneNumber());
		stat.setString(8, p.getMobilePhoneNumber());
		stat.setString(9, p.getMobilePhoneNumber());
		stat.setString(10, p.getStreetAddress());
		stat.setInt(11, p.getPostalCode());
		stat.setString(13, p.getCompanyType());
		stat.setString(14, p.getSektor());
		stat.setString(15, p.getWebsiteUrl());
		stat.setString(16, p.getFoundation());
		stat.setString(17, p.getMembership());
		stat.setString(18, p.getCommon());
		stat.executeUpdate();
	}

	public void execSQLDelete(Person p) throws SQLException {
		String query = "DELETE person_info FROM person_info WHERE id=?;";
		PreparedStatement statement = dbCon.prepareStatement(query);
		statement.setInt(1, p.getId());
		statement.executeUpdate();
	}

	public ArrayList<Person> getArray() {

		return persons;
	}

}
