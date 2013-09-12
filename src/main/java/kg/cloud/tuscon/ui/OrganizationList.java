package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.dao.OrganizationContainer;

import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class OrganizationList extends Table{
	
	public OrganizationList(AuthenticatedScreen as){
		setSizeFull();
		setContainerDataSource(as.getOrganizationsSource());
		setVisibleColumns(OrganizationContainer.NATURAL_COL_ORDER);
		setColumnHeaders(OrganizationContainer.COL_HEADERS_ENGLISH);
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
