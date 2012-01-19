package kg.cloud.tuscon.ui;

import com.vaadin.ui.Label;
import com.vaadin.ui.Window;

public class HelpWindow extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String HELP_HTML_SNIPPET = "Hopefully it doesn't need any real help.";

   public HelpWindow() {
        setCaption("Address Book help");
        addComponent(new Label(HELP_HTML_SNIPPET, Label.CONTENT_XHTML));
    }
	

}
