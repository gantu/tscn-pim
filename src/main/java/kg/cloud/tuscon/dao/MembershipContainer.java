package kg.cloud.tuscon.dao;

import java.io.Serializable;
import java.util.ArrayList;

import kg.cloud.tuscon.domain.Membership;

import com.vaadin.data.util.BeanItemContainer;

@SuppressWarnings("serial")
public class MembershipContainer extends BeanItemContainer<Membership> implements Serializable{
	
	public static final Object[] NATURAL_COL_ORDER = new Object[] {"id","unityName"};

	public static final String[] COL_HEADERS_ENGLISH = new String[] {"ID","Dernek Name" };

	public MembershipContainer (){
		super(Membership.class);
	}
	
	public static MembershipContainer getAllFromDb(){
		MembershipContainer c=null;
		ArrayList<Membership> memList=null;
		try {
			c=new MembershipContainer();
			DbMembership dbMem=new DbMembership();
			dbMem.connect();
			dbMem.execSelectAll();
			memList=dbMem.getMemberships();
			dbMem.close();
			
			for(int i=0;i<memList.size();i++){
				c.addBean(memList.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
		
		
	}

	

}
