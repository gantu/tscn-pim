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
		String query = "SELECT * FROM person_info;";
		persons = new ArrayList<Person>();
		PreparedStatement stat = dbCon.prepareStatement(query);
		ResultSet result = stat.executeQuery();
		while (result.next()) {
			persons.add(new Person(new Integer(result.getInt("id")), result
					.getString("first_name"), result.getString("last_name"),
					result.getString("gender"), result.getString("dob"), result
							.getString("email"), result
							.getString("second_email"), result
							.getString("phone"), result.getString("mobile"),
					result.getString("fax"), result.getString("street"), result
							.getInt("postal_code"), result.getString("city"),
					result.getString("company_type"), result
							.getString("sektor"), result.getString("website"),
					result.getString("foundation"), result
							.getString("membership"), result
							.getString("common")));
		}

	}

	public void execSQLUpdate(Person p) throws SQLException {
		String query = "UPDATE person_info SET first_name=?,last_name=?,gender=?,dob=?," +
				"email=?,second_email=?,phone=?,mobile=?,fax=?,street=?,postal_code=?," +
				"city=?,company_type=?,sektor=?,website=?,foundation=?,membership=?," +
				"common=? WHERE id=?";
		PreparedStatement stat = dbCon.prepareStatement(query);
		stat.setString(1, p.getFirstName());
		stat.setString(2, p.getLastName());
		stat.setString(3, p.getGender());
		stat.setString(4, p.getDob());
		stat.setString(5, p.getEmail());
		stat.setString(6, p.getSecondaryEmail());
		stat.setString(7, p.getPhoneNumber());
		stat.setString(8, p.getMobilePhoneNumber());
		stat.setString(9, p.getMobilePhoneNumber());
		stat.setString(10, p.getStreetAddress());
		stat.setInt(11, p.getPostalCode());
		stat.setString(12, p.getCity());
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
		stat.setString(4, p.getDob());
		stat.setString(5, p.getEmail());
		stat.setString(6, p.getSecondaryEmail());
		stat.setString(7, p.getPhoneNumber());
		stat.setString(8, p.getMobilePhoneNumber());
		stat.setString(9, p.getMobilePhoneNumber());
		stat.setString(10, p.getStreetAddress());
		stat.setInt(11, p.getPostalCode());
		stat.setString(12, p.getCity());
		stat.setString(13, p.getCompanyType());
		stat.setString(14, p.getSektor());
		stat.setString(15, p.getWebsiteUrl());
		stat.setString(16, p.getFoundation());
		stat.setString(17, p.getMembership());
		stat.setString(18, p.getCommon());
		stat.executeUpdate();
	}

	public void execSQLDelete(Person p) throws SQLException{
		String query="DELETE person_info FROM person_info WHERE id=?;";
		PreparedStatement statement = dbCon.prepareStatement(query);
        statement.setInt(1,p.getId());
        statement.executeUpdate();
	}
	public ArrayList<Person> getArray() {

		return persons;
	}

}
