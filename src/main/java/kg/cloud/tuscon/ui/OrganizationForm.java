package kg.cloud.tuscon.ui;

import java.util.Arrays;
import java.util.List;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.MyVaadinApplication;
import kg.cloud.tuscon.dao.DbOrganization;
import kg.cloud.tuscon.dao.OrganizationContainer;
import kg.cloud.tuscon.domain.Organization;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class OrganizationForm extends Form implements ClickListener {
	private Button save = new Button("Save", (ClickListener) this);
	private Button newOrg = new Button("New");
	private Button edit = new Button("Edit", (ClickListener) this);
	private Button delete = new Button("Delete", (ClickListener) this);
	private Button cancel = new Button("Cancel", (ClickListener) this);
	private boolean newOrgMode = false;
	private Organization newOrganization = null;
	AuthenticatedScreen as;
	MyVaadinApplication app;

	public OrganizationForm(AuthenticatedScreen as, MyVaadinApplication app) {
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

		setFormFieldFactory(new DefaultFieldFactory() {
			public Field createField(Item item, Object propertyId,
					Component uiContext) {
				Field field = super.createField(item, propertyId, uiContext);
				if (propertyId.equals("id")) {
					TextField tf = (TextField) field;
					tf.setNullRepresentation("");
					tf.setRequired(true);
				} else if (propertyId.equals("orgName")) {
					TextField tf = (TextField) field;
					tf.setNullRepresentation("");
					tf.setRequired(true);
				}
				field.setWidth("200px");
				return field;
			}
		});
	}

	public void buttonClick(ClickEvent event) {
		Button source = event.getButton();
		if (source == save) {
			if (!isValid()) {
				return;
			}
			commit();
			if (newOrgMode) {
				Item addItem = as.getOrganizationsSource().addItem(
						newOrganization);
				setItemDataSource(addItem);

				try {
					DbOrganization dbOrg = new DbOrganization();
					dbOrg.connect();
					dbOrg.execSQLInsert(newOrganization);
					dbOrg.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				app.getMainWindow().showNotification("Inserted Successfully!");
				newOrgMode = false;
			} else {
				if (!newOrgMode) {
					BeanItem<Organization> o = (BeanItem<Organization>) this
							.getItemDataSource();
					try {
						DbOrganization dbOrg = new DbOrganization();
						dbOrg.connect();
						dbOrg.execSQLUpdate(o.getBean());
						dbOrg.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					app.getMainWindow().showNotification(
							"Updated Successfully!");
				}
			}
			setReadOnly(true);
		} else if (source == cancel) {
			if (newOrgMode) {
				newOrgMode = false;
				/* Clear the form and make it invisible */
				setItemDataSource(null);
			} else {
				discard();
			}
			setReadOnly(true);

		} else if (source == edit) {
			setReadOnly(false);
		} else if (source == delete) {
			if (!newOrgMode) {
				BeanItem<Organization> o = (BeanItem<Organization>) this
						.getItemDataSource();
				try {
					DbOrganization dbOrg = new DbOrganization();
					dbOrg.connect();
					dbOrg.execSQLDelete(o.getBean());
					dbOrg.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				as.getOrganizationsSource().removeItem(o.getBean());
				app.getMainWindow().showNotification("Deleted Successfully");
			}
		}

	}

	@Override
	public void setItemDataSource(Item newDataSource) {
		newOrgMode = false;
		if (newDataSource != null) {
			List<Object> orderedProperties = Arrays
					.asList(OrganizationContainer.NATURAL_COL_ORDER);
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
		newOrg.setVisible(readOnly);
		save.setVisible(!readOnly);
		cancel.setVisible(!readOnly);
		edit.setVisible(readOnly);
		delete.setVisible(readOnly);
	}

	public void addNewOrganization() {
		newOrganization = new Organization();
		setItemDataSource(new BeanItem<Organization>(newOrganization));
		newOrgMode = true;
		setReadOnly(false);

	}

}
