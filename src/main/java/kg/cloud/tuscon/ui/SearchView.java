package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.MyVaadinApplication;
import kg.cloud.tuscon.dao.PersonContainer;
import kg.cloud.tuscon.domain.SearchFilter;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window.Notification;

public class SearchView extends Panel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TextField tf;
	private NativeSelect fieldToSearch;
	private CheckBox saveSearch;
	private TextField searchName;
	private MyVaadinApplication app;
	private AuthenticatedScreen as;

	public SearchView(MyVaadinApplication app,AuthenticatedScreen as) {
		this.app = app;
		this.as=as;
		
		addStyleName("view");
		
		setCaption("Search contacts");
		setSizeFull();

		/* Use a FormLayout as main layout for this Panel */
		FormLayout formLayout = new FormLayout();
		setContent(formLayout);

		/* Create UI components */
		tf = new TextField("Search term");
		fieldToSearch = new NativeSelect("Field to search");
		saveSearch = new CheckBox("Save search");
		searchName = new TextField("Search name");
		Button search = new Button("Search");

		/* Initialize fieldToSearch */
		for (int i = 0; i < PersonContainer.NATURAL_COL_ORDER.length; i++) {
			fieldToSearch.addItem(PersonContainer.NATURAL_COL_ORDER[i]);
			fieldToSearch.setItemCaption(PersonContainer.NATURAL_COL_ORDER[i],
					PersonContainer.COL_HEADERS_ENGLISH[i]);
		}

		fieldToSearch.setValue("lastName");
		fieldToSearch.setNullSelectionAllowed(false);

		/* Initialize save checkbox */
		saveSearch.setValue(true);
		saveSearch.setImmediate(true);
		saveSearch.addListener(new ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				searchName.setVisible(event.getButton().booleanValue());
			}
		});

		search.addListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				performSearch();
			}

		});

		/* Add all the created components to the form */
		addComponent(tf);
		addComponent(fieldToSearch);
		addComponent(saveSearch);
		addComponent(searchName);
		addComponent(search);
	}

	private void performSearch() {
		String searchTerm = (String) tf.getValue();
		if (searchTerm == null || searchTerm.equals("")) {
			getWindow().showNotification("Search term cannot be empty!",
					Notification.TYPE_WARNING_MESSAGE);
			return;
		}

		SearchFilter searchFilter = new SearchFilter(fieldToSearch.getValue(),
				searchTerm, (String) searchName.getValue());
		if (saveSearch.booleanValue()) {
			if (searchName.getValue() == null
					|| searchName.getValue().equals("")) {
				getWindow().showNotification(
						"Please enter a name for your search!",
						Notification.TYPE_WARNING_MESSAGE);
				return;
			}
			as.saveSearch(searchFilter);
		}
		as.search(searchFilter);
	}
}
