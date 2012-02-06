package kg.cloud.tuscon.ui;

import kg.cloud.tuscon.AuthenticatedScreen;
import kg.cloud.tuscon.dao.PersonContainer;
import kg.cloud.tuscon.domain.SearchFilter;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;


public class SharingOptions extends Window {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextArea editor=new TextArea();;
	
	@SuppressWarnings("serial")
	public SharingOptions(final AuthenticatedScreen as) {
		/*
		 * Make the window modal, which will disable all other components while
		 * it is visible
		 */
		

		VerticalLayout layout = (VerticalLayout) this.getContent();
		setModal(true);

	
		/* Make the sub window 50% the size of the browser window */
		setWidth("50%");
		/*
		 * Center the window both horizontally and vertically in the browser
		 * window
		 */
		Button generate=new Button("Get Emails");
		generate.addListener(new ClickListener(){

			public void buttonClick(ClickEvent event) {
				String initialText="";
				PersonContainer pc=as.getDataSource();
				
				SearchFilter sf=as.getSf();
				if (sf != null) {
					pc.removeAllContainerFilters();
					pc.addContainerFilter(sf.getPropertyId(), sf.getTerm(), true, false);
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
				
			}});
		center();
		
		layout.addComponent(generate);
		editor.setRows(20); // this will make it an 'area', i.e multiline
		editor.setColumns(40);
		editor.setImmediate(true);

		setCaption("Sharing options");
		layout.addComponent(new Label(
				"With these setting you can modify contact sharing "
						+ "options. (non-functional, example of modal dialog)"));

		layout.addComponent(editor);
		Button close = new Button("Clear");
		close.addListener(new Button.ClickListener() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				editor.setValue("");
			}
		});
		layout.addComponent(close);
	}
}
