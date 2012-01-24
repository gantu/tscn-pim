package kg.cloud.tuscon.dao;

import java.io.Serializable;
import java.util.ArrayList;

import kg.cloud.tuscon.domain.Sektor;

import com.vaadin.data.util.BeanItemContainer;

@SuppressWarnings("serial")
public class SektorContainer extends BeanItemContainer<Sektor> implements Serializable {

	public static final Object[] NATURAL_COL_ORDER = new Object[] {"sektorName"};

	public static final String[] COL_HEADERS_ENGLISH = new String[] {"Sektor Name" };

	public SektorContainer(){
		super(Sektor.class); 
	}
	
	public static SektorContainer getAllFromDb(){
		SektorContainer c=null;
		ArrayList<Sektor> sektorList=null;
		
		try {
			DbSektor dbSektor=new DbSektor();
			c=new SektorContainer();
			dbSektor.connect();
			dbSektor.execSQLSelectAll();
			sektorList=dbSektor.getSekotrs();
			dbSektor.close();
			for(int i=0;i<sektorList.size();i++){
				c.addItem(sektorList.get(i));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}
}
