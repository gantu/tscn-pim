package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.MyVaadinApplication;

import com.vaadin.ui.Tree;

public class NavigationTree extends Tree{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MyVaadinApplication app;
	     public static final Object SHOW_ALL = "Show all";
	     public static final Object SEARCH = "Search";

	    public NavigationTree() {
	             addItem(SHOW_ALL);
	             addItem(SEARCH);
	     }
	

}
