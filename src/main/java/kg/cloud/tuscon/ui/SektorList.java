package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.dao.SektorContainer;

import com.vaadin.ui.Table;

@SuppressWarnings("serial")
public class SektorList extends Table {
	
	public SektorList(AuthenticatedScreen as){
		setSizeFull();
		setContainerDataSource(as.getSektorsSource());
		setVisibleColumns(SektorContainer.NATURAL_COL_ORDER);
		setColumnHeaders(SektorContainer.COL_HEADERS_ENGLISH);
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
