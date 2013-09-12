package kg.cloud.tuscon.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.MyVaadinApplication;
import kg.cloud.tuscon.dao.DbPerson;
import kg.cloud.tuscon.dao.OrganizationContainer;
import kg.cloud.tuscon.dao.PersonContainer;
import kg.cloud.tuscon.dao.SektorContainer;
import kg.cloud.tuscon.domain.Person;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;

public class PersonForm extends Form implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button save = new Button("Save", (ClickListener) this);
	private Button cancel = new Button("Cancel", (ClickListener) this);
	private Button edit = new Button("Edit", (ClickListener) this);
	private Button delete = new Button("Delete", (ClickListener) this);

	private final NativeSelect organizations = new NativeSelect("Organization");
	private final ListSelect sektors = new ListSelect("Sektors");
	private final PopupDateField datetime = new PopupDateField("Date Of Birth");
	private final NativeSelect gender = new NativeSelect("Gender");
	private final NativeSelect companyType = new NativeSelect("Company Type");

	private AuthenticatedScreen as;
	private boolean newContactMode = false;
	private Person newPerson = null;
	private MyVaadinApplication app;

	public PersonForm(MyVaadinApplication app, AuthenticatedScreen as) {
		this.as = as;
		this.app = app;
		setWriteThrough(false);
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(cancel);
		footer.addComponent(edit);
		footer.addComponent(delete);
		footer.setVisible(false);
		setFooter(footer);
		
		// organization selec is beeing feeded
		OrganizationContainer orgContainer=as.getOrganizationsSource();
		for(int i=0;i<orgContainer.size();i++){
			organizations.addItem(orgContainer.getIdByIndex(i).getId());
		}
		//organizations.setContainerDataSource(as.getOrganizationsSource());
		sektors.setContainerDataSource(as.getSektorsSource());
		
		
		
	

		/* Populate cities select using the cities in the data container */

		/*
		 * Field factory for overriding how the component for city selection is
		 * created
		 */
		setFormFieldFactory(new DefaultFieldFactory() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				if (propertyId.equals("organization")) {
					organizations.setWidth("200px");
					/* We do not want to use null values */
					organizations.setNullSelectionAllowed(false);
					/* Add an empty city used for selecting no city */
					// organizations.addItem("");
					organizations.setImmediate(true);
				/*	organizations
							.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
					organizations.setItemCaptionPropertyId("id");*/
					return organizations;
				}

				if (propertyId.equals("sektor")) {
					sektors.setWidth("200px");
					sektors.setRows(10);
					sektors.setNullSelectionAllowed(true);
					sektors.setMultiSelect(true);
					sektors.setImmediate(true);
					sektors.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
					sektors.setItemCaptionPropertyId("sektorName");
					sektors.setRequired(true);
					sektors.setRequiredError("You must select at least one sektor!");
					return sektors;
				}
				if (propertyId.equals("dob")) {
					datetime.setWidth("200px");
					datetime.setDateFormat("yyyy-MM-dd");
					datetime.setRequired(true);
					return datetime;
				}
				if (propertyId.equals("gender")) {
					gender.setWidth("200px");
					gender.addItem("Male");
					gender.addItem("Female");
					return gender;
				}
				if (propertyId.equals("companyType")) {
					companyType.setWidth("200px");
					companyType.addItem("Import");
					companyType.addItem("Export");
					companyType.addItem("Consultancy");
					return companyType;
				}

				Field field = super.createField(item, propertyId, uiContext);
				if (propertyId.equals("email")) {
					/* Add a validator for email and make it required */
					field.addValidator(new EmailValidator(
							"Email must contain '@' and have full domain."));
					field.setRequired(true);

				}
				field.setWidth("200px");
				return field;
			}
		});
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();

		if (source == save) {
			/* If the given input is not valid there is no point in continuing */
			if (!isValid()) {
				return;
			}

			commit();

			if (newContactMode) {

				/* We need to add the new person to the container */

				Item addedItem = as.getDataSource().addItem(newPerson);
				/*
				 * We must update the form to use the Item from our datasource
				 * as we are now in edit mode (no longer in add mode)
				 */
				setItemDataSource(addedItem);

				try {
					DbPerson dbP = new DbPerson();
					dbP.connect();
					dbP.execSQLInsert(newPerson);
					dbP.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				newContactMode = false;
			} else {
				if (!newContactMode) {
					BeanItem<Person> p = (BeanItem<Person>) this
							.getItemDataSource();
					try {
						DbPerson dbP = new DbPerson();
						dbP.connect();
						dbP.execSQLUpdate(p.getBean());
						dbP.close();

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					app.getMainWindow()
							.showNotification("Updated Successfully");
				}
			}

			setReadOnly(true);
		} else if (source == cancel) {
			if (newContactMode) {
				newContactMode = false;
				/* Clear the form and make it invisible */
				setItemDataSource(null);
			} else {
				discard();
			}
			setReadOnly(true);
		} else if (source == edit) {
			setReadOnly(false);
		} else if (source == delete) {
			if (!newContactMode) {
				BeanItem<Person> p = (BeanItem<Person>) this
						.getItemDataSource();
				try {
					DbPerson dbP = new DbPerson();
					dbP.connect();
					dbP.execSQLDelete(p.getBean());
					dbP.close();

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				as.getDataSource().removeItem(p.getBean());
				app.getMainWindow().showNotification(
						p.getBean().getId()
								+ "The contact is successfully deleted!");

			}
		}
	}

	@Override
	public void setItemDataSource(Item newDataSource) {
		newContactMode = false;
		if (newDataSource != null) {
			List<Object> orderedProperties = Arrays
					.asList(PersonContainer.NATURAL_COL_ORDER);
			super.setItemDataSource(newDataSource, orderedProperties);

			setReadOnly(true);
			getFooter().setVisible(true);
		} else {
			super.setItemDataSource(null);
			getFooter().setVisible(false);
		}
	}

	@Override
	public void setReadOnly(boolean readOnly) {
		super.setReadOnly(readOnly);
		save.setVisible(!readOnly);
		cancel.setVisible(!readOnly);
		edit.setVisible(readOnly);
		delete.setVisible(readOnly);
	}

	public void addContact() {
		// Create a temporary item for the form
		newPerson = new Person();
		setItemDataSource(new BeanItem<Person>(newPerson));
		newContactMode = true;
		setReadOnly(false);
	}

}