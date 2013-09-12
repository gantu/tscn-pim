package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.MyVaadinApplication;
import kg.cloud.tuscon.dao.PersonContainer;
import kg.cloud.tuscon.domain.SearchFilter;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

public class SharingOptions extends Window implements ClickListener{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextArea editor = new TextArea();
	AuthenticatedScreen as;
	Button generate = new Button("Get Emails");
	Button toExcel = new Button("Export");
	Button close = new Button("Clear");
	CheckBox  cb;
	//MyVaadinApplication app;

	@SuppressWarnings("serial")
	public SharingOptions(AuthenticatedScreen as) {
		/*
		 * Make the window modal, which will disable all other components while
		 * it is visible
		 */
		this.as = as;
	//	PersonContainer p=as.getDataSource();
		
		VerticalLayout layout = (VerticalLayout) this.getContent();
		HorizontalLayout controls = new HorizontalLayout();
		
		setModal(true);

		/* Make the sub window 50% the size of the browser window */
		setWidth("50%");
		/*
		 * Center the window both horizontally and vertically in the browser
		 * window
		 */

		
		toExcel.setIcon(new ThemeResource("icons/32/document-xsl.png"));
		generate.setIcon(new ThemeResource("icons/32/email.png"));
		toExcel.addListener(this);
		generate.addListener(this);
		close.addListener(this);
		
		center();
		controls.addComponent(toExcel);
		controls.addComponent(generate);
		layout.addComponent(controls);
	
		editor.setRows(20); // this will make it an 'area', i.e multiline
		editor.setColumns(40);
		editor.setImmediate(true);

		setCaption("Sharing options");
		layout.addComponent(new Label(
				"Click to generate to get all email addresses!"));

		layout.addComponent(editor);
		layout.addComponent(close);
	}

	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		String initialText = "";
		PersonContainer pc = as.getDataSource();
		SearchFilter sf = as.getSf();
		if(source==generate){
			if (sf != null) {
				pc.removeAllContainerFilters();
				pc.addContainerFilter(sf.getPropertyId(), sf.getTerm(),
						true, false);
				for (int i = 0; i < pc.size(); i++) {
					initialText += pc.getIdByIndex(i).getEmail() + ";";
				}
				editor.setValue(initialText);
			} else {
				for (int i = 0; i < pc.size(); i++) {
					initialText += pc.getIdByIndex(i).getEmail() + ";";
				}
				editor.setValue(initialText);
			}
		}else if(source==toExcel){
			exportToExcel(pc,sf);
		}else if(source==close){
			editor.setValue("");
		}else if(source==cb){
			editor.setValue(source.booleanValue()? "enabled" : "disabled");
		}

	}

	private void exportToExcel(PersonContainer pc, SearchFilter sf) {
		// TODO Auto-generated method stub
		
	}

	
}
