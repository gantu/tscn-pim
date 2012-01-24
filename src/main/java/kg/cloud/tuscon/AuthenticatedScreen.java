package kg.cloud.tuscon;

import kg.cloud.tuscon.dao.MembershipContainer;
import kg.cloud.tuscon.dao.OrganizationContainer;
import kg.cloud.tuscon.dao.PersonContainer;
import kg.cloud.tuscon.dao.SektorContainer;
import kg.cloud.tuscon.domain.SearchFilter;
import kg.cloud.tuscon.ui.HelpWindow;
import kg.cloud.tuscon.ui.ListView;
import kg.cloud.tuscon.ui.NavigationTree;
import kg.cloud.tuscon.ui.PersonForm;
import kg.cloud.tuscon.ui.PersonList;
import kg.cloud.tuscon.ui.SearchView;
import kg.cloud.tuscon.ui.SharingOptions;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.Notification;

public class AuthenticatedScreen extends VerticalLayout implements
		ValueChangeListener, ItemClickListener, ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HorizontalSplitPanel hSplitPanel = new HorizontalSplitPanel();
	private Button newContact;
	private Button search;
	private Button share;
	private Button help;
	private NavigationTree navTree = new NavigationTree();
	private ListView listView = null;
	private PersonForm personForm = null;
	private PersonList personList = null;
	private HelpWindow helpWindow = null;
	private SearchView searchView = null;
	private SharingOptions sharingOptions = null;
	MyVaadinApplication app;

	private PersonContainer dataSource = PersonContainer.getAllFromDB();
	private OrganizationContainer organizationsSource = OrganizationContainer
			.getAllFromDb();
	private MembershipContainer membershipSource = MembershipContainer
			.getAllFromDb();

	private SektorContainer sektorsSource = SektorContainer.getAllFromDb();
	
	public AuthenticatedScreen(MyVaadinApplication app) {
		this.app = app;

		newContact = new Button("Add contact");
		search = new Button("Search");
		share = new Button("Share");
		help = new Button("Help");

		this.addComponent(createToolbar());
		this.addComponent(hSplitPanel);
		this.setExpandRatio(hSplitPanel, 1);
		hSplitPanel.setSplitPosition(200, HorizontalSplitPanel.UNITS_PIXELS);
		app.getMainWindow().setContent(this);
		hSplitPanel.setFirstComponent(navTree);
		setMainComponent(getListView());

		navTree.addListener((ItemClickListener) this);
		this.setSizeFull();

	}

	public HorizontalLayout createToolbar() {
		HorizontalLayout toolBar = new HorizontalLayout();
		toolBar.addComponent(newContact);
		toolBar.addComponent(search);
		toolBar.addComponent(share);
		toolBar.addComponent(help);

		search.addListener((ClickListener) this);
		share.addListener((ClickListener) this);
		help.addListener((ClickListener) this);
		newContact.addListener((ClickListener) this);

		search.setIcon(new ThemeResource("icons/32/folder-add.png"));
		share.setIcon(new ThemeResource("icons/32/users.png"));
		help.setIcon(new ThemeResource("icons/32/help.png"));
		newContact.setIcon(new ThemeResource("icons/32/document-add.png"));

		toolBar.setMargin(true);
		toolBar.setSpacing(true);

		toolBar.setStyleName("toolbar");

		toolBar.setWidth("100%");

		Embedded em = new Embedded("", new ThemeResource("images/logo.png"));
		toolBar.addComponent(em);
		toolBar.setComponentAlignment(em, Alignment.MIDDLE_RIGHT);
		toolBar.setExpandRatio(em, 1);
		return toolBar;
	}

	private void setMainComponent(Component c) {
		hSplitPanel.setSecondComponent(c);
	}

	private void showListView() {
		setMainComponent(getListView());
	}

	private void showSearchView() {
		setMainComponent(getSearchView());
	}

	private void showShareWindow() {
		app.getMainWindow().addWindow(getSharingOptions());
	}

	private void showHelpWindow() {

		app.getMainWindow().addWindow(getHelpWindow());
	}

	private Component getSearchView() {
		if (searchView == null) {
			searchView = new SearchView(app, this);
		}
		return searchView;
	}

	private SharingOptions getSharingOptions() {
		if (sharingOptions == null) {
			sharingOptions = new SharingOptions();
		}
		return sharingOptions;
	}

	private ListView getListView() {
		if (listView == null) {
			personList = new PersonList(this);
			personList.addListener((ValueChangeListener) this);
			personForm = new PersonForm(this);
			listView = new ListView(personList, personForm);
		}

		return listView;
	}

	private HelpWindow getHelpWindow() {
		if (helpWindow == null) {
			helpWindow = new HelpWindow();
		}

		return helpWindow;
	}

	public void itemClick(ItemClickEvent event) {
		if (event.getSource() == navTree) {
			Object itemId = event.getItemId();
			if (itemId != null) {
				if (NavigationTree.SHOW_ALL.equals(itemId)) {
					// clear previous filters
					getDataSource().removeAllContainerFilters();
					showListView();
				} else if (NavigationTree.SEARCH.equals(itemId)) {
					showSearchView();
				} else if (itemId instanceof SearchFilter) {
					search((SearchFilter) itemId);
				}
			}
		}

	}

	public void search(SearchFilter searchFilter) {
		// clear previous filters
		getDataSource().removeAllContainerFilters();
		// filter contacts with given filter
		getDataSource().addContainerFilter(searchFilter.getPropertyId(),
				searchFilter.getTerm(), true, false);
		showListView();

		app.getMainWindow().showNotification(
				"Searched for " + searchFilter.getPropertyId() + "=*"
						+ searchFilter.getTerm() + "*, found "
						+ getDataSource().size() + " item(s).",
				Notification.TYPE_TRAY_NOTIFICATION);
	}

	public void valueChange(ValueChangeEvent event) {
		Property property = event.getProperty();
		if (property == personList) {
			Item item = personList.getItem(personList.getValue());
			if (item != personForm.getItemDataSource()) {
				personForm.setItemDataSource(item);
			}
		}
	}

	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();

		if (source == search) {
			showSearchView();
		} else if (source == help) {
			showHelpWindow();
		} else if (source == share) {
			showShareWindow();
		} else if (source == newContact) {
			addNewContanct();
		}

	}

	private void addNewContanct() {
		showListView();
		personForm.addContact();
	}

	public void saveSearch(SearchFilter searchFilter) {
		navTree.addItem(searchFilter);
		navTree.setParent(searchFilter, NavigationTree.SEARCH);
		// mark the saved search as a leaf (cannot have children)
		navTree.setChildrenAllowed(searchFilter, false);
		// make sure "Search" is expanded
		navTree.expandItem(NavigationTree.SEARCH);
		// select the saved search
		navTree.setValue(searchFilter);
	}

	public PersonContainer getDataSource() {
		return dataSource;
	}

	public OrganizationContainer getOrganizationsSource() {
		return organizationsSource;
	}

	public MembershipContainer getMembershipSource() {
		return membershipSource;
	}

	public SektorContainer getSektorsSource() {
		return sektorsSource;
	}

}
