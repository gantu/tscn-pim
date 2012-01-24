package kg.cloud.tuscon.dao;

import java.io.Serializable;
import java.util.ArrayList;

import kg.cloud.tuscon.domain.Organization;

import com.vaadin.data.util.BeanItemContainer;

@SuppressWarnings("serial")
public class OrganizationContainer extends BeanItemContainer<Organization> implements Serializable{

	public static final Object[] NATURAL_COL_ORDER = new Object[] {"orgName"};
	public static final String[] COL_HEADERS_ENGLISH = new String[] {"Organization" };
	
	public OrganizationContainer(){
		super(Organization.class);
	}
	
	public static OrganizationContainer getAllFromDb(){
		OrganizationContainer c=null;
		ArrayList<Organization> orgList=null;
		
		try {
			c=new OrganizationContainer();
			DbOrganization dbOrg=new DbOrganization();
			dbOrg.connect();
			dbOrg.execSelectAll();
			orgList=dbOrg.getOrganizations();
			dbOrg.close();
			for(int i=0;i<orgList.size();i++){
				c.addItem(orgList.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

}
