package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.dao.PersonContainer;

import com.vaadin.ui.Table;

public class PersonList extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private AuthenticatedScreen as;
	public PersonList(AuthenticatedScreen as){
		this.as=as;
		setSizeFull();
		setContainerDataSource(as.getDataSource());

		setVisibleColumns(PersonContainer.NATURAL_COL_ORDER);
		setColumnHeaders(PersonContainer.COL_HEADERS_ENGLISH);

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
