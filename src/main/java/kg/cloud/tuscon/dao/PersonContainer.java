package kg.cloud.tuscon.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import kg.cloud.tuscon.domain.Person;

import com.vaadin.data.util.BeanItemContainer;

@SuppressWarnings("serial")
public class PersonContainer extends BeanItemContainer<Person> implements
		Serializable {

	public static final Object[] NATURAL_COL_ORDER = new Object[] {
			"firstName", "lastName", "gender", "dob", "company", "email",
			"secondaryEmail", "phoneNumber", "mobilePhoneNumber", "faxNumber",
			"streetAddress","companyType", "websiteUrl",
			"sektor", "organization", "foundation", "membership", "common" };

	public static final String[] COL_HEADERS_ENGLISH = new String[] {
			"First name", "Last name","Gender","Date Of Birth","Company","E-mail","2nd e-mail","Phone number","Mobile","Fax",
			"Street Address","Company Type","Website","sektor","Organization","Foundation","Membership","Common" };

	public PersonContainer() throws InstantiationException,
			IllegalAccessException {
		super(Person.class);
	}

	public static PersonContainer createWithTestData() {
		final String[] fnames = { "Peter", "Alice", "Joshua", "Mike", "Olivia",
				"Nina", "Alex", "Rita", "Dan", "Umberto", "Henrik", "Rene",
				"Lisa", "Marge" };
		final String[] lnames = { "Smith", "Gordon", "Simpson", "Brown",
				"Clavel", "Simons", "Verne", "Scott", "Allison", "Gates",
				"Rowling", "Barks", "Ross", "Schneider", "Tate" };
		final String cities[] = { "Amsterdam", "Berlin", "Helsinki",
				"Hong Kong", "London", "Luxemburg", "New York", "Oslo",
				"Paris", "Rome", "Stockholm", "Tokyo", "Turku" };
		final String streets[] = { "4215 Blandit Av.", "452-8121 Sem Ave",
				"279-4475 Tellus Road", "4062 Libero. Av.", "7081 Pede. Ave",
				"6800 Aliquet St.", "P.O. Box 298, 9401 Mauris St.",
				"161-7279 Augue Ave", "P.O. Box 496, 1390 Sagittis. Rd.",
				"448-8295 Mi Avenue", "6419 Non Av.",
				"659-2538 Elementum Street", "2205 Quis St.",
				"252-5213 Tincidunt St.", "P.O. Box 175, 4049 Adipiscing Rd.",
				"3217 Nam Ave", "P.O. Box 859, 7661 Auctor St.",
				"2873 Nonummy Av.", "7342 Mi, Avenue",
				"539-3914 Dignissim. Rd.", "539-3675 Magna Avenue",
				"Ap #357-5640 Pharetra Avenue", "416-2983 Posuere Rd.",
				"141-1287 Adipiscing Avenue", "Ap #781-3145 Gravida St.",
				"6897 Suscipit Rd.", "8336 Purus Avenue", "2603 Bibendum. Av.",
				"2870 Vestibulum St.", "Ap #722 Aenean Avenue",
				"446-968 Augue Ave", "1141 Ultricies Street",
				"Ap #992-5769 Nunc Street", "6690 Porttitor Avenue",
				"Ap #105-1700 Risus Street",
				"P.O. Box 532, 3225 Lacus. Avenue", "736 Metus Street",
				"414-1417 Fringilla Street", "Ap #183-928 Scelerisque Road",
				"561-9262 Iaculis Avenue" };
		PersonContainer c = null;
		Random r = new Random(0);
		try {
			c = new PersonContainer();
			for (int i = 0; i < 100; i++) {
				Person p = new Person();
				p.setFirstName(fnames[r.nextInt(fnames.length)]);
				p.setLastName(lnames[r.nextInt(lnames.length)]);
				p.setEmail(p.getFirstName().toLowerCase() + "."
						+ p.getLastName().toLowerCase() + "@vaadin.com");
				p.setPhoneNumber("+358 02 555 " + r.nextInt(10) + r.nextInt(10)
						+ r.nextInt(10) + r.nextInt(10));
				int n = r.nextInt(100000);
				if (n < 10000) {
					n += 10000;
				}
				//p.setPostalCode(n);
				p.setStreetAddress(streets[r.nextInt(streets.length)]);
				c.addItem(p);
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return c;
	}

	public static PersonContainer getAllFromDB() {
		PersonContainer c = null;
		ArrayList<Person> personList = null;

		try {
			c = new PersonContainer();
			DbPerson dbPerson = new DbPerson();
			dbPerson.connect();
			dbPerson.execSQLSelectAll();
			personList = dbPerson.getArray();
			dbPerson.close();
			for (int i = 0; i < personList.size(); i++) {
				c.addItem(personList.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

}
