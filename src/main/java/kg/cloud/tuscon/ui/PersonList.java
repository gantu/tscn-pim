package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.MyVaadinApplication;
import kg.cloud.tuscon.dao.PersonContainer;
import kg.cloud.tuscon.domain.Person;

import com.vaadin.terminal.ExternalResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.Link;
import com.vaadin.ui.Table;

public class PersonList extends Table {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	MyVaadinApplication app;
	public PersonList(MyVaadinApplication app){
		this.app=app;
		setSizeFull();
		setContainerDataSource(app.getDataSource());

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

		// customize email column to have mailto: links using column generator
		addGeneratedColumn("email", new ColumnGenerator() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public Component generateCell(Table source, Object itemId,
					Object columnId) {
				Person p = (Person) itemId;
				Link l = new Link();
				l.setResource(new ExternalResource("mailto:" + p.getEmail()));
				l.setCaption(p.getEmail());
				return l;
			}
		});
	}
		
	
}
