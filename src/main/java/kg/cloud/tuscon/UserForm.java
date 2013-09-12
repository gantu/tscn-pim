package kg.cloud.tuscon;

import java.util.Arrays;
import java.util.List;

import kg.cloud.tuscon.dao.DbUser;
import kg.cloud.tuscon.dao.UserContainer;
import kg.cloud.tuscon.domain.User;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class UserForm extends Form implements ClickListener {
	private Button save = new Button("Save", (ClickListener) this);
	private Button edit = new Button("Edit", (ClickListener) this);
	private Button delete = new Button("Delete", (ClickListener) this);
	private Button cancel = new Button("Cancel", (ClickListener) this);
	private boolean newUserMode = false;
	private User newUser = null;
	private ComboBox roles = new ComboBox("Role");
	AuthenticatedScreen as;
	MyVaadinApplication app;

	public UserForm(AuthenticatedScreen as, MyVaadinApplication app) {
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
				if (propertyId.equals("roles")) {
					roles.setWidth("200px");
					roles.addItem("admin");
					roles.addItem("user");
					return roles;
				}
				Field field = super.createField(item, propertyId, uiContext);
				if (propertyId.equals("username")) {
					TextField tf = (TextField) field;
					tf.setNullRepresentation("");
					tf.setRequired(true);
				} else if (propertyId.equals("password")) {
					TextField tf = (TextField) field;
					tf.setNullRepresentation("");
					tf.setSecret(true);
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
			if (newUserMode) {
				Item addItem = as.getUserSource().addItem(newUser);
				setItemDataSource(addItem);

				try {
					DbUser dbUser = new DbUser();
					dbUser.connect();
					dbUser.execSQLInsert(newUser);
					dbUser.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				app.getMainWindow().showNotification("Inserted Successfully!");
				newUserMode = false;
			} else {
				if (!newUserMode) {
					BeanItem<User> o = (BeanItem<User>) this
							.getItemDataSource();
					try {
						DbUser dbUser = new DbUser();
						dbUser.connect();
						dbUser.execSQLUpdate(o.getBean());
						dbUser.close();
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
			if (newUserMode) {
				newUserMode = false;
				/* Clear the form and make it invisible */
				setItemDataSource(null);
			} else {
				discard();
			}
			setReadOnly(true);

		} else if (source == edit) {
			setReadOnly(false);
		} else if (source == delete) {
			if (!newUserMode) {
				BeanItem<User> o = (BeanItem<User>) this.getItemDataSource();
				try {
					DbUser dbUser = new DbUser();
					dbUser.connect();
					dbUser.execSQLDelete(o.getBean());
					dbUser.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				as.getUserSource().removeItem(o.getBean());
				app.getMainWindow().showNotification("Deleted Successfully");
			}
		}

	}

	@Override
	public void setItemDataSource(Item newDataSource) {
		newUserMode = false;
		if (newDataSource != null) {
			List<Object> orderedProperties = Arrays
					.asList(UserContainer.NATURAL_COL_ORDER);
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

	public void addNewUser() {
		newUser = new User();
		setItemDataSource(new BeanItem<User>(newUser));
		newUserMode = true;
		setReadOnly(false);

	}

}
