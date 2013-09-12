package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.dao.UserContainer;

import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class UserList extends Table {
	
	public UserList(AuthenticatedScreen as){
		setSizeFull();
		setContainerDataSource(as.getUserSource());
		setVisibleColumns(UserContainer.NATURAL_COL_ORDER);
		setColumnHeaders(UserContainer.COL_HEADERS_ENGLISH);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);

		/*
		 * Make table selectable, react immediatedly to user events, and pass
		 * events to the controller (our main application)
		 */
		setSelectable(true);
		setImmediate(true);
		//addListener((ValueChangeListener) app);
		/* We don't want to allow users to de-select a row */
		setNullSelectionAllowed(false);
	}

}
