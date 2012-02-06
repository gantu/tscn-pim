package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.MyVaadinApplication;
import kg.cloud.tuscon.UserForm;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.VerticalSplitPanel;
import com.vaadin.ui.Window;

public class HelpWindow extends Window implements ValueChangeListener,
		ItemClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HorizontalSplitPanel hPanel = new HorizontalSplitPanel();
	private VerticalSplitPanel vPanel = new VerticalSplitPanel();
	private Tree tree = new Tree("Settings");
	public static final String ORGANIZATION="Organization";
	public static final String SEKTOR="Sektor";
	public static final String USER="Users";
	public OrganizationList orgList=null;
	public OrganizationForm orgForm=null;
	public SektorList sektorList=null;
	public SektorForm sektorForm=null;
	public UserList userList=null;
	public UserForm userForm=null;
	private AuthenticatedScreen as;
	private MyVaadinApplication app;
	public HelpWindow(AuthenticatedScreen as,MyVaadinApplication app ) {
		this.as=as;
		this.app=app;
		
		setCaption("PIM Settings");
		VerticalLayout layout=(VerticalLayout)this.getContent();
		setWidth("70%");
		setHeight("70%");
		center();
		//build Tree
		tree.addItem(ORGANIZATION);
		tree.addItem(SEKTOR);
		tree.addItem(USER);
		tree.addListener((ItemClickListener) this);
		
		vPanel.setSplitPosition(40);
		hPanel.setSplitPosition(200, HorizontalSplitPanel.UNITS_PIXELS);
		hPanel.setFirstComponent(tree);
		
		hPanel.setSecondComponent(vPanel);
		layout.setSizeFull();
		layout.addComponent(hPanel);
		
	}

	public void valueChange(ValueChangeEvent event) {
		Property property = event.getProperty();
		if (property == orgList) {
			Item item = orgList.getItem(orgList.getValue());
			if (item != orgForm.getItemDataSource()) {
				orgForm.setItemDataSource(item);
			}
		}else if(property==sektorList){
			Item item=sektorList.getItem(sektorList.getValue());
			if(item!=sektorForm.getItemDataSource()){
				sektorForm.setItemDataSource(item);
			}
		}else if(property==userList){
			Item item=userList.getItem(userList.getValue());
			if(item!=userForm.getItemDataSource()){
				userForm.setItemDataSource(item);
			}
		}

	}

	public void itemClick(ItemClickEvent event) {
		if(event.getSource()==tree){
			Object itemid=event.getItemId();
			if(itemid!=null){
				if(ORGANIZATION.equals(itemid)){
					orgList=new OrganizationList(as);
					orgList.addListener((ValueChangeListener)this);
					orgForm=new OrganizationForm(as,app);
					orgForm.addNewOrganization();
					vPanel.setFirstComponent(orgList);
					vPanel.setSecondComponent(orgForm);
					
				}else if(SEKTOR.equals(itemid)){
					sektorList=new SektorList(as);
					sektorList.addListener((ValueChangeListener)this);
					sektorForm=new SektorForm(as,app);
					sektorForm.addNewSektor();
					vPanel.setFirstComponent(sektorList);
					vPanel.setSecondComponent(sektorForm);
				}else if(USER.equals(itemid)){
					userList=new UserList(as);
					userList.addListener((ValueChangeListener)this);
					userForm=new UserForm(as,app);
					userForm.addNewUser();
					vPanel.setFirstComponent(userList);
					vPanel.setSecondComponent(userForm);
				}
			}
		}

	}

}
