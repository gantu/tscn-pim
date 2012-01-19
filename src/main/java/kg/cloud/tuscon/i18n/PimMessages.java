package kg.cloud.tuscon.i18n;

import java.io.Serializable;
import java.util.ListResourceBundle;

public class PimMessages extends ListResourceBundle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String AppTitle = generateId();
	public static final String AppLanguageTurkish = generateId();
	public static final String AppLanguageRussian = generateId();
	
	public static final String Login = generateId();
	public static final String Username=generateId();
	public static final String Password = generateId();
	public static final String LoginButton = generateId();
	
	public static final String NewContact = generateId();
	public static final String SearchContact = generateId();
	public static final String ShareContact = generateId();
	public static final String Help =generateId();
	public static final String Save = generateId();
	public static final String Cancel = generateId();
	
	public static final String FirstName = generateId();
	public static final String LastName = generateId();
	public static final String Company = generateId();
	public static final String MobilePhone = generateId();
	public static final String WorkPhone = generateId();
	public static final String HomePhone = generateId();
	public static final String WorkEmail = generateId();
	public static final String HomeEmail = generateId();
	public static final String Street = generateId();
	public static final String Zip = generateId();
	public static final String City =generateId();
	public static final String State = generateId();
	public static final String Country =generateId();
	
	public static final String SearchTerm = generateId();
	public static final String SearchField = generateId();
	public static final String SearchSave = generateId();
	public static final String SearchName = generateId();
	

	@Override
	protected Object[][] getContents() {
		// TODO Auto-generated method stub
		return null;
	}
	
	static int ids = 0;
	private static String generateId() {
		return new Integer(ids++).toString();
	}


}
