package kg.cloud.tuscon.ui;

import java.util.Arrays;
import java.util.List;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.MyVaadinApplication;
import kg.cloud.tuscon.dao.DbSektor;
import kg.cloud.tuscon.dao.SektorContainer;
import kg.cloud.tuscon.domain.Sektor;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class SektorForm extends Form implements ClickListener {

	private Button save = new Button("Save", (ClickListener) this);
	private Button newOrg = new Button("New");
	private Button edit = new Button("Edit", (ClickListener) this);
	private Button delete = new Button("Delete", (ClickListener) this);
	private Button cancel = new Button("Cancel", (ClickListener) this);
	private boolean newSekMode = false;
	private Sektor newSektor = null;
	AuthenticatedScreen as;
	MyVaadinApplication app;

	public SektorForm(AuthenticatedScreen as, MyVaadinApplication app) {
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
					tf.setReadOnly(true);
				} else if (propertyId.equals("sektorName")) {
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
			if (newSekMode) {
				Item addItem = as.getSektorsSource().addItem(newSektor);
				setItemDataSource(addItem);

				try {
					DbSektor dbSek = new DbSektor();
					dbSek.connect();
					dbSek.execSQLInsert(newSektor);
					dbSek.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				app.getMainWindow().showNotification("Inserted Successfully!");
				newSekMode = false;
			} else {
				if (!newSekMode) {
					BeanItem<Sektor> o = (BeanItem<Sektor>) this
							.getItemDataSource();
					try {
						DbSektor dbSek = new DbSektor();
						dbSek.connect();
						dbSek.execSQLUpdate(o.getBean());
						dbSek.close();
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
			if (newSekMode) {
				newSekMode = false;
				/* Clear the form and make it invisible */
				setItemDataSource(null);
			} else {
				discard();
			}
			setReadOnly(true);

		} else if (source == edit) {
			setReadOnly(false);
		} else if (source == delete) {
			if (!newSekMode) {
				BeanItem<Sektor> o = (BeanItem<Sektor>) this
						.getItemDataSource();
				try {
					DbSektor dbSek = new DbSektor();
					dbSek.connect();
					dbSek.execSQLDelete(o.getBean());
					dbSek.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				as.getSektorsSource().removeItem(o.getBean());
				app.getMainWindow().showNotification("Deleted Successfully");
			}
		}

	}

	@Override
	public void setItemDataSource(Item newDataSource) {
		newSekMode = false;
		if (newDataSource != null) {
			List<Object> orderedProperties = Arrays
					.asList(SektorContainer.NATURAL_COL_ORDER);
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

	public void addNewSektor() {
		newSektor = new Sektor();
		setItemDataSource(new BeanItem<Sektor>(newSektor));
		newSekMode = true;
		setReadOnly(false);

	}

}
