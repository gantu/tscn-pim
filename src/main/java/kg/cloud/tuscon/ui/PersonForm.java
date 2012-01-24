package kg.cloud.tuscon.ui;

import java.util.Arrays;
import java.util.List;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.dao.PersonContainer;
import kg.cloud.tuscon.domain.Membership;
import kg.cloud.tuscon.domain.Person;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Select;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Component;

public class PersonForm extends Form implements ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button save = new Button("Save", (ClickListener) this);
	private Button cancel = new Button("Cancel", (ClickListener) this);
	private Button edit = new Button("Edit", (ClickListener) this);
	private Button delete=new Button("Delete",(ClickListener)this);
	
	private final Select organizations =new Select("Organization");
	private final ComboBox memberships = new ComboBox("Membership");
	private final ListSelect sektors = new ListSelect("Sektors");
	private final PopupDateField datetime=new PopupDateField("Date Of Birth");

	private AuthenticatedScreen as;
	private boolean newContactMode = false;
	private Person newPerson = null;

	public PersonForm(AuthenticatedScreen as) {
		this.as = as;
		
		setWriteThrough(false);
		HorizontalLayout footer = new HorizontalLayout();
		footer.setSpacing(true);
		footer.addComponent(save);
		footer.addComponent(cancel);
		footer.addComponent(edit);
		footer.addComponent(delete);
		footer.setVisible(false);
		setFooter(footer);

		/* Allow the user to enter new cities */
		organizations.setNewItemsAllowed(false);
		/* We do not want to use null values */
		organizations.setNullSelectionAllowed(false);
		/* Add an empty city used for selecting no city */
		//organizations.addItem("");
		
		organizations.setContainerDataSource(as.getOrganizationsSource());
		organizations.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		organizations.setItemCaptionPropertyId("orgName");
		
		memberships.setNewItemsAllowed(false);
		/* We do not want to use null values */
		memberships.setNullSelectionAllowed(false);
		/* Add an empty city used for selecting no city */
		//memberships.addItem("");
		
		
		memberships.setContainerDataSource(as.getMembershipSource());
		memberships.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		memberships.setItemCaptionPropertyId("unityName");
		//memberships.setValue(this.getItemDataSource().getItemProperty("membership"));
		
		sektors.setRows(10);
		sektors.setNullSelectionAllowed(true);
		sektors.setMultiSelect(true);
		//sektors.setImmediate(true);
		sektors.setItemCaptionMode(Select.ITEM_CAPTION_MODE_PROPERTY);
		sektors.setItemCaptionPropertyId("sektorName");
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
					return organizations;
				}
				
				if(propertyId.equals("membership")){
					memberships.setWidth("200px");
					return memberships;
				}
				if(propertyId.equals("sektor")){
					sektors.setWidth("200px");
					return sektors;
				}
				if(propertyId.equals("dob")){
					datetime.setWidth("200px");
					datetime.setDateFormat("yyyy-MM-dd");
					return datetime;
				}

				Field field = super.createField(item, propertyId, uiContext);
				if (propertyId.equals("postalCode")) {
					TextField tf = (TextField) field;
					/*
					 * We do not want to display "null" to the user when the
					 * field is empty
					 */
					tf.setNullRepresentation("");

					/* Add a validator for postalCode and make it required */
					tf.addValidator(new RegexpValidator("[1-9][0-9]{4}",
							"Postal code must be a five digit number and cannot start with a zero."));
					tf.setRequired(true);
				} else if (propertyId.equals("email")) {
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
				newPerson.setMembership(memberships.getValue().toString());
				newPerson.setOrganization(organizations.getValue().toString());
				newPerson.setSektor(sektors.getValue().toString());
				Item addedItem = as.getDataSource().addItem(newPerson);
				/*
				 * We must update the form to use the Item from our datasource
				 * as we are now in edit mode (no longer in add mode)
				 */
				setItemDataSource(addedItem);

				newContactMode = false;
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