package kg.cloud.tuscon.dao;

import java.io.Serializable;
import java.util.ArrayList;

import kg.cloud.tuscon.domain.User;

import com.vaadin.data.util.BeanItemContainer;

@SuppressWarnings("serial")
public class UserContainer extends BeanItemContainer<User> implements Serializable {

	public static final Object[] NATURAL_COL_ORDER = new Object[] {"username","password","roles"};

	public static final String[] COL_HEADERS_ENGLISH = new String[] {"Username","Password","Roles" };
	
	public UserContainer(){
		super(User.class);
	}
	
	public static UserContainer getAllFromDb() {
		UserContainer c=null;
		ArrayList<User> users=null;
		try {
			c=new UserContainer();
			DbUser dbUser=new DbUser();
			dbUser.connect();
			dbUser.execSQLSSelectAll();
			users=dbUser.getArray();
			dbUser.close();
			for(int i=0;i<users.size();i++){
				c.addItem(users.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return c;
	}

}
