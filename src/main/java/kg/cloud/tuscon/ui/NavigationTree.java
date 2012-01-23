package kg.cloud.tuscon.ui;

import com.vaadin.ui.Tree;

public class NavigationTree extends Tree{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	     public static final Object SHOW_ALL = "Show all";
	     public static final Object SEARCH = "Search";

	    public NavigationTree() {
	             addItem(SHOW_ALL);
	             addItem(SEARCH);
	             setChildrenAllowed(SHOW_ALL, false);

	     		/*
	     		 * We want items to be selectable but do not want the user to be able to
	     		 * de-select an item.
	     		 */
	     		setSelectable(true);
	     		setNullSelectionAllowed(false);
	     }
	

}
